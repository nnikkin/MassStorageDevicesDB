package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Domain.FloppyDensity;
import com.nikkin.devicesdb.Dto.FloppyDiskDto;
import com.nikkin.devicesdb.Presenters.FloppyDiskPresenter;
import com.nikkin.devicesdb.Views.CustomDialog;
import com.nikkin.devicesdb.Views.NavBar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Route("floppy")
public class FloppyDiskView extends AppLayout {
    private final FloppyDiskPresenter presenter;
    private Grid<FloppyDiskDto> floppyDiskGrid;
    private List<Component> buttons;

    public FloppyDiskView(FloppyDiskPresenter presenter) {
        this.presenter = presenter;

        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        H1 pageTitle = new H1("Запоминающие устройства - Накопители на гибких дисках");
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

        layout.add(tableMenu, floppyDiskGrid);
    }

    private void initTable() {
        floppyDiskGrid = new Grid<>(FloppyDiskDto.class, false);
        floppyDiskGrid.setItems(new ArrayList<>());
        floppyDiskGrid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        floppyDiskGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        floppyDiskGrid.setColumnRendering(ColumnRendering.LAZY);
        floppyDiskGrid.setEmptyStateText("В таблице отсутствуют записи.");

        floppyDiskGrid.addColumn(FloppyDiskDto::name)
                .setHeader("")  // иначе при добавлении поиска по столбцу в setupFilters будет NoSuchElementException
                .setAutoWidth(true)
                .setSortable(true);
        floppyDiskGrid.addColumn(FloppyDiskDto::capacity)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        floppyDiskGrid.addColumn(FloppyDiskDto::format)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        floppyDiskGrid.addColumn(FloppyDiskDto::diskDensity)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        floppyDiskGrid.addColumn(dto ->
                        dto.isDoubleSided() ? "Да" : "Нет"
                )
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true)
                .setComparator(FloppyDiskDto::isDoubleSided);

        floppyDiskGrid.addSelectionListener(
                selectionEvent -> {
                    if (selectionEvent.getAllSelectedItems().isEmpty())
                        buttons.forEach(button -> ((Button) button).setEnabled(false));
                    else
                        buttons.forEach(button -> ((Button) button).setEnabled(true));

                    ((Button) buttons.getFirst()).setEnabled(true);
                    ((Button) buttons.getLast()).setEnabled(true);
                }
        );
    }

    private void refreshGrid() {
        floppyDiskGrid.deselectAll();
        fillTable();
    }

    private void fillTable() {
        List<FloppyDiskDto> items = presenter.loadAllFloppyDisks();
        floppyDiskGrid.getListDataView().setItems(items);
    }

    private void setupFilters() {
        var headerCells = floppyDiskGrid.getHeaderRows()
                                        .getFirst()
                                        .getCells();

        FloppyDiskFilter filter = new FloppyDiskFilter(floppyDiskGrid.getListDataView());

        headerCells.getFirst().setComponent(createFilterHeader("Наименование", filter::setName));
        headerCells.get(1).setComponent(createFilterHeader("Объём (МБ)", filter::setCapacity));
        headerCells.get(2).setComponent(createFilterHeader("Формат", filter::setFormat));
        headerCells.get(3).setComponent(createFilterHeader("Плотность", filter::setDiskDensity));
        headerCells.get(4).setComponent(createFilterHeader("Двусторонняя?", filter::setIsDoubleSided));
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
            var selected = floppyDiskGrid.getSelectedItems();
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

        Button refreshBtn = new Button("Обновить", VaadinIcon.REFRESH.create());
        refreshBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshBtn.addClickListener(e -> refreshGrid());

        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(delBtn);
        buttons.add(refreshBtn);
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
        var dialog = new FloppyDiskDialog(floppyDisk -> {
            presenter.addFloppyDisk(floppyDisk);
            refreshGrid();
        });
        dialog.setHeaderTitle("Добавление новой записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showEditDialog(FloppyDiskDto oldDto) {
        var dialog = new FloppyDiskDialog(newDto -> {
            presenter.updateFloppyDisk(oldDto.id(), newDto);
            refreshGrid();
        }, oldDto);
        dialog.setHeaderTitle("Изменение записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showDelConfirmDialog() {
        var selectedItems = floppyDiskGrid.getSelectedItems();
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
                    .map(FloppyDiskDto::id)
                    .toList();

            presenter.deleteFloppyDisks(ids);
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

    private static class FloppyDiskFilter {
        private final GridListDataView<FloppyDiskDto> dataView;

        private String name;
        private String capacity;
        private String format;
        private String diskDensity;
        private Boolean isDoubleSided;

        public FloppyDiskFilter(GridListDataView<FloppyDiskDto> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setName(String name) {
            this.name = name;
            this.dataView.refreshAll();
        }

        public void setFormat(String format) {
            this.format = format;
            this.dataView.refreshAll();
        }

        public void setDiskDensity(String diskDensity) {
            this.diskDensity = diskDensity;
            this.dataView.refreshAll();
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
            this.dataView.refreshAll();
        }

        public void setIsDoubleSided(String value) {
            if (value == null || value.isEmpty()) {
                this.isDoubleSided = null;
            } else if ("да".contains(value.toLowerCase())) {
                this.isDoubleSided = true;
            } else if ("нет".contains(value.toLowerCase())) {
                this.isDoubleSided = false;
            }
            this.dataView.refreshAll();
        }

        private boolean test(FloppyDiskDto dto) {
            return matches(dto.name(), name)
                    && matches(dto.diskDensity(), diskDensity)
                    && matchesNumeric(dto.capacity(), capacity)
                    && matchesNumeric(dto.format(), format)
                    && matches(dto.diskDensity(), diskDensity)
                    && (isDoubleSided == null || dto.isDoubleSided() == isDoubleSided);
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

    static class FloppyDiskForm extends FormLayout {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private NumberField floppyFormatField;
        private ComboBox<FloppyDensity> floppyDensityBox;
        private Checkbox floppyDoubleSidedCheckbox;

        private Binder<FloppyDiskDto> binder = new Binder<>(FloppyDiskDto.class);
        private Long currentId = null;

        public FloppyDiskForm() {
            setupFields();
            setupBinder();
            add(nameField, capacityField, floppyFormatField, floppyDensityBox, floppyDoubleSidedCheckbox);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        private void setupFields() {
            nameField = new TextArea("Название:");
            nameField.setMinRows(1);
            nameField.setMaxRows(1);
            nameField.setMinLength(1);
            nameField.setMaxLength(30);
            nameField.setClearButtonVisible(true);

            capacityField = new NumberField("Объём:");
            capacityField.setClearButtonVisible(true);
            capacityField.setRequiredIndicatorVisible(true);

            capacityUnitBox = new ComboBox<>();
            capacityUnitBox.setItems(Bytes.values());
            capacityUnitBox.setItemLabelGenerator(Bytes::getLabel);
            capacityUnitBox.setValue(Bytes.MB);
            capacityField.setSuffixComponent(capacityUnitBox);

            floppyFormatField = new NumberField("Размер (в дюймах):");
            floppyFormatField.setSuffixComponent(new Div("\""));
            floppyFormatField.setStepButtonsVisible(true);
            floppyFormatField.setStep(.25);
            floppyFormatField.setClearButtonVisible(true);

            floppyDensityBox = new ComboBox<>("Плотность записи:");
            floppyDensityBox.setItems(FloppyDensity.values());
            floppyDensityBox.setItemLabelGenerator(FloppyDensity::getLabel);
            floppyDensityBox.setClearButtonVisible(true);

            floppyDoubleSidedCheckbox = new Checkbox("Двусторонняя дискета");
            floppyDoubleSidedCheckbox.setValue(false); // По умолчанию односторонняя
        }

        private void setupBinder() {
            // Bind fields to DTO
            binder.forField(nameField)
                    .asRequired("Название обязательно")
                    .withValidator(name -> name.length() <= 30, "Название должно быть до 30 символов")
                    .bind(
                            FloppyDiskDto::name,
                            (dto, value) -> {}
                    );

            binder.forField(capacityField)
                    .asRequired("Объём обязателен")
                    .withValidator(capacity -> capacity > 0, "Объём должен быть положительным")
                    .bind(
                            dto -> dto.capacity() != null ? dto.capacity().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(floppyFormatField)
                    .withValidator(formatInches -> formatInches == null || (formatInches >= 2 && formatInches <= 8),
                            "Принимается размер дискеты от 2 до 8 дюймов")
                    .bind(
                            dto -> dto.format() != null ? dto.format().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(floppyDensityBox)
                    .bind(
                            dto -> dto.diskDensity() != null ? FloppyDensity.valueOfLabel(dto.diskDensity()) : null,
                            (dto, value) -> {}
                    );

            binder.forField(floppyDoubleSidedCheckbox)
                    .bind(
                            FloppyDiskDto::isDoubleSided,
                            (dto, value) -> {}
                    );
        }

        public void setFloppyDisk(FloppyDiskDto floppyDisk) {
            this.currentId = floppyDisk.id();
            binder.readBean(floppyDisk);
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

        public Optional<FloppyDiskDto> getFormDataObject() {
            if (!isValid()) {
                return Optional.empty();
            }

            FloppyDiskDto dto = new FloppyDiskDto(
                    currentId,
                    nameField.getValue(),
                    capacityField.getValue() != null ? toMegabytes(capacityField.getValue().floatValue(), capacityUnitBox.getValue()) : null,
                    floppyFormatField.getValue() != null ? floppyFormatField.getValue().floatValue() : null,
                    floppyDensityBox.getValue() != null ? floppyDensityBox.getValue().name() : null,
                    floppyDoubleSidedCheckbox.getValue()
            );

            return Optional.of(dto);
        }

        private Boolean isFloppyDoubleSided(String value) {
            if (value == null)
                return null;

            return value.equals("Двусторонняя");
        }

        public boolean isValid() {
            return binder.validate().isOk();
        }

        public void clear() {
            currentId = null;
            nameField.clear();
            capacityField.clear();
            floppyFormatField.clear();
            floppyDensityBox.clear();
            floppyDoubleSidedCheckbox.setValue(false);
            binder.validate();
        }
    }

    static class FloppyDiskDialog extends CustomDialog {
        private final FloppyDiskForm form;
        private final SerializableConsumer<FloppyDiskDto> onSaveCallback;
        private boolean isEditMode;

        public FloppyDiskDialog(SerializableConsumer<FloppyDiskDto> onSaveCallback) {
            this(onSaveCallback, null);
        }

        public FloppyDiskDialog(SerializableConsumer<FloppyDiskDto> onSaveCallback, FloppyDiskDto dto) {
            this.onSaveCallback = onSaveCallback;

            form = new FloppyDiskForm();
            isEditMode = false;

            if (dto != null) {
                isEditMode = true;
                form.setFloppyDisk(dto);
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

            form.getFormDataObject().ifPresent(floppyDisk -> {
                try {
                    onSaveCallback.accept(floppyDisk);
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
