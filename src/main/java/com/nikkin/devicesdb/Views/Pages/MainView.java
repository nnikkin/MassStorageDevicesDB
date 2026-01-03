package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Views.NavBar;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

// Аннотация Route указывает, что этот класс является корневым представлением
@Route("/")
// скрипт для добавления тёмной темы
@JsModule("./prefer-color-theme.js") // ./ --> /src/main/frontend/
public class MainView extends AppLayout {

    public MainView() {
        // Заголовок
        H1 pageTitle = new H1("Запоминающие устройства");
        pageTitle.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        // Настройка навигационного меню
        DrawerToggle toggle = new DrawerToggle();
        NavBar nav = new NavBar();
        Div navDrawer = new Div(nav);
        navDrawer.setWidthFull();

        Scroller scroller = new Scroller(navDrawer);
        scroller.setClassName(LumoUtility.Padding.SMALL);

//        MyView view = new MyView();
//        setContent(view);

        addToDrawer(scroller);
        addToNavbar(toggle, pageTitle);

        setPrimarySection(Section.DRAWER);
    }
}
