package com.nikkin.devicesdb._Unused;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Views.BaseTableView;
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

@Route("optical")
public class OpticalDiscTableView extends BaseTableView<OpticalDisc, OpticalDiscDto> {
    public OpticalDiscTableView(OpticalDiscService service) {
        super("Оптические диски", service);
    }

    @Override
    protected BaseForm<OpticalDiscDto> createForm() {
        return new OpticalDiscForm();
    }

    @Override
    protected void initTable() {
        var opticalDiscGrid = new Grid<>(OpticalDiscDto.class, false);

        opticalDiscGrid.setItems(new ArrayList<>());
        opticalDiscGrid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        opticalDiscGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        opticalDiscGrid.setColumnRendering(ColumnRendering.LAZY);
        opticalDiscGrid.setEmptyStateText("В таблице отсутствуют записи.");

        opticalDiscGrid.addColumn(OpticalDiscDto::name)
                .setHeader("")  // иначе при добавлении поиска по столбцу в setupFilters будет NoSuchElementException
                .setAutoWidth(true)
                .setSortable(true);
        opticalDiscGrid.addColumn(OpticalDiscDto::capacity)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        opticalDiscGrid.addColumn(OpticalDiscDto::type)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        opticalDiscGrid.addColumn(OpticalDiscDto::rewriteType)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        opticalDiscGrid.addColumn(OpticalDiscDto::speedMultiplier)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        opticalDiscGrid.addColumn(OpticalDiscDto::layers)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);


        opticalDiscGrid.addSelectionListener(
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

        setGrid(opticalDiscGrid);
    }

    @Override
    protected void setupFilters() {
        var opticalDiscGrid = getGrid();
        var headerCells = opticalDiscGrid.getHeaderRows()
                                        .getFirst()
                                        .getCells();

        OpticalDiscFilter filter = new OpticalDiscFilter(opticalDiscGrid.getListDataView());

        headerCells.getFirst().setComponent(createFilterHeader("Наименование", filter::setName));
        headerCells.get(1).setComponent(createFilterHeader("Объём (МБ)", filter::setCapacity));
        headerCells.get(2).setComponent(createFilterHeader("Вид диска", filter::setType));
        headerCells.get(3).setComponent(createFilterHeader("Тип перезаписи", filter::setRewriteType));
        headerCells.get(4).setComponent(createFilterHeader("Множитель скорости", filter::setSpeedMultiplier));
        headerCells.getLast().setComponent(createFilterHeader("Количество слоёв", filter::setLayers));
    }

    private static class OpticalDiscFilter {
        private final GridListDataView<OpticalDiscDto> dataView;

        private String name;
        private String capacity;
        private String type;
        private String rewriteType;
        private String layers;
        private String speedMultiplier;

        public OpticalDiscFilter(GridListDataView<OpticalDiscDto> dataView) {
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

        public void setType(String type) {
            this.type = type;
            this.dataView.refreshAll();
        }

        public void setRewriteType(String rewriteType) {
            this.rewriteType = rewriteType;
            this.dataView.refreshAll();
        }

        public void setLayers(String layers) {
            this.layers = layers;
            this.dataView.refreshAll();
        }

        public void setSpeedMultiplier(String speedMultiplier) {
            this.speedMultiplier = speedMultiplier;
            this.dataView.refreshAll();
        }

        private boolean test(OpticalDiscDto dto) {
            return matches(dto.name(), name)
                    && matches(dto.type(), type)
                    && matches(dto.rewriteType(), rewriteType)
                    && matchesNumeric(dto.capacity(), capacity)
                    && matchesNumeric(dto.speedMultiplier(), speedMultiplier)
                    && matchesNumeric(dto.layers(), layers);
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

    static class OpticalDiscForm extends BaseForm<OpticalDiscDto> {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> opticalDiscTypeBox;
        private ComboBox<String> opticalRewriteTypeBox;
        private NumberField opticalSpeedMultiplierField;
        private NumberField opticalLayersField;

        private Binder<OpticalDiscDto> binder;
        private Long currentId = null;

        public OpticalDiscForm() {
            super();

            binder = new Binder<>(OpticalDiscDto.class);
            setBinder(binder);

            setupFields();
            setupBinder();

            HorizontalLayout capacityFieldLayout = new HorizontalLayout();
            capacityFieldLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
            capacityFieldLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
            capacityFieldLayout.setMargin(false);
            capacityFieldLayout.add(capacityField, capacityUnitBox);

            add(nameField, capacityFieldLayout, opticalDiscTypeBox, opticalRewriteTypeBox, opticalSpeedMultiplierField, opticalLayersField);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        @Override
        protected void setDto(OpticalDiscDto dto) {
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
            capacityField.setStepButtonsVisible(true);
            capacityField.setWidthFull();

            capacityUnitBox = new ComboBox<>();
            capacityUnitBox.setItems(Bytes.values());
            capacityUnitBox.setItemLabelGenerator(Bytes::getLabel);
            capacityUnitBox.setValue(Bytes.MB);

            opticalDiscTypeBox = new ComboBox<>("Вид диска:");
            opticalDiscTypeBox.setItems("Blu-ray", "CD", "DVD");
            opticalDiscTypeBox.setClearButtonVisible(true);

            opticalRewriteTypeBox = new ComboBox<>("Тип записи:");
            opticalRewriteTypeBox.setItems("R", "-R", "+R", "RW", "-RW", "+RW");
            opticalRewriteTypeBox.setClearButtonVisible(true);

            opticalSpeedMultiplierField = new NumberField("Множитель скорости:");
            opticalSpeedMultiplierField.setSuffixComponent(new Div("x"));
            opticalSpeedMultiplierField.setClearButtonVisible(true);

            opticalLayersField = new NumberField("Количество слоёв:");
            opticalLayersField.setClearButtonVisible(true);
        }

        @Override
        protected void setupBinder() {
            binder.forField(nameField)
                    .asRequired("Название обязательно")
                    .withValidator(name -> name.length() <= 30, "Название должно быть до 30 символов")
                    .bind(
                            OpticalDiscDto::name,
                            (dto, value) -> {}
                    );

            binder.forField(capacityField)
                    .withValidator(capacity -> capacity == null || capacity > 0, "Объём должен быть положительным")
                    .bind(
                            dto -> dto.capacity() != null ? dto.capacity().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(opticalRewriteTypeBox)
                    .bind(
                            OpticalDiscDto::rewriteType,
                            (dto, value) -> {}
                    );

            binder.forField(opticalDiscTypeBox)
                    .bind(
                            OpticalDiscDto::type,
                            (dto, value) -> {}
                    );

            binder.forField(opticalSpeedMultiplierField)
                    .withValidator(multiplier -> multiplier == null || multiplier > 0,
                            "Скорость должна быть положительной")
                    .bind(
                            dto -> dto.speedMultiplier() != null ? dto.speedMultiplier().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(opticalLayersField)
                    .withValidator(layers -> layers == null || layers > 0,
                            "Количество слоёв должно быть положительным")
                    .bind(
                            dto -> dto.layers() != null ? dto.layers().doubleValue() : null,
                            (dto, value) -> {}
                    );
        }

        @Override
        protected Optional<OpticalDiscDto> getFormDataObject() {
            if (!isValid()) {
                return Optional.empty();
            }

            OpticalDiscDto dto = new OpticalDiscDto(
                    currentId,
                    nameField.getValue(),
                    opticalDiscTypeBox.getValue(),
                    capacityField.getValue() != null ? toMegabytes(capacityField.getValue().floatValue(), capacityUnitBox.getValue()) : null,
                    opticalSpeedMultiplierField.getValue() != null ? opticalSpeedMultiplierField.getValue().intValue() : null,
                    opticalRewriteTypeBox.getValue(),
                    opticalLayersField.getValue() != null ? opticalLayersField.getValue().intValue() : null
            );

            return Optional.of(dto);
        }

        @Override
        protected void clearFields() {
            currentId = null;
            nameField.clear();
            capacityField.clear();
            opticalLayersField.clear();
            opticalSpeedMultiplierField.clear();
            opticalDiscTypeBox.clear();
            opticalRewriteTypeBox.clear();
            binder.validate();
        }
    }
}
