package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Views.Pages.MainView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.server.streams.DownloadHandler;

public class NavBar extends SideNav {

    public NavBar() {
        SideNavItem homePageLink = new SideNavItem("Главная", MainView.class, VaadinIcon.HOME.create());

        SideNavItem dbsPageLink = new SideNavItem("Таблицы");
        dbsPageLink.setPrefixComponent(VaadinIcon.TABLE.create());
        dbsPageLink.addItem(new SideNavItem("ОЗУ", "/ram",
                createIcon("icons/ram2.ico")));
        dbsPageLink.addItem(new SideNavItem("Оптические диски", "/optical",
                createIcon("icons/optical.ico")));
        dbsPageLink.addItem(new SideNavItem("Гибкие диски", "/floppy",
                createIcon("icons/floppy2.ico")));
        dbsPageLink.addItem(new SideNavItem("Жёсткие диски", "/hdd",
                createIcon("icons/drive.ico")));
        dbsPageLink.addItem(new SideNavItem("Твёрдотельные накопители", "/ssd",
                createIcon("icons/drive.ico")));
        dbsPageLink.addItem(new SideNavItem("Флеш-память", "/flash",
                createIcon("icons/flash.ico")));

        addItem(
                homePageLink,
                dbsPageLink
        );
    }

    private Image createIcon(String path) {
        Image menuIcon = new Image(DownloadHandler.forServletResource(path), null);
        menuIcon.getStyle().setWidth("1em");

        return menuIcon;
    }
}
