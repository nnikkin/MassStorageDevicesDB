package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Entities.FlashDrive;
import com.nikkin.devicesdb.Repos.FlashDriveRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.ArrayList;
import java.util.List;

@Route("flash")
public class FlashDriveView extends AppLayout {
    private final FlashDriveRepository flashDriveRepository;

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

        Grid<FlashDrive> flashDriveGrid = new Grid<>();

        HorizontalLayout tableMenu = new HorizontalLayout();

        tableMenu.add(getButtons());

        layout.add(tableMenu, flashDriveGrid);

        setContent(layout);

        InitTable(flashDriveGrid);
    }

    private void InitTable(Grid<FlashDrive> table) {
        //table.addColumn("ID");
        //table.addColumn("Название");
        //table.addColumn("Интерфейс USB");
        //table.addColumn("Тип USB");
        //table.addColumn("Объём (Гб)");
        //table.addColumn("Скорость чтения (/)");
        //table.addColumn("Скорость записи (/)");

        //List<FlashDrive> list = flashDriveRepository.findAll();
        //table.setItems(list);
    }

    private List<Component> getButtons() {
        List<Component> btns = new ArrayList<>();

        Button addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBtn.addClickListener(e -> showNewEntryDialog());

        Button delBtn = new Button("Удалить", VaadinIcon.TRASH.create());
        delBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button searchBtn = new Button("Поиск...", VaadinIcon.SEARCH.create());

        btns.add(addBtn);
        btns.add(delBtn);
        btns.add(searchBtn);

        return btns;
    }

    private void showNewEntryDialog() {
        NewEntryDialog dialog = new NewEntryDialog();

        dialog.open();
    }
}
