package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Services.*;
import com.nikkin.devicesdb.Views.NavBar;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("/")
public class MainView extends BaseAppView {

    private final FlashDriveService flashDriveService;
    private final FloppyDiskService floppyDiskService;
    private final HDDService hddService;
    private final OpticalDiscService opticalDiscService;
    private final RAMService ramService;
    private final SSDService ssdService;

    public MainView(FlashDriveService flashDriveService,
                    FloppyDiskService floppyDiskService,
                    HDDService hddService,
                    OpticalDiscService opticalDiscService,
                    RAMService ramService,
                    SSDService ssdService) {
        super("Запоминающие устройства");

        this.flashDriveService = flashDriveService;
        this.floppyDiskService = floppyDiskService;
        this.hddService = hddService;
        this.opticalDiscService = opticalDiscService;
        this.ramService = ramService;
        this.ssdService = ssdService;

        // Создание дашборда
        VerticalLayout dashboardLayout = new VerticalLayout();
        dashboardLayout.setWidthFull();
        dashboardLayout.setPadding(true);
        dashboardLayout.setSpacing(true);

        // Статистические карточки
        HorizontalLayout statsLayout = createStatsCards();

        // Графики
        HorizontalLayout chartsLayout = new HorizontalLayout();
        chartsLayout.setWidthFull();

        Chart deviceCountChart = createDeviceCountChart();

        chartsLayout.add(deviceCountChart);
        chartsLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        dashboardLayout.add(
                new H2("Обзор базы данных"),
                statsLayout,
                chartsLayout
        );

        setContent(dashboardLayout);
    }

    private HorizontalLayout createStatsCards() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setSpacing(true);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);

        layout.add(
                createStatCard("Флеш-память", flashDriveService.getAll().size(),
                        new Image(DownloadHandler.forServletResource("icons/flash.ico"), "")),
                createStatCard("Дискеты", floppyDiskService.getAll().size(),
                        new Image(DownloadHandler.forServletResource("icons/floppy2.ico"), "")),
                createStatCard("Жёсткие диски", hddService.getAll().size(),
                        new Image(DownloadHandler.forServletResource("icons/drive.ico"), "")),
                createStatCard("Оптические диски", opticalDiscService.getAll().size(),
                        new Image(DownloadHandler.forServletResource("icons/optical.ico"), "")),
                createStatCard("ОЗУ", ramService.getAll().size(),
                        new Image(DownloadHandler.forServletResource("icons/ram2.ico"), "")),
                createStatCard("Твердотельные накопители", ssdService.getAll().size(),
                        new Image(DownloadHandler.forServletResource("icons/drive.ico"), ""))
        );

        return layout;
    }

    private VerticalLayout createStatCard(String title, int count, Image icon) {
        VerticalLayout card = new VerticalLayout();
        card.setAlignItems(FlexComponent.Alignment.CENTER);
        card.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        card.setPadding(true);
        card.setSpacing(false);

        card.getStyle()
                .set("border", "1px solid var(--lumo-contrast-10pct)")
                .set("border-radius", "var(--lumo-border-radius-m)")
                .set("background", "var(--lumo-base-color)")
                .set("padding", "var(--lumo-space-m)")
                .set("min-width", "150px");

        icon.getStyle().setWidth("5.5em");

        Span countSpan = new Span(String.valueOf(count));
        countSpan.getStyle()
                .set("font-size", "2em")
                .set("font-weight", "bold")
                .set("color", "var(--lumo-primary-text-color)");

        Span titleSpan = new Span(title);
        titleSpan.getStyle()
                .set("font-size", "0.9em")
                .set("color", "var(--lumo-secondary-text-color)");

        card.add(icon, countSpan, titleSpan);
        return card;
    }

    private Chart createDeviceCountChart() {
        Chart chart = new Chart(ChartType.PIE);
        chart.setWidth("50%");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Распределение устройств по типам");

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Флеш-память", flashDriveService.getAll().size()));
        series.add(new DataSeriesItem("Дискеты", floppyDiskService.getAll().size()));
        series.add(new DataSeriesItem("HDD", hddService.getAll().size()));
        series.add(new DataSeriesItem("Оптические", opticalDiscService.getAll().size()));
        series.add(new DataSeriesItem("ОЗУ", ramService.getAll().size()));
        series.add(new DataSeriesItem("SSD", ssdService.getAll().size()));

        conf.addSeries(series);

        PlotOptionsPie options = new PlotOptionsPie();
        options.setInnerSize("50%"); // размер для кругового графика
        series.setPlotOptions(options);

        return chart;
    }
}