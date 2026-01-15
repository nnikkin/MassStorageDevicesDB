package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import com.nikkin.devicesdb.Services.RAMService;
import com.nikkin.devicesdb.Views.BaseForm;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Optional;

@Route("ram")
public class RAMTableView extends BaseTableView<RandomAccessMemory, RandomAccessMemoryDto> {
    public RAMTableView(RAMService service) {
        super("ОЗУ", service);
    }

    @Override
    protected BaseForm<RandomAccessMemoryDto> createForm() {
        return new RandomAccessMemoryForm();
    }

    @Override
    protected void initTable() {
        var ramGrid = new Grid<>(RandomAccessMemoryDto.class, false);

        ramGrid.setItems(new ArrayList<>());
        ramGrid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        ramGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        ramGrid.setColumnRendering(ColumnRendering.LAZY);
        ramGrid.setEmptyStateText("В таблице отсутствуют записи.");

        ramGrid.addColumn(RandomAccessMemoryDto::name)
                .setHeader("")  // иначе при добавлении поиска по столбцу в setupFilters будет NoSuchElementException
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(dto -> String.format("%.2f", dto.capacity()))
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(RandomAccessMemoryDto::manufacturer)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(RandomAccessMemoryDto::model)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(RandomAccessMemoryDto::memoryType)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(RandomAccessMemoryDto::moduleType)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(RandomAccessMemoryDto::casLatency)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(dto -> String.format("%.2f", dto.frequencyMhz()))
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(dto ->
                        dto.supportsEcc() ? "Да" : "Нет")
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);


        ramGrid.addSelectionListener(
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

        setGrid(ramGrid);
    }

    @Override
    protected void setupFilters() {
        var ramGrid = getGrid();
        var headerCells = ramGrid.getHeaderRows()
                                        .getFirst()
                                        .getCells();

        RandomAccessMemoryFilter filter = new RandomAccessMemoryFilter(ramGrid.getListDataView());

        headerCells.getFirst().setComponent(createFilterHeader("Наименование", filter::setName));
        headerCells.get(1).setComponent(createFilterHeader("Объём (МБ)", filter::setCapacity));
        headerCells.get(2).setComponent(createFilterHeader("Производитель", filter::setManufacturer));
        headerCells.get(3).setComponent(createFilterHeader("Модель", filter::setModel));
        headerCells.get(4).setComponent(createFilterHeader("Вид памяти", filter::setMemoryType));
        headerCells.get(5).setComponent(createFilterHeader("Вид модуля", filter::setModuleType));
        headerCells.get(6).setComponent(createFilterHeader("CAS-латентность", filter::setCasLatency));
        headerCells.get(7).setComponent(createFilterHeader("Тактовая частота (МГц)", filter::setFrequencyMhz));
        headerCells.getLast().setComponent(createFilterHeader("Поддержка ECC", filter::setSupportsEcc));
    }

    private static class RandomAccessMemoryFilter {
        private final GridListDataView<RandomAccessMemoryDto> dataView;

        private String name;
        private String model;
        private String manufacturer;
        private String capacity;
        private String memoryType;
        private String moduleType;
        private String casLatency;
        private String frequencyMhz;
        private Boolean supportsEcc;

        public RandomAccessMemoryFilter(GridListDataView<RandomAccessMemoryDto> dataView) {
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

        public void setModel(String model) {
            this.model = model;
            this.dataView.refreshAll();
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            this.dataView.refreshAll();
        }

        public void setMemoryType(String memoryType) {
            this.memoryType = memoryType;
            this.dataView.refreshAll();
        }

        public void setModuleType(String moduleType) {
            this.moduleType = moduleType;
            this.dataView.refreshAll();
        }

        public void setCasLatency(String casLatency) {
            this.casLatency = casLatency;
            this.dataView.refreshAll();
        }

        public void setFrequencyMhz(String frequencyMhz) {
            this.frequencyMhz = frequencyMhz;
            this.dataView.refreshAll();
        }

        public void setSupportsEcc(String value) {
            if (value == null || value.isEmpty()) {
                this.supportsEcc = null;
            } else if ("да".contains(value.toLowerCase())) {
                this.supportsEcc = true;
            } else if ("нет".contains(value.toLowerCase())) {
                this.supportsEcc = false;
            }
            this.dataView.refreshAll();
        }

        private boolean test(RandomAccessMemoryDto dto) {
            return matches(dto.name(), name)
                    && matchesNumeric(dto.capacity(), capacity)
                    && matches(dto.memoryType(), memoryType)
                    && matches(dto.moduleType(), moduleType)
                    && matches(dto.model(), model)
                    && matches(dto.manufacturer(), manufacturer)
                    && matchesNumeric(dto.casLatency(), casLatency)
                    && matchesNumeric(dto.frequencyMhz(), frequencyMhz)
                    && (supportsEcc == null || dto.supportsEcc() == supportsEcc);
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

    static class RandomAccessMemoryForm extends BaseForm<RandomAccessMemoryDto> {
        private TextArea nameField;
        private TextArea modelField;
        private TextArea manufacturerField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> ramMemoryTypeBox;
        private RadioButtonGroup<String> ramTypeRadio;
        private NumberField ramFrequencyField;
        private NumberField ramLatencyField;
        private Checkbox ramEccSupportCheckbox;

        private Binder<RandomAccessMemoryDto> binder;
        private Long currentId = null;

        public RandomAccessMemoryForm() {
            super();

            binder = new Binder<>(RandomAccessMemoryDto.class);
            setBinder(binder);

            setupFields();
            setupBinder();

            HorizontalLayout capacityFieldLayout = new HorizontalLayout();
            capacityFieldLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
            capacityFieldLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
            capacityFieldLayout.setMargin(false);
            capacityFieldLayout.add(capacityField, capacityUnitBox);

            add(nameField, manufacturerField, modelField, capacityFieldLayout, ramMemoryTypeBox, ramTypeRadio,
                    ramFrequencyField, ramLatencyField, ramEccSupportCheckbox);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        @Override
        protected void setDto(RandomAccessMemoryDto dto) {
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

            modelField = new TextArea("Модель:");
            modelField.setMinRows(1);
            modelField.setMaxRows(1);
            modelField.setMinLength(1);
            modelField.setMaxLength(30);
            modelField.setClearButtonVisible(true);

            manufacturerField = new TextArea("Производитель:");
            manufacturerField.setMinRows(1);
            manufacturerField.setMaxRows(1);
            manufacturerField.setMinLength(1);
            manufacturerField.setMaxLength(30);
            manufacturerField.setClearButtonVisible(true);

            capacityField = new NumberField("Объём:");
            capacityField.setRequiredIndicatorVisible(true);
            capacityField.setClearButtonVisible(true);
            capacityField.setStepButtonsVisible(true);
            capacityField.setWidthFull();

            capacityUnitBox = new ComboBox<>();
            capacityUnitBox.setItems(Bytes.values());
            capacityUnitBox.setItemLabelGenerator(Bytes::getLabel);
            capacityUnitBox.setValue(Bytes.MB);

            ramMemoryTypeBox = new ComboBox<>("Вид памяти:");
            ramMemoryTypeBox.setItems("DDR", "DDR2", "DDR3", "DDR3L", "DDR4", "DDR5");

            ramTypeRadio = new RadioButtonGroup<>("Вид модуля:");
            ramTypeRadio.setItems("DIMM", "SO-DIMM");

            ramFrequencyField = new NumberField("Тактовая частота (МГц):");
            ramFrequencyField.setClearButtonVisible(true);

            ramLatencyField = new NumberField("CAS-латентность:");
            ramLatencyField.setPrefixComponent(new Div("CL"));
            ramLatencyField.setClearButtonVisible(true);

            ramEccSupportCheckbox = new Checkbox("Поддерживает ECC?");
            ramEccSupportCheckbox.setValue(false); // По умолчанию нет
        }

        @Override
        protected void setupBinder() {
            // Bind fields to DTO
            binder.forField(nameField)
                    .asRequired("Название обязательно")
                    .withValidator(name -> name.length() <= 30, "Название должно быть до 30 символов")
                    .bind(
                            RandomAccessMemoryDto::name,
                            (dto, value) -> {}
                    );

            binder.forField(modelField)
                    .withValidator(model -> model.length() <= 30, "Название модели должно быть до 30 символов")
                    .bind(
                            RandomAccessMemoryDto::model,
                            (dto, value) -> {}
                    );

            binder.forField(manufacturerField)
                    .withValidator(manufacturer -> manufacturer.length() <= 30, "Название производителя должно быть до 30 символов")
                    .bind(
                            RandomAccessMemoryDto::manufacturer,
                            (dto, value) -> {}
                    );

            binder.forField(ramMemoryTypeBox)
                    .asRequired("Вид памяти обязателен")
                    .withValidator(memoryType -> memoryType.length() <= 10, "Название вида памяти должно быть до 10 символов")
                    .bind(
                            RandomAccessMemoryDto::memoryType,
                            (dto, value) -> {}
                    );

            binder.forField(ramTypeRadio)
                    .asRequired("Вид модуля обязателен")
                    .withValidator(manufacturer -> manufacturer.length() <= 10, "Название вида модуля должно быть до 10 символов")
                    .bind(
                            RandomAccessMemoryDto::moduleType,
                            (dto, value) -> {}
                    );

            binder.forField(capacityField)
                    .asRequired("Объём обязателен")
                    .withValidator(capacity -> capacity > 0, "Объём должен быть положительным")
                    .withConverter(new Converter<Double, Float>() {
                        @Override
                        public Result<Float> convertToModel(Double aDouble, ValueContext valueContext) {
                            return Result.ok(aDouble.floatValue());
                        }

                        @Override
                        public Double convertToPresentation(Float aFloat, ValueContext valueContext) {
                            return 0.0;
                        }
                    })
                    .bind(
                            dto -> dto.capacity() != null ? dto.capacity() : null,
                            (dto, value) -> {}
                    );

            binder.forField(ramFrequencyField)
                    .withValidator(frequency -> frequency == null || frequency > 0, "Частота должна быть положительной")
                    .bind(
                            dto -> dto.frequencyMhz() != null ? dto.frequencyMhz().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(ramLatencyField)
                    .withValidator(latency -> latency == null || latency > 0, "Значение должно быть положительным")
                    .bind(
                            dto -> dto.casLatency() != null ? dto.casLatency().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(ramEccSupportCheckbox)
                    .bind(
                            RandomAccessMemoryDto::supportsEcc,
                            (dto, value) -> {}
                    );
        }

        @Override
        protected Optional<RandomAccessMemoryDto> getFormDataObject() {
            if (!isValid()) {
                return Optional.empty();
            }

            RandomAccessMemoryDto dto = new RandomAccessMemoryDto(
                    currentId,
                    nameField.getValue(),
                    modelField.getValue(),
                    manufacturerField.getValue(),
                    ramMemoryTypeBox.getValue(),
                    ramTypeRadio.getValue(),
                    capacityField.getValue() != null ? toMegabytes(capacityField.getValue().floatValue(),
                            capacityUnitBox.getValue()) : null,
                    ramFrequencyField.getValue().floatValue(),
                    ramLatencyField.getValue().intValue(),
                    ramEccSupportCheckbox.getValue()
            );

            return Optional.of(dto);
        }

        @Override
        protected void clearFields() {
            currentId = null;
            nameField.clear();
            capacityField.clear();
            manufacturerField.clear();
            modelField.clear();
            ramLatencyField.clear();
            ramFrequencyField.clear();
            ramEccSupportCheckbox.setValue(false);
            binder.validate();
        }
    }
}
