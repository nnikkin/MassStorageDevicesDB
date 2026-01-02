package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Domain.UsbInterface;
import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Presenters.FlashDrivePresenter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnRendering;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Route("flash")
public class FlashDriveView extends AppLayout {
    private final FlashDrivePresenter presenter;
    private Grid<FlashDriveDto> flashDriveGrid;
    private List<Component> buttons;

    public FlashDriveView(FlashDrivePresenter presenter) {
        this.presenter = presenter;

        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        H1 pageTitle = new H1("Запоминающие устройства - Флеш-память");
        pageTitle.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        // Настройка навигационного меню
        DrawerToggle toggle = new DrawerToggle();
        NavBar nav = new NavBar();
        Div navDrawer = new Div(nav);
        navDrawer.setWidthFull();

        Scroller scroller = new Scroller(navDrawer);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, pageTitle);

        setPrimarySection(Section.DRAWER);

        HorizontalLayout tableMenu = new HorizontalLayout();

        setupButtons();
        tableMenu.add(buttons);

        setContent(layout);

        initTable();
        fillTable();
        setupFilters();

        layout.add(tableMenu, flashDriveGrid);
    }

    private void initTable() {
        flashDriveGrid = new Grid<>(FlashDriveDto.class, false);
        flashDriveGrid.setItems(new ArrayList<>());
        flashDriveGrid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        flashDriveGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        flashDriveGrid.setColumnRendering(ColumnRendering.LAZY);
        flashDriveGrid.setEmptyStateText("В таблице отсутствуют записи.");

        flashDriveGrid.addColumn(FlashDriveDto::name)
                .setHeader("")  // иначе при добавлении поиска по столбцу в setupFilters будет NoSuchElementException
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDriveDto::capacity)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDriveDto::usbInterface)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDriveDto::usbType)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDriveDto::readSpeed)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDriveDto::writeSpeed)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);

        flashDriveGrid.addSelectionListener(
                selectionEvent -> {
                    if (selectionEvent.getAllSelectedItems().isEmpty())
                        buttons.forEach(button -> ((Button) button).setEnabled(false));
                    else
                        buttons.forEach(button -> ((Button) button).setEnabled(true));

                    ((Button) buttons.getFirst()).setEnabled(true);
                }
        );
    }

    private void refreshGrid() {
        fillTable();
        flashDriveGrid.deselectAll();
    }

    private void fillTable() {
        List<FlashDriveDto> items = presenter.loadAllFlashDrives();
        flashDriveGrid.getListDataView().setItems(items);
    }

    private void setupFilters() {
        var headerCells = flashDriveGrid.getHeaderRows()
                                        .getFirst()
                                        .getCells();

        FlashDriveFilter filter = new FlashDriveFilter(flashDriveGrid.getListDataView());

        headerCells.getFirst().setComponent(createFilterHeader("Наименование", filter::setName));
        headerCells.get(1).setComponent(createFilterHeader("Объём (МБ)", filter::setCapacity));
        headerCells.get(2).setComponent(createFilterHeader("Интерфейс USB", filter::setUsbInterface));
        headerCells.get(3).setComponent(createFilterHeader("Тип USB", filter::setUsbType));
        headerCells.get(4).setComponent(createFilterHeader("Скорость чтения (МБ/сек)", filter::setReadSpeed));
        headerCells.getLast().setComponent(createFilterHeader("Скорость записи (МБ/сек)", filter::setWriteSpeed));
    }

    private void setupButtons() {
        buttons = new ArrayList<>();

        Button addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBtn.addClickListener(e -> showNewEntryDialog());

        Button editBtn = new Button("Изменить", VaadinIcon.PENCIL.create());
        editBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editBtn.setEnabled(false);
        editBtn.addClickListener(e -> {
            var selected = flashDriveGrid.getSelectedItems();
            if (selected.size() != 1) {
                showErrorDialog("Выберите одну запись для редактирования");
            } else {
                showEditDialog(selected.iterator().next());
            }
        });

        Button delBtn = new Button("Удалить", VaadinIcon.TRASH.create());
        delBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delBtn.setEnabled(false);
        delBtn.addClickListener(e -> showDelConfirmDialog());

        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(delBtn);
    }

    private static Component createFilterHeader(String labelText,
                                                Consumer<String> filterChangeConsumer) {
        NativeLabel label = new NativeLabel(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    private void showNewEntryDialog() {
        var dialog = new FlashDriveDialog(flashDrive -> {
            presenter.addFlashDrive(flashDrive);
            refreshGrid();
        });
        dialog.setHeaderTitle("Добавление новой записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showEditDialog(FlashDriveDto oldDto) {
        var dialog = new FlashDriveDialog(newDto -> {
            presenter.updateFlashDrive(oldDto.id(), newDto);
            refreshGrid();
        }, oldDto);
        dialog.setHeaderTitle("Изменение записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showDelConfirmDialog() {
        var selectedItems = flashDriveGrid.getSelectedItems();
        String message = selectedItems.size() == 1
                ? "Вы действительно хотите удалить запись?"
                : "Вы действительно хотите удалить несколько записей?";

        Div textDiv = new Div(message);
        textDiv.getStyle().set("padding", "var(--lumo-space-m)");

        CustomDialog confirmDialog = new CustomDialog("Удаление записи", textDiv);
        confirmDialog.setOkButtonText("Да, удалить");
        confirmDialog.setCancelDialogButtonText("Нет");

        confirmDialog.open();

        confirmDialog.addOkClickListener(e -> {
            List<Long> ids = selectedItems.stream()
                    .map(FlashDriveDto::id)
                    .toList();

            presenter.deleteFlashDrives(ids);
            refreshGrid();
            confirmDialog.close();

            Notification.show(
                    "Удалено записей: " + ids.size(),
                    3000,
                    Notification.Position.BOTTOM_END
            ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        confirmDialog.open();
    }

    private void showErrorDialog(String message) {
        Div textDiv = new Div(message);

        CustomDialog errorDialog = new CustomDialog("Ошибка", textDiv);
        errorDialog.setCancelButtonVisible(false);

        errorDialog.open();

        errorDialog.addOkClickListener(e -> errorDialog.close());
    }

    private static class FlashDriveFilter {
        private final GridListDataView<FlashDriveDto> dataView;

        private String name;
        private String usbInterface;
        private String usbType;
        private String capacity;
        private String writeSpeed;
        private String readSpeed;

        public FlashDriveFilter(GridListDataView<FlashDriveDto> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setName(String name) {
            this.name = name;
            this.dataView.refreshAll();
        }

        public void setUsbInterface(String usbInterface) {
            this.usbInterface = usbInterface;
            this.dataView.refreshAll();
        }

        public void setUsbType(String usbType) {
            this.usbType = usbType;
            this.dataView.refreshAll();
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
            this.dataView.refreshAll();
        }

        public void setWriteSpeed(String writeSpeed) {
            this.writeSpeed = writeSpeed;
            this.dataView.refreshAll();
        }

        public void setReadSpeed(String readSpeed) {
            this.readSpeed = readSpeed;
            this.dataView.refreshAll();
        }

        private boolean test(FlashDriveDto dto) {
            return matches(dto.name(), name)
                    && matches(dto.usbInterface(), usbInterface)
                    && matches(dto.usbType(), usbType)
                    && matchesNumeric(dto.capacity(), capacity)
                    && matchesNumeric(dto.writeSpeed(), writeSpeed)
                    && matchesNumeric(dto.readSpeed(), readSpeed);
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || (value != null && value.toLowerCase().contains(searchTerm.toLowerCase()));
        }

        private boolean matchesNumeric(Float value, String searchTerm) {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return true;
            }

            return String.valueOf(value).startsWith(searchTerm);
        }
    }

    static class FlashDriveForm extends FormLayout {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<UsbInterface> usbInterfaceBox;
        private ComboBox<String> usbTypeBox;
        private NumberField readSpeedField;
        private NumberField writeSpeedField;

        private Binder<FlashDriveDto> binder = new Binder<>(FlashDriveDto.class);
        private Long currentId = null;

        public FlashDriveForm() {
            setupFields();
            setupBinder();
            add(nameField, capacityField, usbInterfaceBox,
                    usbTypeBox, writeSpeedField, readSpeedField);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        private void setupFields() {
            nameField = new TextArea("Название:");
            capacityField = new NumberField("Объём:");
            capacityUnitBox = new ComboBox<>();
            usbInterfaceBox = new ComboBox<>("Интерфейс:");
            usbTypeBox = new ComboBox<>("Версия USB:");
            readSpeedField = new NumberField("Скорость записи:");
            writeSpeedField = new NumberField("Скорость чтения:");

            nameField.setMinRows(1);
            nameField.setMaxRows(1);
            nameField.setMinLength(1);
            nameField.setMaxLength(30);
            nameField.setClearButtonVisible(true);

            capacityField.setClearButtonVisible(true);
            capacityField.setRequiredIndicatorVisible(true);

            capacityUnitBox.setItems(Bytes.values());
            capacityUnitBox.setItemLabelGenerator(Bytes::getLabel);
            capacityUnitBox.setValue(Bytes.MB);
            capacityField.setSuffixComponent(capacityUnitBox);

            usbInterfaceBox.setItems(UsbInterface.values());
            usbInterfaceBox.setItemLabelGenerator(UsbInterface::getLabel);
            usbInterfaceBox.setClearButtonVisible(true);
            usbInterfaceBox.addValueChangeListener(e -> System.out.println(e.getValue()));

            usbTypeBox.setItems("1.0", "2.0", "3.0", "3.1", "3.2");
            usbTypeBox.setClearButtonVisible(true);

            readSpeedField.setSuffixComponent(new Div("МБ/с"));
            readSpeedField.setClearButtonVisible(true);

            writeSpeedField.setSuffixComponent(new Div("МБ/с"));
            writeSpeedField.setClearButtonVisible(true);
        }

        private void setupBinder() {
            // Bind fields to DTO
            binder.forField(nameField)
                    .asRequired("Название обязательно")
                    .withValidator(name -> name.length() <= 30, "Название должно быть до 30 символов")
                    .bind(
                            FlashDriveDto::name,
                            (dto, value) -> {}
                    );

            binder.forField(capacityField)
                    .asRequired("Объём обязателен")
                    .withValidator(capacity -> capacity > 0, "Объём должен быть положительным")
                    .bind(
                            dto -> dto.capacity() != null ? dto.capacity().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(usbInterfaceBox)
                    .bind(
                            dto -> dto.usbInterface() != null ? UsbInterface.valueOfLabel(dto.usbInterface()) : null,
                            (dto, value) -> {}
                    );

            binder.forField(usbTypeBox)
                    .bind(FlashDriveDto::usbType, (dto, value) -> {});

            binder.forField(writeSpeedField)
                    .withValidator(writeSpd -> writeSpd == null || writeSpd > 0, "Скорость должна быть положительной")
                    .bind(
                            dto -> dto.writeSpeed() != null ? dto.writeSpeed().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(readSpeedField)
                    .withValidator(readSpd -> readSpd == null || readSpd > 0, "Скорость должна быть положительной")
                    .bind(
                            dto -> dto.readSpeed() != null ? dto.readSpeed().doubleValue() : null,
                            (dto, value) -> {}
                    );
        }

        public void setFlashDrive(FlashDriveDto flashDrive) {
            this.currentId = flashDrive.id();
            binder.readBean(flashDrive);
        }

        private float toMegabytes(float value, Bytes unit) {
            if (unit == Bytes.MB)
                return value;
            else {
                if (unit.getRank() > Bytes.MB.getRank())
                    return (float) (value * Math.pow(1024, unit.getRank() - Bytes.MB.getRank()));

                else {
                    if (unit == Bytes.BIT)
                        return (float) ((value / 8) / Math.pow(1024, Bytes.MB.getRank() - 1));

                    return (float) (value / Math.pow(1024, Bytes.MB.getRank() - unit.getRank()));
                }
            }
        }

        public Optional<FlashDriveDto> getFormDataObject() {
            if (!isValid()) {
                return Optional.empty();
            }

            FlashDriveDto dto = new FlashDriveDto(
                    currentId,
                    nameField.getValue(),
                    usbInterfaceBox.getValue() != null ? usbInterfaceBox.getValue().getLabel() : null,
                    usbTypeBox.getValue(),
                    capacityField.getValue() != null ? toMegabytes(capacityField.getValue().floatValue(), capacityUnitBox.getValue()) : null,
                    writeSpeedField.getValue() != null ? writeSpeedField.getValue().floatValue() : null,
                    readSpeedField.getValue() != null ? readSpeedField.getValue().floatValue() : null
            );

            return Optional.of(dto);
        }

        public boolean isValid() {
            return binder.validate().isOk();
        }

        public void clear() {
            currentId = null;
            nameField.clear();
            capacityField.clear();
            usbInterfaceBox.clear();
            usbTypeBox.clear();
            writeSpeedField.clear();
            readSpeedField.clear();
            binder.validate();
        }
    }

    static class FlashDriveDialog extends CustomDialog {
        private final FlashDriveForm form;
        private final SerializableConsumer<FlashDriveDto> onSaveCallback;
        private boolean isEditMode;

        public FlashDriveDialog(SerializableConsumer<FlashDriveDto> onSaveCallback) {
            this(onSaveCallback, null);
        }

        public FlashDriveDialog(SerializableConsumer<FlashDriveDto> onSaveCallback, FlashDriveDto dto) {
            this.onSaveCallback = onSaveCallback;

            form = new FlashDriveForm();
            isEditMode = false;

            if (dto != null) {
                isEditMode = true;
                form.setFlashDrive(dto);
            }

            addToDialogBody(form);
        }

        private void save() {
            if (!form.isValid()) {
                Notification notification = Notification.show(
                        "Пожалуйста, исправьте ошибки в форме",
                        3000,
                        Notification.Position.MIDDLE
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            form.getFormDataObject().ifPresent(flashDrive -> {
                try {
                    onSaveCallback.accept(flashDrive);
                    close();

                    Notification notification = Notification.show(
                            isEditMode ? "Запись обновлена" : "Запись создана",
                            3000,
                            Notification.Position.MIDDLE
                    );
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } catch (Exception e) {
                    Notification notification = Notification.show(
                            "Ошибка при сохранении: " + e.getMessage(),
                            5000,
                            Notification.Position.MIDDLE
                    );
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });
        }
    }
}
