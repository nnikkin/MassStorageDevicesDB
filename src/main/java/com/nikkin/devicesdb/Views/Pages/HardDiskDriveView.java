package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Domain.HddInterface;
import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Presenters.HardDiskDrivePresenter;
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
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Route("hdd")
public class HardDiskDriveView extends AppLayout {
    private final HardDiskDrivePresenter presenter;
    private Grid<HardDiskDriveDto> hddGrid;
    private List<Component> buttons;

    public HardDiskDriveView(HardDiskDrivePresenter presenter) {
        this.presenter = presenter;

        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        H1 pageTitle = new H1("Запоминающие устройства - Накопители на жёстких дисках");
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

        layout.add(tableMenu, hddGrid);
    }

    private void initTable() {
        hddGrid = new Grid<>(HardDiskDriveDto.class, false);
        hddGrid.setItems(new ArrayList<>());
        hddGrid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        hddGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        hddGrid.setColumnRendering(ColumnRendering.LAZY);
        hddGrid.setEmptyStateText("В таблице отсутствуют записи.");

        hddGrid.addColumn(HardDiskDriveDto::name)
                .setHeader("")  // иначе при добавлении поиска по столбцу в setupFilters будет NoSuchElementException
                .setAutoWidth(true)
                .setSortable(true);
        hddGrid.addColumn(HardDiskDriveDto::capacity)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        hddGrid.addColumn(HardDiskDriveDto::driveInterface)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        hddGrid.addColumn(HardDiskDriveDto::format)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        hddGrid.addColumn(HardDiskDriveDto::cache)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        hddGrid.addColumn(HardDiskDriveDto::rpm)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        hddGrid.addColumn(HardDiskDriveDto::powerConsumption)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);

        hddGrid.addSelectionListener(
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
        hddGrid.deselectAll();
        fillTable();
    }

    private void fillTable() {
        List<HardDiskDriveDto> items = presenter.loadAllHdd();
        hddGrid.getListDataView().setItems(items);
    }

    private void setupFilters() {
        var headerCells = hddGrid.getHeaderRows()
                                        .getFirst()
                                        .getCells();

        HardDiskDriveFilter filter = new HardDiskDriveFilter(hddGrid.getListDataView());

        headerCells.getFirst().setComponent(createFilterHeader("Наименование", filter::setName));
        headerCells.get(1).setComponent(createFilterHeader("Объём (МБ)", filter::setCapacity));
        headerCells.get(2).setComponent(createFilterHeader("Интерфейс:", filter::setDriveInterface));
        headerCells.get(3).setComponent(createFilterHeader("Формат:", filter::setFormat));
        headerCells.get(4).setComponent(createFilterHeader("Размер кэша (МБ):", filter::setCache));
        headerCells.get(4).setComponent(createFilterHeader("RPM (об/мин):", filter::setRpm));
        headerCells.get(4).setComponent(createFilterHeader("Энергопотребление (Ватт):", filter::setPowerConsumption));
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
            var selected = hddGrid.getSelectedItems();
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
        var dialog = new HardDiskDriveDialog(hdd -> {
            presenter.addHdd(hdd);
            refreshGrid();
        });
        dialog.setHeaderTitle("Добавление новой записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showEditDialog(HardDiskDriveDto oldDto) {
        var dialog = new HardDiskDriveDialog(newDto -> {
            presenter.updateHdd(oldDto.id(), newDto);
            refreshGrid();
        }, oldDto);
        dialog.setHeaderTitle("Изменение записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showDelConfirmDialog() {
        var selectedItems = hddGrid.getSelectedItems();
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
                    .map(HardDiskDriveDto::id)
                    .toList();

            presenter.deleteHdd(ids);
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

    private static class HardDiskDriveFilter {
        private final GridListDataView<HardDiskDriveDto> dataView;

        private String name;
        private String capacity;
        private String format;
        private String driveInterface;
        private String rpm;
        private String cache;
        private String powerConsumption;

        public HardDiskDriveFilter(GridListDataView<HardDiskDriveDto> dataView) {
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

        public void setCapacity(String capacity) {
            this.capacity = capacity;
            this.dataView.refreshAll();
        }

        public void setDriveInterface(String driveInterface) {
            this.driveInterface = driveInterface;
            this.dataView.refreshAll();
        }

        public void setRpm(String rpm) {
            this.rpm = rpm;
            this.dataView.refreshAll();
        }

        public void setCache(String cache) {
            this.cache = cache;
            this.dataView.refreshAll();
        }

        public void setPowerConsumption(String powerConsumption) {
            this.powerConsumption = powerConsumption;
            this.dataView.refreshAll();
        }

        private boolean test(HardDiskDriveDto dto) {
            return matches(dto.name(), name)
                    && matchesNumeric(dto.capacity(), capacity)
                    && matches(dto.format(), format)
                    && matchesNumeric(dto.powerConsumption(), powerConsumption)
                    && matchesNumeric(dto.rpm(), rpm)
                    && matchesNumeric(dto.cache(), cache)
                    && matches(dto.driveInterface(), driveInterface);
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

        private boolean matchesNumeric(Integer value, String searchTerm) {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return true;
            }

            return String.valueOf(value).startsWith(searchTerm);
        }
    }

    static class HardDiskDriveForm extends FormLayout {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> hddInterfaceComboBox;
        private RadioButtonGroup<String> hddFormatRadio;
        private NumberField hddRpmField;
        private NumberField hddCacheField;
        private NumberField powerConsumptionField;

        private Binder<HardDiskDriveDto> binder = new Binder<>(HardDiskDriveDto.class);
        private Long currentId = null;

        public HardDiskDriveForm() {
            setupFields();
            setupBinder();
            add(nameField, capacityField, hddInterfaceComboBox, hddFormatRadio, hddRpmField,
                    hddCacheField);
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

            hddInterfaceComboBox = new ComboBox<>("Интерфейс:");
            hddInterfaceComboBox.setItems("SATA","SCSI", "SAS", "IDE", "ESDI");
            hddInterfaceComboBox.setRequiredIndicatorVisible(true);
            hddInterfaceComboBox.setClearButtonVisible(true);

            hddFormatRadio = new RadioButtonGroup<>("Формат:");
            hddFormatRadio.setItems("2.5\"", "3.5\"");

            hddRpmField = new NumberField("RPM (оборотов в минуту):");
            hddRpmField.setSuffixComponent(new Div("об/мин"));
            hddRpmField.setClearButtonVisible(true);

            hddCacheField = new NumberField("Размер кэша (МБ):");
            hddCacheField.setSuffixComponent(new Div("МБ"));
            hddCacheField.setClearButtonVisible(true);

            powerConsumptionField = new NumberField("Энергопотребление (Вт):");
            powerConsumptionField.setSuffixComponent(new Div("Вт"));
            powerConsumptionField.setClearButtonVisible(true);
        }

        private void setupBinder() {
            // Bind fields to DTO
            binder.forField(nameField)
                    .asRequired("Название обязательно")
                    .withValidator(name -> name.length() <= 30, "Название должно быть до 30 символов")
                    .bind(
                            HardDiskDriveDto::name,
                            (dto, value) -> {}
                    );

            binder.forField(capacityField)
                    .asRequired("Объём обязателен")
                    .withValidator(capacity -> capacity > 0, "Объём должен быть положительным")
                    .bind(
                            dto -> dto.capacity() != null ? dto.capacity().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(hddCacheField)
                    .withValidator(cache -> cache == null || cache > 0,
                            "Размер кэша должен быть положительным")
                    .bind(
                            dto -> dto.cache() != null ? dto.cache().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(hddRpmField)
                    .withValidator(rpm -> rpm == null || rpm > 0,
                            "Значение RPM должно быть положительным")
                    .bind(
                            dto -> dto.rpm() != null ? dto.rpm().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(powerConsumptionField)
                    .withValidator(power -> power == null || power > 0,
                            "Значение энергопотребления должно быть положительным")
                    .bind(
                            dto -> dto.powerConsumption() != null ? dto.powerConsumption().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(hddInterfaceComboBox)
                    .asRequired("Интерфейс обязателен")
                    .bind(
                            HardDiskDriveDto::driveInterface,
                            (dto, value) -> {}
                    );

            binder.forField(hddFormatRadio)
                    .asRequired("Формат обязателен")
                    .bind(
                            HardDiskDriveDto::format,
                            (dto, value) -> {}
                    );
        }

        public void setHardDiskDrive(HardDiskDriveDto hdd) {
            this.currentId = hdd.id();
            binder.readBean(hdd);
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

        public Optional<HardDiskDriveDto> getFormDataObject() {
            if (!isValid()) {
                return Optional.empty();
            }

            HardDiskDriveDto dto = new HardDiskDriveDto(
                    currentId,
                    nameField.getValue(),
                    capacityField.getValue() != null ? toMegabytes(capacityField.getValue().floatValue(), capacityUnitBox.getValue()) : null,
                    hddInterfaceComboBox.getValue(),
                    hddFormatRadio.getValue(),
                    hddRpmField.getValue() != null ? hddRpmField.getValue().intValue() : null,
                    hddCacheField.getValue() != null ? hddCacheField.getValue().intValue() : null,
                    powerConsumptionField.getValue() != null ? powerConsumptionField.getValue().floatValue() : null
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
            hddRpmField.clear();
            hddCacheField.clear();
            hddInterfaceComboBox.clear();
            hddFormatRadio.setValue("3.5\"");
            powerConsumptionField.clear();
            binder.validate();
        }
    }

    static class HardDiskDriveDialog extends CustomDialog {
        private final HardDiskDriveForm form;
        private final SerializableConsumer<HardDiskDriveDto> onSaveCallback;
        private boolean isEditMode;

        public HardDiskDriveDialog(SerializableConsumer<HardDiskDriveDto> onSaveCallback) {
            this(onSaveCallback, null);
        }

        public HardDiskDriveDialog(SerializableConsumer<HardDiskDriveDto> onSaveCallback, HardDiskDriveDto dto) {
            this.onSaveCallback = onSaveCallback;

            form = new HardDiskDriveForm();
            isEditMode = false;

            if (dto != null) {
                isEditMode = true;
                form.setHardDiskDrive(dto);
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

            form.getFormDataObject().ifPresent(hdd -> {
                try {
                    onSaveCallback.accept(hdd);
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
