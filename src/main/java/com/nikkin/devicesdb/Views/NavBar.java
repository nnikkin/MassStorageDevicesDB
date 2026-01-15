package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Views.Pages.MainView;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class NavBar extends SideNav {

    public NavBar() {
        SideNavItem homePageLink = new SideNavItem("Главная", MainView.class, VaadinIcon.HOME.create());

        SideNavItem dbsPageLink = new SideNavItem("Таблицы");
        dbsPageLink.setPrefixComponent(VaadinIcon.TABLE.create());
        dbsPageLink.addItem(new SideNavItem("ОЗУ", "/ram", new SvgIcon("icons/floppy-disc.svg")));
        dbsPageLink.addItem(new SideNavItem("Оптические диски", "/optical", VaadinIcon.DISC.create()));
        dbsPageLink.addItem(new SideNavItem("Гибкие диски", "/floppy", new SvgIcon("icons/floppy-disc.svg")));
        dbsPageLink.addItem(new SideNavItem("Жёсткие диски", "/hdd", VaadinIcon.HARDDRIVE.create()));
        dbsPageLink.addItem(new SideNavItem("Твёрдотельные накопители", "/ssd", VaadinIcon.HARDDRIVE_O.create()));
        dbsPageLink.addItem(new SideNavItem("Флеш-память", "/flash", new SvgIcon("icons/flash-drive.svg")));

        addItem(
                homePageLink,
                dbsPageLink
        );
    }
}
