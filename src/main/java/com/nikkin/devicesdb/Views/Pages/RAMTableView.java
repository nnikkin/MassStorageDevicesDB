package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Dto.ComputerDto;
import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import com.nikkin.devicesdb.Services.ComputerService;
import com.nikkin.devicesdb.Services.RAMService;
import com.nikkin.devicesdb.Views.BaseForm;
import com.nikkin.devicesdb.Views.BaseTableView;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnRendering;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Optional;

@Route("ram")
public class RAMTableView extends BaseTableView<RandomAccessMemory, RandomAccessMemoryDto> {
    protected static ComputerService computerService;

    public RAMTableView(RAMService service, ComputerService computerService) {
        super("ОЗУ", service);
        this.computerService = computerService;
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

        ramGrid.addColumn(RandomAccessMemoryDto::manufacturer)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(RandomAccessMemoryDto::model)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(dto -> String.format("%.2f", dto.capacity()))
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
        ramGrid.addColumn(dto -> String.format("%.2f", dto.frequencyMhz()))
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(
                dto -> {
                    if (dto.computerId() == null) {
                        return "Не назначен";
                    }

                    return computerService.getById(dto.computerId())
                            .map(ComputerDto::name)
                            .orElse("Не назначен");
                })
                .setHeader("Компьютер")
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

        headerCells.getFirst().setComponent(createFilterHeader("Производитель", filter::setManufacturer));
        headerCells.get(1).setComponent(createFilterHeader("Модель", filter::setModel));
        headerCells.get(2).setComponent(createFilterHeader("Объём (МБ)", filter::setCapacity));
        headerCells.get(3).setComponent(createFilterHeader("Вид памяти", filter::setMemoryType));
        headerCells.get(4).setComponent(createFilterHeader("Вид модуля", filter::setModuleType));
        headerCells.get(5).setComponent(createFilterHeader("Тактовая частота (МГц)", filter::setFrequencyMhz));
        headerCells.getLast().setComponent(createFilterHeader("Компьютер", null));
    }

    private static class RandomAccessMemoryFilter {
        private final GridListDataView<RandomAccessMemoryDto> dataView;

        private String model;
        private String manufacturer;
        private String capacity;
        private String memoryType;
        private String moduleType;
        private String frequencyMhz;

        public RandomAccessMemoryFilter(GridListDataView<RandomAccessMemoryDto> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
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

        public void setFrequencyMhz(String frequencyMhz) {
            this.frequencyMhz = frequencyMhz;
            this.dataView.refreshAll();
        }

        private boolean test(RandomAccessMemoryDto dto) {
            return matchesNumeric(dto.capacity(), capacity)
                    && matches(dto.memoryType(), memoryType)
                    && matches(dto.moduleType(), moduleType)
                    && matches(dto.model(), model)
                    && matches(dto.manufacturer(), manufacturer)
                    && matchesNumeric(dto.frequencyMhz(), frequencyMhz);
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

    static class RandomAccessMemoryForm extends BaseForm<RandomAccessMemoryDto> {
        private TextArea modelField;
        private TextArea manufacturerField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> ramMemoryTypeBox;
        private RadioButtonGroup<String> ramTypeRadio;
        private NumberField ramFrequencyField;
        private ComboBox<ComputerDto> computersField;

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

            add(manufacturerField, modelField, capacityFieldLayout, ramMemoryTypeBox, ramTypeRadio,
                    ramFrequencyField, computersField);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        @Override
        protected void setDto(RandomAccessMemoryDto dto) {
            this.currentId = dto.id();
            binder.readBean(dto);
        }

        @Override
        protected void setupFields() {
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

            computersField = new ComboBox<>("Компьютер:");
            computersField.setItems(computerService.getAll());
            computersField.setItemLabelGenerator(ComputerDto::name);
            computersField.setClearButtonVisible(true);
            computersField.setRequiredIndicatorVisible(true);
        }

        @Override
        protected void setupBinder() {
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
                    .bind(
                            dto -> dto.capacity() != null ? dto.capacity().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(ramFrequencyField)
                .withValidator(frequency -> frequency == null || frequency > 0, "Частота должна быть положительной")
                .bind(
                        dto -> dto.frequencyMhz() != null ? dto.frequencyMhz().doubleValue() : null,
                        (dto, value) -> {}
                );

            binder.forField(computersField)
                .asRequired("Связка с компьютером обязательна")
                .bind(
                        dto -> dto.computerId() == null ? null :
                                computerService.getById(dto.computerId()).orElse(null),
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
                    modelField.getValue(),
                    manufacturerField.getValue(),
                    ramMemoryTypeBox.getValue(),
                    ramTypeRadio.getValue(),
                    capacityField.getValue() != null ? toMegabytes(capacityField.getValue().floatValue(),
                            capacityUnitBox.getValue()) : null,
                    ramFrequencyField.getValue() != null ? ramFrequencyField.getValue().floatValue() : null,
                    computersField.getValue().id()
            );

            return Optional.of(dto);
        }

        @Override
        protected void clearFields() {
            currentId = null;
            capacityField.clear();
            manufacturerField.clear();
            modelField.clear();
            ramFrequencyField.clear();
            computersField.clear();
            binder.validate();
        }
    }
}
