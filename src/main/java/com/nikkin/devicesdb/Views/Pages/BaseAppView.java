package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Views.NavBar;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class BaseAppView extends AppLayout {
    private boolean isDarkThemeOn = false;

    public BaseAppView(String title) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        H1 pageTitle = new H1(title);
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

        var themeToggle = new Div(createThemeToggle());
        themeToggle.getStyle().set("margin-left", "auto");
        themeToggle.getStyle().set("padding", "15px");
        addToNavbar(themeToggle);

        setPrimarySection(AppLayout.Section.DRAWER);

        setContent(layout);
    }

    private Button createThemeToggle() {
        var toggle = new Button();
        toggle.setIcon(VaadinIcon.MOON_O.create());

        toggle.addClickListener(e -> {
            if (!isDarkThemeOn) {
                toggle.setIcon(VaadinIcon.SUN_O.create());
            } else {
                toggle.setIcon(VaadinIcon.MOON_O.create());
            }

            setDarkThemeOn();

            isDarkThemeOn = !isDarkThemeOn;
        });

        return toggle;
    }

    private void setDarkThemeOn() {
        var js = "document.documentElement.setAttribute('theme', $0)";
        getElement().executeJs(js, isDarkThemeOn ? Lumo.DARK : Lumo.LIGHT);
    }

}
