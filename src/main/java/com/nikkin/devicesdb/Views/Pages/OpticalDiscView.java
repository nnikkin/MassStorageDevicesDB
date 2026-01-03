package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Dto.OpticalDiscDto;
import com.nikkin.devicesdb.Presenters.OpticalDiscPresenter;
import com.nikkin.devicesdb.Views.CustomDialog;
import com.nikkin.devicesdb.Views.NavBar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

@Route("optical")
public class OpticalDiscView extends AppLayout {
    private final OpticalDiscPresenter presenter;
    private Grid<OpticalDiscDto> opticalDiscGrid;
    private List<Component> buttons;

    public OpticalDiscView(OpticalDiscPresenter presenter) {
        this.presenter = presenter;

        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        H1 pageTitle = new H1("Запоминающие устройства - Оптические диски");
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

        layout.add(tableMenu, opticalDiscGrid);
    }

    private void initTable() {
        opticalDiscGrid = new Grid<>(OpticalDiscDto.class, false);
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
                        buttons.forEach(button -> ((Button) button).setEnabled(false));
                    else
                        buttons.forEach(button -> ((Button) button).setEnabled(true));

                    ((Button) buttons.getFirst()).setEnabled(true);
                    ((Button) buttons.getLast()).setEnabled(true);
                }
        );
    }

    private void refreshGrid() {
        opticalDiscGrid.deselectAll();
        fillTable();
    }

    private void fillTable() {
        List<OpticalDiscDto> items = presenter.loadAllOpticalDiscs();
        opticalDiscGrid.getListDataView().setItems(items);
    }

    private void setupFilters() {
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

    private void setupButtons() {
        buttons = new ArrayList<>();

        Button addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBtn.addClickListener(e -> showNewEntryDialog());

        Button editBtn = new Button("Изменить", VaadinIcon.PENCIL.create());
        editBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editBtn.setEnabled(false);
        editBtn.addClickListener(e -> {
            var selected = opticalDiscGrid.getSelectedItems();
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
        var dialog = new OpticalDiscDialog(opticalDisc -> {
            presenter.addOpticalDisc(opticalDisc);
            refreshGrid();
        });
        dialog.setHeaderTitle("Добавление новой записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showEditDialog(OpticalDiscDto oldDto) {
        var dialog = new OpticalDiscDialog(newDto -> {
            presenter.updateOpticalDisc(oldDto.id(), newDto);
            refreshGrid();
        }, oldDto);
        dialog.setHeaderTitle("Изменение записи");
        dialog.addOkClickListener(e -> dialog.save());

        dialog.open();
    }

    private void showDelConfirmDialog() {
        var selectedItems = opticalDiscGrid.getSelectedItems();
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
                    .map(OpticalDiscDto::id)
                    .toList();

            presenter.deleteOpticalDiscs(ids);
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

    static class OpticalDiscForm extends FormLayout {
        private TextArea nameField;
        private NumberField capacityField;
        private ComboBox<Bytes> capacityUnitBox;
        private ComboBox<String> opticalDiscTypeBox;
        private ComboBox<String> opticalRewriteTypeBox;
        private NumberField opticalSpeedMultiplierField;
        private NumberField opticalLayersField;

        private Binder<OpticalDiscDto> binder = new Binder<>(OpticalDiscDto.class);
        private Long currentId = null;

        public OpticalDiscForm() {
            setupFields();
            setupBinder();
            add(nameField, capacityField, opticalDiscTypeBox, opticalRewriteTypeBox, opticalSpeedMultiplierField, opticalLayersField);
            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        private void setupFields() {
            nameField = new TextArea("Название:");
            nameField.setMinRows(1);
            nameField.setMaxRows(1);
            nameField.setMinLength(1);
            nameField.setMaxLength(30);
            nameField.setClearButtonVisible(true);

            capacityField = new NumberField("Объём:");
            capacityField.setClearButtonVisible(true);

            capacityUnitBox = new ComboBox<>();
            capacityUnitBox.setItems(Bytes.values());
            capacityUnitBox.setItemLabelGenerator(Bytes::getLabel);
            capacityUnitBox.setValue(Bytes.MB);
            capacityField.setSuffixComponent(capacityUnitBox);

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

        private void setupBinder() {
            // Bind fields to DTO
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

        public void setOpticalDisc(OpticalDiscDto opticalDisc) {
            this.currentId = opticalDisc.id();
            binder.readBean(opticalDisc);
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

        public Optional<OpticalDiscDto> getFormDataObject() {
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

        public boolean isValid() {
            return binder.validate().isOk();
        }

        public void clear() {
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

    static class OpticalDiscDialog extends CustomDialog {
        private final OpticalDiscForm form;
        private final SerializableConsumer<OpticalDiscDto> onSaveCallback;
        private boolean isEditMode;

        public OpticalDiscDialog(SerializableConsumer<OpticalDiscDto> onSaveCallback) {
            this(onSaveCallback, null);
        }

        public OpticalDiscDialog(SerializableConsumer<OpticalDiscDto> onSaveCallback, OpticalDiscDto dto) {
            this.onSaveCallback = onSaveCallback;

            form = new OpticalDiscForm();
            isEditMode = false;

            if (dto != null) {
                isEditMode = true;
                form.setOpticalDisc(dto);
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

            form.getFormDataObject().ifPresent(opticalDisc -> {
                try {
                    onSaveCallback.accept(opticalDisc);
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
