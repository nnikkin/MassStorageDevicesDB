package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Domain.SsdInterface;
import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Presenters.SolidStateDrivePresenter;
import com.nikkin.devicesdb.Views.CustomDialog;
import com.nikkin.devicesdb.Views.NavBar;
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
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Route("ssd")
public class SolidStateDriveView extends AppLayout {
    private final SolidStateDrivePresenter presenter;
    private Grid<SolidStateDriveDto> ssdGrid;
    private List<Component> buttons;

    public SolidStateDriveView(SolidStateDrivePresenter presenter) {
        this.presenter = presenter;

        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        H1 pageTitle = new H1("Запоминающие устройства - Твердотельные накопители");
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

        layout.add(tableMenu, ssdGrid);
    }

    private void initTable() {
        ssdGrid = new Grid<>(SolidStateDriveDto.class, false);
        ssdGrid.setItems(new ArrayList<>());
        ssdGrid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        ssdGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        ssdGrid.setColumnRendering(ColumnRendering.LAZY);
        ssdGrid.setEmptyStateText("В таблице отсутствуют записи.");

        ssdGrid.addColumn(SolidStateDriveDto::name)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ssdGrid.addColumn(SolidStateDriveDto::capacity)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ssdGrid.addColumn(SolidStateDriveDto::driveInterface)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ssdGrid.addColumn(SolidStateDriveDto::nandType)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ssdGrid.addColumn(SolidStateDriveDto::tbw)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ssdGrid.addColumn(SolidStateDriveDto::writeSpeed)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ssdGrid.addColumn(SolidStateDriveDto::readSpeed)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ssdGrid.addColumn(SolidStateDriveDto::powerConsumption)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);

        ssdGrid.addSelectionListener(
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
        ssdGrid.deselectAll();
        fillTable();
    }

    private void fillTable() {
        List<SolidStateDriveDto> items = presenter.loadAllSsd();
        ssdGrid.getListDataView().setItems(items);
    }

    private void setupFilters() {
        var headerCells = ssdGrid.getHeaderRows()
                .getFirst()
                .getCells();

        SolidStateDriveFilter filter = new SolidStateDriveFilter(ssdGrid.getListDataView());

        headerCells.get(0).setComponent(createFilterHeader("Наименование", filter::setName));
        headerCells.get(1).setComponent(createFilterHeader("Объём (МБ)", filter::setCapacity));
        headerCells.get(2).setComponent(createFilterHeader("Интерфейс", filter::setDriveInterface));
        headerCells.get(3).setComponent(createFilterHeader("Тип NAND", filter::setNandType));
        headerCells.get(4).setComponent(createFilterHeader("TBW (ТБ)", filter::setTbw));
        headerCells.get(5).setComponent(createFilterHeader("Скорость записи (МБ/с)", filter::setWriteSpeed));
        headerCells.get(6).setComponent(createFilterHeader("Скорость чтения (МБ/с)", filter::setReadSpeed));
        headerCells.get(7).setComponent(createFilterHeader("Энергопотребление (Вт)", filter::setPowerConsumption));
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
            var selected = ssdGrid.getSelectedItems();
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
        var dialog = new SolidStateDriveDialog(ssd -> {
            presenter.addSsd(ssd);
            refreshGrid();
        });
        dialog.setHeaderTitle("Добавление новой записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showEditDialog(SolidStateDriveDto oldDto) {
        var dialog = new SolidStateDriveDialog(newDto -> {
            presenter.updateSsd(oldDto.id(), newDto);
            refreshGrid();
        }, oldDto);
        dialog.setHeaderTitle("Изменение записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showDelConfirmDialog() {
        var selectedItems = ssdGrid.getSelectedItems();
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
                    .map(SolidStateDriveDto::id)
                    .toList();

            presenter.deleteSsd(ids);
            refreshGrid();
            confirmDialog.close();

            Notification.show(
                    "Удалено записей: " + ids.size(),
                    3000,
                    Notification.Position.BOTTOM_END
            ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
    }

    private void showErrorDialog(String message) {
        Div textDiv = new Div(message);

        CustomDialog errorDialog = new CustomDialog("Ошибка", textDiv);
        errorDialog.setCancelButtonVisible(false);

        errorDialog.open();

        errorDialog.addOkClickListener(e -> errorDialog.close());
    }

    private static class SolidStateDriveFilter {
        private final GridListDataView<SolidStateDriveDto> dataView;

        private String name;
        private String capacity;
        private String driveInterface;
        private String nandType;
        private String tbw;
        private String writeSpeed;
        private String readSpeed;
        private String powerConsumption;

        public SolidStateDriveFilter(GridListDataView<SolidStateDriveDto> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setName(String name) {
            this.name = name;
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

        public void setNandType(String nandType) {
            this.nandType = nandType;
            this.dataView.refreshAll();
        }

        public void setTbw(String tbw) {
            this.tbw = tbw;
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

        public void setPowerConsumption(String powerConsumption) {
            this.powerConsumption = powerConsumption;
            this.dataView.refreshAll();
        }

        private boolean test(SolidStateDriveDto dto) {
            return matches(dto.name(), name)
                    && matchesNumeric(dto.capacity(), capacity)
                    && matches(dto.driveInterface(), driveInterface)
                    && matches(dto.nandType(), nandType)
                    && matchesNumeric(dto.tbw(), tbw)
                    && matchesNumeric(dto.writeSpeed(), writeSpeed)
                    && matchesNumeric(dto.readSpeed(), readSpeed)
                    && matchesNumeric(dto.powerConsumption(), powerConsumption);
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || (value != null && value.toLowerCase().contains(searchTerm.toLowerCase()));
        }

        private boolean matchesNumeric(Float value, String searchTerm) {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return true;
            }
            return value != null && String.valueOf(value).startsWith(searchTerm);
        }

        private boolean matchesNumeric(Integer value, String searchTerm) {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return true;
            }
            return value != null && String.valueOf(value).startsWith(searchTerm);
        }
    }

    static class SolidStateDriveForm extends FormLayout {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> ssdInterfaceComboBox;
        private ComboBox<String> ssdNandTypeBox;
        private NumberField ssdTbwField;
        private NumberField writeSpeedField;
        private NumberField readSpeedField;
        private NumberField powerConsumptionField;

        private Binder<SolidStateDriveDto> binder = new Binder<>(SolidStateDriveDto.class);
        private Long currentId = null;

        public SolidStateDriveForm() {
            setupFields();
            setupBinder();
            add(nameField, capacityField, ssdInterfaceComboBox, ssdNandTypeBox,
                    ssdTbwField, writeSpeedField, readSpeedField, powerConsumptionField);
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
            capacityUnitBox.setValue(Bytes.GB);
            capacityField.setSuffixComponent(capacityUnitBox);

            ssdInterfaceComboBox = new ComboBox<>("Интерфейс:");
            ssdInterfaceComboBox.setItems("SATA", "PCI Express", "SAS", "M.2",
                    "NVMe", "AHCI");
            ssdInterfaceComboBox.setRequiredIndicatorVisible(true);
            ssdInterfaceComboBox.setClearButtonVisible(true);

            ssdNandTypeBox = new ComboBox<>("Тип NAND:");
            ssdNandTypeBox.setItems(
                    "SLC (Single Level Cell / 1 бит)",
                    "MLC (Multi Level Cell / 2 бита)",
                    "TLC (Triple Level Cell / 3 бита)",
                    "QLC (Quad Level Cell / 4 бита)"
            );
            ssdNandTypeBox.setClearButtonVisible(true);

            ssdTbwField = new NumberField("Максимальный ресурс записи:");
            ssdTbwField.setSuffixComponent(new Div("ТБ"));
            ssdTbwField.setClearButtonVisible(true);

            writeSpeedField = new NumberField("Скорость записи:");
            writeSpeedField.setSuffixComponent(new Div("МБ/с"));
            writeSpeedField.setClearButtonVisible(true);

            readSpeedField = new NumberField("Скорость чтения:");
            readSpeedField.setSuffixComponent(new Div("МБ/с"));
            readSpeedField.setClearButtonVisible(true);

            powerConsumptionField = new NumberField("Энергопотребление:");
            powerConsumptionField.setSuffixComponent(new Div("Вт"));
            powerConsumptionField.setClearButtonVisible(true);
        }

        private void setupBinder() {
            binder.forField(nameField)
                    .asRequired("Название обязательно")
                    .withValidator(name -> name.length() <= 30, "Название должно быть до 30 символов")
                    .bind(
                            SolidStateDriveDto::name,
                            (dto, value) -> {}
                    );

            binder.forField(capacityField)
                    .asRequired("Объём обязателен")
                    .withValidator(capacity -> capacity > 0, "Объём должен быть положительным")
                    .bind(
                            dto -> dto.capacity() != null ? dto.capacity().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(ssdInterfaceComboBox)
                    .asRequired("Интерфейс обязателен")
                    .bind(
                            dto -> dto.driveInterface() != null ? dto.driveInterface() : null,
                            (dto, value) -> {}
                    );

            binder.forField(ssdNandTypeBox)
                    .bind(
                            SolidStateDriveDto::nandType,
                            (dto, value) -> {}
                    );

            binder.forField(ssdTbwField)
                    .withValidator(tbw -> tbw == null || tbw > 0, "TBW должен быть положительным")
                    .bind(
                            dto -> dto.tbw() != null ? dto.tbw().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(writeSpeedField)
                    .withValidator(speed -> speed == null || speed > 0, "Скорость должна быть положительной")
                    .bind(
                            dto -> dto.writeSpeed() != null ? dto.writeSpeed().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(readSpeedField)
                    .withValidator(speed -> speed == null || speed > 0, "Скорость должна быть положительной")
                    .bind(
                            dto -> dto.readSpeed() != null ? dto.readSpeed().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(powerConsumptionField)
                    .withValidator(power -> power == null || power > 0, "Энергопотребление должно быть положительным")
                    .bind(
                            dto -> dto.powerConsumption() != null ? dto.powerConsumption().doubleValue() : null,
                            (dto, value) -> {}
                    );
        }

        private SsdInterface findSsdInterface(String value) {
            for (SsdInterface si : SsdInterface.values()) {
                if (si.getLabel().equals(value) || si.name().equals(value)) {
                    return si;
                }
            }
            return null;
        }

        public void setSolidStateDrive(SolidStateDriveDto ssd) {
            this.currentId = ssd.id();
            binder.readBean(ssd);
        }

        private float toMegabytes(float value, Bytes unit) {
            if (unit == Bytes.MB)
                return value;
            if (unit.getRank() > Bytes.MB.getRank())
                return (float) (value * Math.pow(1024, unit.getRank() - Bytes.MB.getRank()));
            if (unit == Bytes.BIT)
                return (float) ((value / 8) / Math.pow(1024, Bytes.MB.getRank() - 1));
            return (float) (value / Math.pow(1024, Bytes.MB.getRank() - unit.getRank()));
        }

        public Optional<SolidStateDriveDto> getFormDataObject() {
            if (!isValid()) {
                return Optional.empty();
            }

            SolidStateDriveDto dto = new SolidStateDriveDto(
                    currentId,
                    nameField.getValue(),
                    ssdInterfaceComboBox.getValue() != null ? ssdInterfaceComboBox.getValue() : null,
                    capacityField.getValue() != null ? toMegabytes(capacityField.getValue().floatValue(), capacityUnitBox.getValue()) : null,
                    ssdNandTypeBox.getValue(),
                    ssdTbwField.getValue() != null ? ssdTbwField.getValue().intValue() : null,
                    writeSpeedField.getValue() != null ? writeSpeedField.getValue().floatValue() : null,
                    readSpeedField.getValue() != null ? readSpeedField.getValue().floatValue() : null,
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
            ssdInterfaceComboBox.clear();
            ssdNandTypeBox.clear();
            ssdTbwField.clear();
            writeSpeedField.clear();
            readSpeedField.clear();
            powerConsumptionField.clear();
            binder.validate();
        }
    }

    static class SolidStateDriveDialog extends CustomDialog {
        private final SolidStateDriveForm form;
        private final SerializableConsumer<SolidStateDriveDto> onSaveCallback;
        private boolean isEditMode;

        public SolidStateDriveDialog(SerializableConsumer<SolidStateDriveDto> onSaveCallback) {
            this(onSaveCallback, null);
        }

        public SolidStateDriveDialog(SerializableConsumer<SolidStateDriveDto> onSaveCallback, SolidStateDriveDto dto) {
            this.onSaveCallback = onSaveCallback;

            form = new SolidStateDriveForm();
            isEditMode = false;

            if (dto != null) {
                isEditMode = true;
                form.setSolidStateDrive(dto);
            }

            addToDialogBody(form);
        }

        public void save() {
            if (!form.isValid()) {
                Notification notification = Notification.show(
                        "Пожалуйста, исправьте ошибки в форме",
                        3000,
                        Notification.Position.MIDDLE
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            form.getFormDataObject().ifPresent(ssd -> {
                try {
                    onSaveCallback.accept(ssd);
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