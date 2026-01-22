package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Bytes;
import com.nikkin.devicesdb.Dto.ComputerDto;
import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Services.ComputerService;
import com.nikkin.devicesdb.Services.HddService;
import com.nikkin.devicesdb.Views.BaseForm;
import com.nikkin.devicesdb.Views.BaseTableView;
import com.nikkin.devicesdb.Views.BytesConverter;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnRendering;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Optional;

@Route("hdd")
public final class HddTableView extends BaseTableView<HardDiskDriveDto> {
    protected static ComputerService computerService;

    public HddTableView(HddService service, ComputerService computerService) {
        super("Накопители на жёстких дисках", service);
        this.computerService = computerService;
    }

    @Override
    protected BaseForm<HardDiskDriveDto> createForm() {
        return new HardDiskDriveForm();
    }

    @Override
    protected void initTable() {
        grid = new Grid<>(HardDiskDriveDto.class, false);

        grid.setItems(new ArrayList<>());
        grid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setColumnRendering(ColumnRendering.LAZY);
        grid.setEmptyStateText("В таблице отсутствуют записи.");

        grid.addColumn(dto -> dto.manufacturer().isEmpty() ? "—" : dto.manufacturer())
                .setHeader("")  // иначе при добавлении поиска по столбцу в setupFilters будет NoSuchElementException
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(dto -> (int)Math.ceil(BytesConverter.convert(dto.capacity(), Bytes.MiB, Bytes.GiB)))
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(dto -> dto.driveInterface() == null ? "—" : dto.driveInterface())
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(dto -> dto.format() == null ? "—" : dto.format())
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(dto -> dto.powerConsumption() != null ? dto.powerConsumption() : "—")
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(dto -> dto.computerId() != null ? computerService.getById(dto.computerId()).name() : "Не назначен")
                .setHeader("Компьютер")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addSelectionListener(
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
    }

    @Override
    protected void setupFilters() {
        var headerCells = grid.getHeaderRows()
                                        .getFirst()
                                        .getCells();

        HardDiskDriveFilter filter = new HardDiskDriveFilter(grid.getListDataView());

        headerCells.getFirst().setComponent(createFilterHeader("Производитель", filter::setManufacturer));
        headerCells.get(1).setComponent(createFilterHeader("Объём (ГБ)", filter::setCapacity));
        headerCells.get(2).setComponent(createFilterHeader("Интерфейс", filter::setDriveInterface));
        headerCells.get(3).setComponent(createFilterHeader("Формат", filter::setFormat));
        headerCells.get(4).setComponent(createFilterHeader("Энергопотребление (Ватт)", filter::setPowerConsumption));
        headerCells.getLast().setComponent(createFilterHeader("Компьютер", filter::setComputerName));
    }

    private static class HardDiskDriveFilter {
        private final GridListDataView<HardDiskDriveDto> dataView;

        private String manufacturer;
        private String capacity;
        private String format;
        private String driveInterface;
        private String powerConsumption;
        private String computerName;

        public HardDiskDriveFilter(GridListDataView<HardDiskDriveDto> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
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

        public void setPowerConsumption(String powerConsumption) {
            this.powerConsumption = powerConsumption;
            this.dataView.refreshAll();
        }

        public void setComputerName(String computerName) {
            this.computerName = computerName;
            this.dataView.refreshAll();
        }

        private boolean test(HardDiskDriveDto dto) {
            String compName = "Не назначен";
            if (dto.computerId() != null) {
                compName = computerService.getById(dto.computerId()).name();
            }

            return matches(dto.manufacturer(), manufacturer)
                    && matches(compName, computerName)
                    && matchesNumeric(dto.capacity(), capacity)
                    && matches(dto.format(), format)
                    && matchesNumeric(dto.powerConsumption(), powerConsumption)
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
    }

    static class HardDiskDriveForm extends BaseForm<HardDiskDriveDto> {
        private TextArea manufacturerField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> hddInterfaceComboBox;
        private RadioButtonGroup<String> hddFormatRadio;
        private NumberField powerConsumptionField;
        private ComboBox<ComputerDto> computersField;

        private Binder<HardDiskDriveDto> binder;
        private Integer currentId = null;

        public HardDiskDriveForm() {
            super();

            binder = new Binder<>(HardDiskDriveDto.class);
            setBinder(binder);

            setupFields();
            setupBinder();

            HorizontalLayout capacityFieldLayout = new HorizontalLayout();
            capacityFieldLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
            capacityFieldLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
            capacityFieldLayout.setMargin(false);
            capacityFieldLayout.add(capacityField, capacityUnitBox);

            add(manufacturerField, capacityFieldLayout, hddInterfaceComboBox, hddFormatRadio, computersField);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        @Override
        protected void setDto(HardDiskDriveDto dto) {
            this.currentId = dto.id();
            binder.readBean(dto);
        }

        @Override
        protected void setupFields() {
            manufacturerField = new TextArea("Производитель:");
            manufacturerField.setMinRows(1);
            manufacturerField.setMaxRows(1);
            manufacturerField.setMinLength(1);
            manufacturerField.setMaxLength(30);
            manufacturerField.setClearButtonVisible(true);

            capacityField = new NumberField("Объём:");
            capacityField.setClearButtonVisible(true);
            capacityField.setRequiredIndicatorVisible(true);
            capacityField.setStepButtonsVisible(true);
            capacityField.setWidthFull();

            capacityUnitBox = new ComboBox<>();
            capacityUnitBox.setItems(Bytes.values());
            capacityUnitBox.setItemLabelGenerator(Bytes::getLabel);
            capacityUnitBox.setValue(Bytes.GiB);

            hddInterfaceComboBox = new ComboBox<>("Интерфейс:");
            hddInterfaceComboBox.setItems("SATA","SCSI", "SAS", "IDE", "ESDI");
            hddInterfaceComboBox.setClearButtonVisible(true);

            hddFormatRadio = new RadioButtonGroup<>("Формат:");
            hddFormatRadio.setItems("2.5\"", "3.5\"");

            powerConsumptionField = new NumberField("Энергопотребление (Вт):");
            powerConsumptionField.setSuffixComponent(new Div("Вт"));
            powerConsumptionField.setClearButtonVisible(true);

            computersField = new ComboBox<>("Компьютер:");
            computersField.setItems(computerService.getAll());
            computersField.setItemLabelGenerator(ComputerDto::name);
            computersField.setClearButtonVisible(true);
            computersField.setRequiredIndicatorVisible(true);
        }

        @Override
        protected void setupBinder() {
            // Bind fields to DTO
            binder.forField(manufacturerField)
                    .withValidator(name -> name.length() <= 30, "Название производителя должно быть до 30 символов")
                    .bind(
                            HardDiskDriveDto::manufacturer,
                            (dto, value) -> {}
                    );

            binder.forField(capacityField)
                    .asRequired("Объём обязателен")
                    .withValidator(capacity -> capacity > 0, "Объём должен быть положительным")
                    .bind(
                            dto -> dto.capacity() != null ? dto.capacity().doubleValue() : null,
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
                    .bind(
                            HardDiskDriveDto::driveInterface,
                            (dto, value) -> {}
                    );

            binder.forField(hddFormatRadio)
                    .bind(
                            HardDiskDriveDto::format,
                            (dto, value) -> {}
                    );

            binder.forField(computersField)
                    .asRequired("Связка с компьютером обязательна")
                    .bind(
                            dto -> dto.computerId() == null ? null :
                                    computerService.getById(dto.computerId()),
                            (dto, value) -> {}
                    );
        }

        @Override
        protected Optional<HardDiskDriveDto> getFormDataObject() {
            if (!isValid()) {
                return Optional.empty();
            }

            HardDiskDriveDto dto = new HardDiskDriveDto(
                    currentId,
                    manufacturerField.getValue(),
                    capacityField.getValue() != null ? toMegabytes(capacityField.getValue().floatValue(), capacityUnitBox.getValue()) : null,
                    hddInterfaceComboBox.getValue(),
                    hddFormatRadio.getValue(),
                    powerConsumptionField.getValue() != null ? powerConsumptionField.getValue().floatValue() : null,
                    computersField.getValue().id()
            );

            return Optional.of(dto);
        }

        @Override
        protected void clearFields() {
            currentId = null;
            manufacturerField.clear();
            capacityField.clear();
            hddInterfaceComboBox.clear();
            hddFormatRadio.setValue("3.5\"");
            powerConsumptionField.clear();
            computersField.clear();
            binder.validate();
        }
    }
}
