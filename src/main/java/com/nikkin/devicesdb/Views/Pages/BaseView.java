package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Dto.Identifiable;
import com.nikkin.devicesdb.Services.BaseService;
import com.nikkin.devicesdb.Views.BaseDialog;
import com.nikkin.devicesdb.Views.BaseForm;
import com.nikkin.devicesdb.Views.CustomDialog;
import com.nikkin.devicesdb.Views.NavBar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// E - Entity, D - Dto
public abstract class BaseView<E, D extends Identifiable> extends AppLayout {
    private BaseService<E, D> service;
    private BaseForm<D> dialogForm;
    private Grid<D> grid;
    private List<Component> buttons;
    private Button addBtn, delBtn, refreshBtn, editBtn;

    public BaseView(String title, BaseService<E, D> service) {
        this.service = service;

        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        H1 pageTitle = new H1("Запоминающие устройства - " + title);
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

        setPrimarySection(AppLayout.Section.DRAWER);

        HorizontalLayout tableMenu = new HorizontalLayout();

        setupButtons();
        tableMenu.add(buttons);

        setContent(layout);

        initTable();
        fillTable();
        setupFilters();

        layout.add(tableMenu, grid);
    }

    public Grid<D> getGrid() {
        return grid;
    }

    public void setGrid(Grid<D> grid) {
        this.grid = grid;
    }

    private void fillTable() {
        List<D> items = service.getAll();
        grid.getListDataView().setItems(items);
    }

    private void refreshGrid() {
        fillTable();
        grid.deselectAll();
    }

    private void setupButtons() {
        buttons = new ArrayList<>();

        addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBtn.addClickListener(e -> showNewEntryDialog());

        editBtn = new Button("Изменить", VaadinIcon.PENCIL.create());
        editBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editBtn.setEnabled(false);
        editBtn.addClickListener(e -> {
            var selected = grid.getSelectedItems();
            if (selected.size() != 1) {
                showErrorDialog("Выберите одну запись для редактирования");
            } else {
                showEditDialog(selected.iterator().next());
            }
        });

        delBtn = new Button("Удалить", VaadinIcon.TRASH.create());
        delBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delBtn.setEnabled(false);
        delBtn.addClickListener(e -> showDelConfirmDialog());

        refreshBtn = new Button("Обновить", VaadinIcon.REFRESH.create());
        refreshBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshBtn.addClickListener(e -> refreshGrid());

        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(delBtn);
        buttons.add(refreshBtn);
    }

    protected Component createFilterHeader(String labelText,
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

    protected abstract BaseForm<D> createForm();

    protected void showNewEntryDialog() {
        BaseForm<D> form = createForm();
        BaseDialog<D> dialog = new BaseDialog<>(null, form, dto -> {
            service.add(dto);
            refreshGrid();
        });

        dialog.setHeaderTitle("Добавить запись");
        dialog.open();
    }

    protected void showEditDialog(D oldDto) {
        BaseForm<D> form = createForm();
        BaseDialog<D> dialog = new BaseDialog<>(oldDto, form, dto -> {
            service.update(dto.id(), dto);
            refreshGrid();
        });

        dialog.setHeaderTitle("Изменить запись");
        dialog.open();
    }

    protected void showDelConfirmDialog() {
        var selectedItems = grid.getSelectedItems();
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
            List<Long> itemsToDelete = selectedItems
                    .stream()
                    .map(D::id)
                    .toList();

            itemsToDelete.forEach(id -> service.delete(id));

            refreshGrid();

            confirmDialog.close();

            Notification.show(
                    "Удалено записей: " + itemsToDelete.size(),
                    3000,
                    Notification.Position.BOTTOM_END
            ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        confirmDialog.open();
    }

    protected void showErrorDialog(String message) {
        Div textDiv = new Div(message);

        CustomDialog errorDialog = new CustomDialog("Ошибка", textDiv);
        errorDialog.setCancelButtonVisible(false);

        errorDialog.open();

        errorDialog.addOkClickListener(e -> errorDialog.close());
    }

    protected void changeEditBtnState(boolean state) {
        editBtn.setEnabled(state);
    }

    protected void changeDelBtnState(boolean state) {
        delBtn.setEnabled(state);
    }

    protected abstract void initTable();
    protected abstract void setupFilters();
}
