package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Domain.FloppyDensity;
import com.nikkin.devicesdb.Dto.FloppyDiskDto;
import com.nikkin.devicesdb.Entities.FloppyDisk;
import com.nikkin.devicesdb.Services.FloppyDiskService;
import com.nikkin.devicesdb.Views.BaseForm;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnRendering;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Optional;

@Route("floppy")
final public class FloppyDiskView extends BaseView<FloppyDisk, FloppyDiskDto> {
    public FloppyDiskView(FloppyDiskService service) {
        super("Накопители на гибких дисках", service);
    }

    @Override
    protected BaseForm<FloppyDiskDto> createForm() {
        return new FloppyDiskForm();
    }

    @Override
    protected void initTable() {
        var floppyDiskGrid = new Grid<>(FloppyDiskDto.class, false);

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

        setGrid(floppyDiskGrid);
    }

    @Override
    protected void setupFilters() {
        var floppyDiskGrid = getGrid();
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

    static class FloppyDiskForm extends BaseForm<FloppyDiskDto> {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private NumberField floppyFormatField;
        private ComboBox<FloppyDensity> floppyDensityBox;
        private Checkbox floppyDoubleSidedCheckbox;

        private Binder<FloppyDiskDto> binder;
        private Long currentId = null;

        public FloppyDiskForm() {
            super();

            binder = new Binder<>(FloppyDiskDto.class);
            setBinder(binder);

            setupFields();
            setupBinder();

            HorizontalLayout capacityFieldLayout = new HorizontalLayout();
            capacityFieldLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
            capacityFieldLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
            capacityFieldLayout.setMargin(false);
            capacityFieldLayout.add(capacityField, capacityUnitBox);

            add(nameField, capacityFieldLayout, floppyFormatField, floppyDensityBox, floppyDoubleSidedCheckbox);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        @Override
        protected void setDto(FloppyDiskDto dto) {
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
            capacityField.setWidthFull();
            capacityField.setClearButtonVisible(true);
            capacityField.setRequiredIndicatorVisible(true);
            capacityField.setStepButtonsVisible(true);
            capacityField.setStep(1);

            capacityUnitBox = new ComboBox<>();
            capacityUnitBox.setItems(Bytes.values());
            capacityUnitBox.setItemLabelGenerator(Bytes::getLabel);
            capacityUnitBox.setValue(Bytes.MB);

            floppyFormatField = new NumberField("Размер (в дюймах):");
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

        @Override
        protected void setupBinder() {
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


        @Override
        protected Optional<FloppyDiskDto> getFormDataObject() {
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

        @Override
        protected void clearFields() {
            currentId = null;
            nameField.clear();
            capacityField.clear();
            floppyFormatField.clear();
            floppyDensityBox.clear();
            floppyDoubleSidedCheckbox.setValue(false);
            binder.validate();
        }
    }
}
