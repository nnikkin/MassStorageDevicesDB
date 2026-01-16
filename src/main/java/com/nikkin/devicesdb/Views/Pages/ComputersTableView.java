package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Dto.ComputerDto;
import com.nikkin.devicesdb.Entities.Computer;
import com.nikkin.devicesdb.Services.ComputerService;
import com.nikkin.devicesdb.Views.BaseForm;
import com.nikkin.devicesdb.Views.BaseTableView;
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

@Route("computers")
final public class ComputersTableView extends BaseTableView<Computer, ComputerDto> {
    public ComputersTableView(ComputerService service) {
        super("Компьютеры", service);
    }

    @Override
    protected BaseForm<ComputerDto> createForm() {
        return new ComputerForm();
    }

    @Override
    protected void initTable() {
        var flashDriveGrid = new Grid<>(ComputerDto.class, false);

        flashDriveGrid.setItems(new ArrayList<>());
        flashDriveGrid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        flashDriveGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        flashDriveGrid.setColumnRendering(ColumnRendering.LAZY);
        flashDriveGrid.setEmptyStateText("В таблице отсутствуют записи.");

        flashDriveGrid.addColumn(ComputerDto::name)
                .setHeader("")  // иначе при добавлении поиска по столбцу в setupFilters будет NoSuchElementException
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(ComputerDto::linkedRamDtos)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(ComputerDto::linkedHddDtos)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(ComputerDto::linkedSsdDtos)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(ComputerDto::linkedFlashDtos)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);

        flashDriveGrid.addSelectionListener(
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

        setGrid(flashDriveGrid);
    }

    @Override
    protected void setupFilters() {
        var flashDriveGrid = getGrid();
        var headerCells = flashDriveGrid.getHeaderRows()
                                        .getFirst()
                                        .getCells();

        ComputerFilter filter = new ComputerFilter(flashDriveGrid.getListDataView());

        headerCells.getFirst().setComponent(createFilterHeader("Наименование", filter::setName));
        headerCells.get(1).setComponent(createFilterHeader("Связанные модули ОЗУ", filter::));
        headerCells.get(2).setComponent(createFilterHeader("Связанные HDD", filter::));
        headerCells.get(3).setComponent(createFilterHeader("Связанные SSD", filter::));
        headerCells.getLast().setComponent(createFilterHeader("Связанные Flash", filter::));
    }

    private static class ComputerFilter {
        private final GridListDataView<ComputerDto> dataView;

        private String name;

        public ComputerFilter(GridListDataView<ComputerDto> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setName(String name) {
            this.name = name;
            this.dataView.refreshAll();
        }

        private boolean test(ComputerDto dto) {
            return matches(dto.name(), name)
                    && matches(dto.usbInterface(), usbInterface)
                    && matchesNumeric(dto.capacity(), capacity)
                    && matchesNumeric(dto.writeSpeed(), writeSpeed)
                    && matchesNumeric(dto.readSpeed(), readSpeed);
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

    static class ComputerForm extends BaseForm<ComputerDto> {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> usbInterfaceBox;
        private NumberField readSpeedField;
        private NumberField writeSpeedField;

        private Binder<ComputerDto> binder;
        private Long currentId = null;

        public ComputerForm() {
            super();

            binder = new Binder<>(ComputerDto.class);
            setBinder(binder);

            setupFields();
            setupBinder();

            HorizontalLayout capacityFieldLayout = new HorizontalLayout();
            capacityFieldLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
            capacityFieldLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
            capacityFieldLayout.setMargin(false);
            capacityFieldLayout.add(capacityField, capacityUnitBox);

            add(nameField, capacityFieldLayout, usbInterfaceBox,
                    writeSpeedField, readSpeedField);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        @Override
        protected void setDto(ComputerDto dto) {
            this.currentId = dto.id();
            binder.readBean(dto);
        }

        @Override
        protected void setupFields() {
            nameField = new TextArea("Название:");
            capacityField = new NumberField("Объём:");
            capacityUnitBox = new ComboBox<>();
            usbInterfaceBox = new ComboBox<>("Интерфейс:");
            readSpeedField = new NumberField("Скорость записи:");
            writeSpeedField = new NumberField("Скорость чтения:");

            nameField.setMinRows(1);
            nameField.setMaxRows(1);
            nameField.setMinLength(1);
            nameField.setMaxLength(30);
            nameField.setClearButtonVisible(true);

            capacityField.setClearButtonVisible(true);
            capacityField.setRequiredIndicatorVisible(true);
            capacityField.setStepButtonsVisible(true);
            capacityField.setWidthFull();

            capacityUnitBox.setItems(Bytes.values());
            capacityUnitBox.setItemLabelGenerator(Bytes::getLabel);
            capacityUnitBox.setValue(Bytes.MB);

            usbInterfaceBox.setItems("Type-A", "Type-C", "Micro-USB");
            usbInterfaceBox.setClearButtonVisible(true);

            readSpeedField.setSuffixComponent(new Div("МБ/с"));
            readSpeedField.setClearButtonVisible(true);

            writeSpeedField.setSuffixComponent(new Div("МБ/с"));
            writeSpeedField.setClearButtonVisible(true);
        }

        @Override
        protected void setupBinder() {
            // Bind fields to DTO
            binder.forField(nameField)
                    .asRequired("Название обязательно")
                    .withValidator(name -> name.length() <= 30, "Название должно быть до 30 символов")
                    .bind(
                            ComputerDto::name,
                            (dto, value) -> {}
                    );

            binder.forField(capacityField)
                    .asRequired("Объём обязателен")
                    .withValidator(capacity -> capacity > 0, "Объём должен быть положительным")
                    .bind(
                            dto -> dto.capacity() != null ? dto.capacity().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(usbInterfaceBox)
                    .bind(
                            dto -> dto.usbInterface() != null ? dto.usbInterface() : null,
                            (dto, value) -> {}
                    );

            binder.forField(writeSpeedField)
                    .withValidator(writeSpd -> writeSpd == null || writeSpd > 0, "Скорость должна быть положительной")
                    .bind(
                            dto -> dto.writeSpeed() != null ? dto.writeSpeed().doubleValue() : null,
                            (dto, value) -> {}
                    );

            binder.forField(readSpeedField)
                    .withValidator(readSpd -> readSpd == null || readSpd > 0, "Скорость должна быть положительной")
                    .bind(
                            dto -> dto.readSpeed() != null ? dto.readSpeed().doubleValue() : null,
                            (dto, value) -> {}
                    );
        }

        @Override
        protected Optional<ComputerDto> getFormDataObject() {
            if (!isValid()) {
                return Optional.empty();
            }

            ComputerDto dto = new ComputerDto(
                    currentId,
                    nameField.getValue(),
                    usbInterfaceBox.getValue() != null ? usbInterfaceBox.getValue() : null,
                    capacityField.getValue() != null ? toMegabytes(capacityField.getValue().floatValue(), capacityUnitBox.getValue()) : null,
                    writeSpeedField.getValue() != null ? writeSpeedField.getValue().floatValue() : null,
                    readSpeedField.getValue() != null ? readSpeedField.getValue().floatValue() : null
            );

            return Optional.of(dto);
        }

        @Override
        protected void clearFields() {
            currentId = null;
            nameField.clear();
            capacityField.clear();
            usbInterfaceBox.clear();
            writeSpeedField.clear();
            readSpeedField.clear();
            binder.validate();
        }
    }
}
