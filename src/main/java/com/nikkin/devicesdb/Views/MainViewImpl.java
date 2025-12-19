package com.nikkin.devicesdb.Views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

// Аннотация Route указывает, что этот класс является корневым представлением
@Route
// скрипт для добавления тёмной темы
@JsModule("./prefer-color-theme.js") // ./ --> /src/main/frontend/
public class MainViewImpl extends AppLayout implements MainView {

    public MainViewImpl() {
        // Заголовок
        H1 pageTitle = new H1("ЗУ СУБД");
        pageTitle.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        // Настройка навигационного меню
        DrawerToggle toggle = new DrawerToggle();
        SideNav nav = getSideNav();
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

    private SideNav getSideNav() {
        SideNav nav = new SideNav();

        SideNavItem homePageLink = new SideNavItem("Главная", this.getClass(), VaadinIcon.HOME.create());

        SideNavItem adminPageLink = new SideNavItem("Управление", "/", VaadinIcon.COGS.create());
        adminPageLink.addItem(new SideNavItem("Устройства", "/", VaadinIcon.TABLE.create()));
        adminPageLink.addItem(new SideNavItem("Типы", "/", VaadinIcon.TABLE.create()));
        adminPageLink.addItem(new SideNavItem("Производители", "/", VaadinIcon.TABLE.create()));

        SideNavItem reportsPageLink = new SideNavItem("Отчёты", "/",VaadinIcon.FILE_TEXT.create());

        nav.addItem(
                homePageLink,
                adminPageLink,
                reportsPageLink
        );
        return nav;
    }
}
