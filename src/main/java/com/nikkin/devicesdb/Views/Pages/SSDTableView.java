package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Entities.SolidStateDrive;
import com.nikkin.devicesdb.Services.SSDService;
import com.nikkin.devicesdb.Views.BaseForm;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnRendering;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Optional;

@Route("ssd")
public class SSDTableView extends BaseTableView<SolidStateDrive, SolidStateDriveDto> {
    public SSDTableView(SSDService service) {
        super("Твердотельные накопители", service);
    }

    @Override
    protected BaseForm<SolidStateDriveDto> createForm() {
        return new SolidStateDriveForm();
    }

    @Override
    protected void initTable() {
        var ssdGrid = new Grid<>(SolidStateDriveDto.class, false);

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
                    {
                        changeEditBtnState(false);
                        changeDelBtnState(false);
                    }
                    else
                    {
                        changeDelBtnState(true);
                        changeEditBtnState(true);
                    }
                }
        );

        setGrid(ssdGrid);
    }

    @Override
    protected void setupFilters() {
        var ssdGrid = getGrid();
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

    static class SolidStateDriveForm extends BaseForm<SolidStateDriveDto> {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> ssdInterfaceComboBox;
        private ComboBox<String> ssdNandTypeBox;
        private NumberField ssdTbwField;
        private NumberField writeSpeedField;
        private NumberField readSpeedField;
        private NumberField powerConsumptionField;

        private Binder<SolidStateDriveDto> binder;
        private Long currentId = null;

        public SolidStateDriveForm() {
            super();

            binder = new Binder<>(SolidStateDriveDto.class);
            setBinder(binder);

            setupFields();
            setupBinder();

            HorizontalLayout capacityFieldLayout = new HorizontalLayout();
            capacityFieldLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
            capacityFieldLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
            capacityFieldLayout.setMargin(false);
            capacityFieldLayout.add(capacityField, capacityUnitBox);

            add(nameField, capacityFieldLayout, ssdInterfaceComboBox, ssdNandTypeBox,
                    ssdTbwField, writeSpeedField, readSpeedField, powerConsumptionField);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        @Override
        protected void setDto(SolidStateDriveDto dto) {
            this.currentId = dto.id();
            binder.readBean(dto);
        }

        @Override
        protected void setupFields() {
            nameField = new TextArea("Название:");
            nameField.setMinRows(1);
            nameField.setMaxRows(1);
            nameField.setMinLength(1);
            nameField.setMaxLength(30);
            nameField.setClearButtonVisible(true);

            capacityField = new NumberField("Объём:");
            capacityField.setClearButtonVisible(true);
            capacityField.setRequiredIndicatorVisible(true);
            capacityField.setStepButtonsVisible(true);
            capacityField.setWidthFull();

            capacityUnitBox = new ComboBox<>();
            capacityUnitBox.setItems(Bytes.values());
            capacityUnitBox.setItemLabelGenerator(Bytes::getLabel);
            capacityUnitBox.setValue(Bytes.GB);

            ssdInterfaceComboBox = new ComboBox<>("Интерфейс:");
            ssdInterfaceComboBox.setItems("SATA", "PCI Express", "SAS", "M.2",
                    "NVMe", "AHCI");
            ssdInterfaceComboBox.setClearButtonVisible(true);

            ssdNandTypeBox = new ComboBox<>("Тип NAND:");
            ssdNandTypeBox.setItems(
                    "SLC",
                    "MLC",
                    "TLC",
                    "QLC"
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

        @Override
        protected void setupBinder() {
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

        @Override
        protected Optional<SolidStateDriveDto> getFormDataObject() {
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

        @Override
        protected void clearFields() {
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
}