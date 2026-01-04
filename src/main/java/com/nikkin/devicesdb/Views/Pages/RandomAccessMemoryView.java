package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Presenters.RandomAccessMemoryPresenter;
import com.nikkin.devicesdb.Views.CustomDialog;
import com.nikkin.devicesdb.Views.NavBar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
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

@Route("ram")
public class RandomAccessMemoryView extends AppLayout {
    private final RandomAccessMemoryPresenter presenter;
    private Grid<RandomAccessMemoryDto> ramGrid;
    private List<Component> buttons;

    public RandomAccessMemoryView(RandomAccessMemoryPresenter presenter) {
        this.presenter = presenter;

        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        H1 pageTitle = new H1("Запоминающие устройства - ОЗУ");
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

        layout.add(tableMenu, ramGrid);
    }

    private void initTable() {
        ramGrid = new Grid<>(RandomAccessMemoryDto.class, false);
        ramGrid.setItems(new ArrayList<>());
        ramGrid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        ramGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        ramGrid.setColumnRendering(ColumnRendering.LAZY);
        ramGrid.setEmptyStateText("В таблице отсутствуют записи.");

        ramGrid.addColumn(RandomAccessMemoryDto::name)
                .setHeader("")  // иначе при добавлении поиска по столбцу в setupFilters будет NoSuchElementException
                .setAutoWidth(true)
                .setSortable(true);
        ramGrid.addColumn(RandomAccessMemoryDto::capacity)
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
        ramGrid.addColumn(RandomAccessMemoryDto::frequencyMhz)
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
                        buttons.forEach(button -> ((Button) button).setEnabled(false));
                    else
                        buttons.forEach(button -> ((Button) button).setEnabled(true));

                    ((Button) buttons.getFirst()).setEnabled(true);
                    ((Button) buttons.getLast()).setEnabled(true);
                }
        );
    }

    private void refreshGrid() {
        ramGrid.deselectAll();
        fillTable();
    }

    private void fillTable() {
        List<RandomAccessMemoryDto> items = presenter.loadAllRam();
        ramGrid.getListDataView().setItems(items);
    }

    private void setupFilters() {
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

    private void setupButtons() {
        buttons = new ArrayList<>();

        Button addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBtn.addClickListener(e -> showNewEntryDialog());

        Button editBtn = new Button("Изменить", VaadinIcon.PENCIL.create());
        editBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editBtn.setEnabled(false);
        editBtn.addClickListener(e -> {
            var selected = ramGrid.getSelectedItems();
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
        var dialog = new RandomAccessMemoryDialog(ram -> {
            presenter.addRam(ram);
            refreshGrid();
        });
        dialog.setHeaderTitle("Добавление новой записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showEditDialog(RandomAccessMemoryDto oldDto) {
        var dialog = new RandomAccessMemoryDialog(newDto -> {
            presenter.updateRam(oldDto.id(), newDto);
            refreshGrid();
        }, oldDto);
        dialog.setHeaderTitle("Изменение записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showDelConfirmDialog() {
        var selectedItems = ramGrid.getSelectedItems();
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
                    .map(RandomAccessMemoryDto::id)
                    .toList();

            presenter.deleteRam(ids);
            refreshGrid();
            confirmDialog.close();

            Notification.show(
                    "Удалено записей: " + ids.size(),
                    3000,
                    Notification.Position.BOTTOM_END
            ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        confirmDialog.open();
    }

    private void showErrorDialog(String message) {
        Div textDiv = new Div(message);

        CustomDialog errorDialog = new CustomDialog("Ошибка", textDiv);
        errorDialog.setCancelButtonVisible(false);

        errorDialog.open();

        errorDialog.addOkClickListener(e -> errorDialog.close());
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

    static class RandomAccessMemoryForm extends FormLayout {
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

        private Binder<RandomAccessMemoryDto> binder = new Binder<>(RandomAccessMemoryDto.class);
        private Long currentId = null;

        public RandomAccessMemoryForm() {
            setupFields();
            setupBinder();
            add(nameField, manufacturerField, modelField, capacityField, ramMemoryTypeBox, ramTypeRadio,
                    ramFrequencyField, ramLatencyField, ramEccSupportCheckbox);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        private void setupFields() {
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
            capacityField.setClearButtonVisible(true);

            capacityUnitBox = new ComboBox<>();
            capacityUnitBox.setItems(Bytes.values());
            capacityUnitBox.setItemLabelGenerator(Bytes::getLabel);
            capacityUnitBox.setValue(Bytes.MB);
            capacityField.setSuffixComponent(capacityUnitBox);

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

        private void setupBinder() {
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

        public void setRandomAccessMemory(RandomAccessMemoryDto ram) {
            this.currentId = ram.id();
            binder.readBean(ram);
        }

        private float toMegabytes(float value, Bytes unit) {
            if (unit == Bytes.MB)
                return value;
            else {
                if (unit.getRank() > Bytes.MB.getRank())
                    return (float) (value * Math.pow(1024, unit.getRank() - Bytes.MB.getRank()));

                else {
                    if (unit == Bytes.BIT)
                        return (float) ((value / 8) / Math.pow(1024, Bytes.MB.getRank() - 1));

                    return (float) (value / Math.pow(1024, Bytes.MB.getRank() - unit.getRank()));
                }
            }
        }

        public Optional<RandomAccessMemoryDto> getFormDataObject() {
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

        public boolean isValid() {
            return binder.validate().isOk();
        }

        public void clear() {
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

    static class RandomAccessMemoryDialog extends CustomDialog {
        private final RandomAccessMemoryForm form;
        private final SerializableConsumer<RandomAccessMemoryDto> onSaveCallback;
        private boolean isEditMode;

        public RandomAccessMemoryDialog(SerializableConsumer<RandomAccessMemoryDto> onSaveCallback) {
            this(onSaveCallback, null);
        }

        public RandomAccessMemoryDialog(SerializableConsumer<RandomAccessMemoryDto> onSaveCallback, RandomAccessMemoryDto dto) {
            this.onSaveCallback = onSaveCallback;

            form = new RandomAccessMemoryForm();
            isEditMode = false;

            if (dto != null) {
                isEditMode = true;
                form.setRandomAccessMemory(dto);
            }

            addToDialogBody(form);
        }

        private void save() {
            if (!form.isValid()) {
                Notification notification = Notification.show(
                        "Пожалуйста, исправьте ошибки в форме",
                        3000,
                        Notification.Position.MIDDLE
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            form.getFormDataObject().ifPresent(ram -> {
                try {
                    onSaveCallback.accept(ram);
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
