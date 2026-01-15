package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Entities.FlashDrive;
import com.nikkin.devicesdb.Services.FlashDriveService;
import com.nikkin.devicesdb.Views.BaseForm;
import com.nikkin.devicesdb.Views.CustomDialog;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnRendering;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.router.Route;

import java.util.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Route("flash")
final public class FlashDriveView extends BaseView<FlashDrive, FlashDriveDto> {
    public FlashDriveView(FlashDriveService service) {
        super("Флеш-память", service);
    }

    @Override
    protected BaseForm<FlashDriveDto> createForm() {
        return new FlashDriveForm();
    }

    @Override
    protected void initTable() {
        var flashDriveGrid = new Grid<>(FlashDriveDto.class, false);

        flashDriveGrid.setItems(new ArrayList<>());
        flashDriveGrid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        flashDriveGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        flashDriveGrid.setColumnRendering(ColumnRendering.LAZY);
        flashDriveGrid.setEmptyStateText("В таблице отсутствуют записи.");

        flashDriveGrid.addColumn(FlashDriveDto::name)
                .setHeader("")  // иначе при добавлении поиска по столбцу в setupFilters будет NoSuchElementException
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDriveDto::capacity)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDriveDto::usbInterface)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDriveDto::usbType)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDriveDto::readSpeed)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDriveDto::writeSpeed)
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

        FlashDriveFilter filter = new FlashDriveFilter(flashDriveGrid.getListDataView());

        headerCells.getFirst().setComponent(createFilterHeader("Наименование", filter::setName));
        headerCells.get(1).setComponent(createFilterHeader("Объём (МБ)", filter::setCapacity));
        headerCells.get(2).setComponent(createFilterHeader("Интерфейс USB", filter::setUsbInterface));
        headerCells.get(3).setComponent(createFilterHeader("Тип USB", filter::setUsbType));
        headerCells.get(4).setComponent(createFilterHeader("Скорость чтения (МБ/сек)", filter::setReadSpeed));
        headerCells.getLast().setComponent(createFilterHeader("Скорость записи (МБ/сек)", filter::setWriteSpeed));
    }

    private static class FlashDriveFilter {
        private final GridListDataView<FlashDriveDto> dataView;

        private String name;
        private String usbInterface;
        private String usbType;
        private String capacity;
        private String writeSpeed;
        private String readSpeed;

        public FlashDriveFilter(GridListDataView<FlashDriveDto> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setName(String name) {
            this.name = name;
            this.dataView.refreshAll();
        }

        public void setUsbInterface(String usbInterface) {
            this.usbInterface = usbInterface;
            this.dataView.refreshAll();
        }

        public void setUsbType(String usbType) {
            this.usbType = usbType;
            this.dataView.refreshAll();
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
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

        private boolean test(FlashDriveDto dto) {
            return matches(dto.name(), name)
                    && matches(dto.usbInterface(), usbInterface)
                    && matches(dto.usbType(), usbType)
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

    static class FlashDriveForm extends BaseForm<FlashDriveDto> {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> usbInterfaceBox;
        private ComboBox<String> usbTypeBox;
        private NumberField readSpeedField;
        private NumberField writeSpeedField;

        private Binder<FlashDriveDto> binder;
        private Long currentId = null;

        public FlashDriveForm() {
            super();

            binder = new Binder<>(FlashDriveDto.class);
            setBinder(binder);

            setupFields();
            setupBinder();

            HorizontalLayout capacityFieldLayout = new HorizontalLayout();
            capacityFieldLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
            capacityFieldLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
            capacityFieldLayout.setMargin(false);
            capacityFieldLayout.add(capacityField, capacityUnitBox);

            add(nameField, capacityFieldLayout, usbInterfaceBox,
                    usbTypeBox, writeSpeedField, readSpeedField);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        @Override
        protected void setDto(FlashDriveDto dto) {
            this.currentId = dto.id();
            binder.readBean(dto);
        }

        @Override
        protected void setupFields() {
            nameField = new TextArea("Название:");
            capacityField = new NumberField("Объём:");
            capacityUnitBox = new ComboBox<>();
            usbInterfaceBox = new ComboBox<>("Интерфейс:");
            usbTypeBox = new ComboBox<>("Версия USB:");
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

            usbTypeBox.setItems("1.0", "2.0", "3.0", "3.1", "3.2");
            usbTypeBox.setClearButtonVisible(true);

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
                            FlashDriveDto::name,
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

            binder.forField(usbTypeBox)
                    .bind(FlashDriveDto::usbType, (dto, value) -> {});

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
        protected Optional<FlashDriveDto> getFormDataObject() {
            if (!isValid()) {
                return Optional.empty();
            }

            FlashDriveDto dto = new FlashDriveDto(
                    currentId,
                    nameField.getValue(),
                    usbInterfaceBox.getValue() != null ? usbInterfaceBox.getValue() : null,
                    usbTypeBox.getValue(),
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
            usbTypeBox.clear();
            writeSpeedField.clear();
            readSpeedField.clear();
            binder.validate();
        }
    }
}
