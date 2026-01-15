package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Entities.HardDiskDrive;
import com.nikkin.devicesdb.Services.HDDService;
import com.nikkin.devicesdb.Views.BaseForm;
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
public final class HDDView extends BaseView<HardDiskDrive, HardDiskDriveDto> {
    public HDDView(HDDService service) {
        super("Накопители на жёстких дисках", service);
    }

    @Override
    protected BaseForm<HardDiskDriveDto> createForm() {
        return new HardDiskDriveForm();
    }

    @Override
    protected void initTable() {
        var hddGrid = new Grid<>(HardDiskDriveDto.class, false);

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

        setGrid(hddGrid);
    }

    @Override
    protected void setupFilters() {
        var hddGrid = getGrid();
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

    static class HardDiskDriveForm extends BaseForm<HardDiskDriveDto> {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> hddInterfaceComboBox;
        private RadioButtonGroup<String> hddFormatRadio;
        private NumberField hddRpmField;
        private NumberField hddCacheField;
        private NumberField powerConsumptionField;

        private Binder<HardDiskDriveDto> binder;
        private Long currentId = null;

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

            add(nameField, capacityFieldLayout, hddInterfaceComboBox, hddFormatRadio, hddRpmField,
                    hddCacheField);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        @Override
        protected void setDto(HardDiskDriveDto dto) {
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
            capacityUnitBox.setValue(Bytes.MB);

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

        @Override
        protected void setupBinder() {
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

        @Override
        protected Optional<HardDiskDriveDto> getFormDataObject() {
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

        @Override
        protected void clearFields() {
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
}
