package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Entities.FlashDrive;
import com.nikkin.devicesdb.Presenters.FlashDrivePresenter;
import com.nikkin.devicesdb.Repos.FlashDriveRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnRendering;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Route("flash")
public class FlashDriveView extends AppLayout {
    private final FlashDriveRepository flashDriveRepository;
    private Grid<FlashDrive> flashDriveGrid;
    private CustomDialog dialog;
    private List<Component> buttons;

    public FlashDriveView(FlashDriveRepository flashDriveRepository) {
        this.flashDriveRepository = flashDriveRepository;
        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        H1 pageTitle = new H1("Запоминающие устройства - Флеш-память");
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

        layout.add(tableMenu, flashDriveGrid);

        dialog = new DeviceDialogBuilder("")
                .withStartingFields()
                .withFlashDriveFields()
                .withSpeedFields()
                .build();
    }

    private void initTable() {
        flashDriveGrid = new Grid<>(FlashDrive.class, false);
        flashDriveGrid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        flashDriveGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        flashDriveGrid.setColumnRendering(ColumnRendering.LAZY);
        flashDriveGrid.setEmptyStateText("В таблице отсутствуют записи.");

        flashDriveGrid.addColumn(FlashDrive::getName)
                .setHeader("")  // иначе при добавлении поиска по столбцу в setupFilters будет NoSuchElementException
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDrive::getCapacity)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDrive::getUsbInterface)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDrive::getUsbType)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDrive::getReadSpeed)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);
        flashDriveGrid.addColumn(FlashDrive::getWriteSpeed)
                .setHeader("")
                .setAutoWidth(true)
                .setSortable(true);

        flashDriveGrid.addSelectionListener(
                selectionEvent -> {
                    if (selectionEvent.getAllSelectedItems().isEmpty())
                        buttons.forEach(button -> ((Button) button).setEnabled(false));
                    else
                        buttons.forEach(button -> ((Button) button).setEnabled(true));

                    ((Button) buttons.getFirst()).setEnabled(true);
                }
        );
    }

    private void fillTable() {
        var flashDriveIterable = flashDriveRepository.findAll().iterator();
        List<FlashDrive> items = new ArrayList<>();
        flashDriveIterable.forEachRemaining(items::add);

        flashDriveGrid.setItems(items);
    }

    private void setupFilters() {
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

    private void setupButtons() {
        buttons = new ArrayList<>();

        Button addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBtn.addClickListener(e -> showNewEntryDialog());

        Button editBtn = new Button("Изменить", VaadinIcon.PENCIL.create());
        editBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editBtn.setEnabled(false);
        editBtn.addClickListener(e -> {
            if (flashDriveGrid.getSelectedItems().size() > 1)
                showErrorDialog("Выберите одну запись.");
            else
                showEditEntryDialog();
        });

        Button delBtn = new Button("Удалить", VaadinIcon.TRASH.create());
        delBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delBtn.setEnabled(false);
        delBtn.addClickListener(e -> showDelConfirmDialog());

        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(delBtn);
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
        dialog.setHeaderTitle("Добавление новой записи");
        dialog.open();
    }

    private void showEditEntryDialog() {
        dialog.setHeaderTitle("Изменение записи");
        dialog.open();
    }

    private void showDelConfirmDialog() {
        CustomDialog confirmDialog = new CustomDialog("Удаление записи");

        Div textDiv = new Div("Вы действительно хотите произвести удаление записей?\nДанное действие необратимо.");

        confirmDialog.addToDialogBody(textDiv);

        confirmDialog.setOkButtonText("Да, удалить");
        confirmDialog.setCloseButtonText("Нет, оставить");

        confirmDialog.open();
    }

    private void showErrorDialog(String message) {
        CustomDialog errorDialog = new CustomDialog("Ошибка");

        Div textDiv = new Div(message);
        errorDialog.addToDialogBody(textDiv);
        errorDialog.setCloseButtonEnabled(false);

        errorDialog.open();

        errorDialog.addOkClickListener(e -> errorDialog.close());
    }

    private static class FlashDriveFilter {
        private final GridListDataView<FlashDrive> dataView;

        private String name;
        private String usbInterface;
        private String usbType;
        private String capacity;
        private String writeSpeed;
        private String readSpeed;

        public FlashDriveFilter(GridListDataView<FlashDrive> dataView) {
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

        private boolean test(FlashDrive flashDrive) {
            return matches(flashDrive.getName(), name)
                    && matches(flashDrive.getUsbInterface(), usbInterface)
                    && matches(flashDrive.getUsbType(), usbType)
                    && matchesNumeric(flashDrive.getCapacity(), capacity)
                    && matchesNumeric(flashDrive.getWriteSpeed(), writeSpeed)
                    && matchesNumeric(flashDrive.getReadSpeed(), readSpeed);
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
}
