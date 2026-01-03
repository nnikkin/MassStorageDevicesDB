package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Views.NavBar;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("error-page")
public class ErrorPageView extends AppLayout {
    public ErrorPageView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        H1 pageTitle = new H1("Ошибка");
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

        layout.add(new H1("Страница не найдена."));

        setContent(layout);
    }
}