package com.nikkin.devicesdb.Views;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class NavBar extends SideNav {

    public NavBar() {
        SideNavItem homePageLink = new SideNavItem("Главная", MainView.class, VaadinIcon.HOME.create());

        SideNavItem dbsPageLink = new SideNavItem("Таблицы", "", VaadinIcon.TABLE.create());
        dbsPageLink.addItem(new SideNavItem("ОЗУ", "/ram"));
        dbsPageLink.addItem(new SideNavItem("Оптические диски", "/optical"));
        dbsPageLink.addItem(new SideNavItem("Гибкие диски", "/floppy"));
        dbsPageLink.addItem(new SideNavItem("Жёсткие диски", "/hdd"));
        dbsPageLink.addItem(new SideNavItem("Твёрдотельные накопители", "/ssd"));
        dbsPageLink.addItem(new SideNavItem("Флеш-память", "/flash"));

        SideNavItem dashboardPageLink = new SideNavItem("Дашборд", "/dashboard", VaadinIcon.FILE_TEXT.create());

        addItem(
                homePageLink,
                dbsPageLink,
                dashboardPageLink
        );
    }
}
