package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Domain.*;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;

public class DeviceDialogBuilder {
    private final CustomDialog dialog;

    public DeviceDialogBuilder() {
        this.dialog = new CustomDialog("title");
    }

    public DeviceDialogBuilder(String title) {
        this.dialog = new CustomDialog(title);
    }

    public DeviceDialogBuilder withStartingFields() {
        TextArea nameField = new TextArea("Название:");
        nameField.setMinRows(1);
        nameField.setMaxRows(1);
        nameField.setMinLength(1);
        nameField.setMaxLength(30);
        nameField.setClearButtonVisible(true);
        dialog.addToDialogBody(nameField);

        NumberField capacityField = new NumberField("Объём:");
        capacityField.setClearButtonVisible(true);
        capacityField.setRequiredIndicatorVisible(true);

        ComboBox<Bytes> suffixBox = createBytesSuffixBox();
        capacityField.setSuffixComponent(suffixBox);
        dialog.addToDialogBody(capacityField);

        return this;
    }

    private ComboBox<Bytes> createBytesSuffixBox() {
        ComboBox<Bytes> suffixBox = new ComboBox<>("");
        suffixBox.setItems(Bytes.values());
        suffixBox.setItemLabelGenerator(Bytes::getLabel);
        suffixBox.setValue(Bytes.GB);

        return suffixBox;
    }

    public DeviceDialogBuilder withFlashDriveFields() {
        ComboBox<UsbInterface> usbInterfaceBox = new ComboBox<>("Интерфейс:");
        usbInterfaceBox.setItems(UsbInterface.values());
        usbInterfaceBox.setItemLabelGenerator(UsbInterface::getLabel);
        usbInterfaceBox.setClearButtonVisible(true);

        ComboBox<String> usbTypeBox = new ComboBox<>("Версия USB:");
        usbTypeBox.setItems("1.0", "2.0", "3.0", "3.1", "3.2");
        usbTypeBox.setClearButtonVisible(true);

        dialog.addToDialogBody(usbTypeBox, usbInterfaceBox);

        return this;
    }

    public DeviceDialogBuilder withRamFields() {
        TextArea ramModelField = new TextArea("Модель:");
        ramModelField.setMinRows(1);
        ramModelField.setMaxRows(1);
        ramModelField.setMinLength(1);
        ramModelField.setMaxLength(30);
        ramModelField.setClearButtonVisible(true);

        TextArea ramManufacturerField = new TextArea("Производитель:");
        ramManufacturerField.setMinRows(1);
        ramManufacturerField.setMaxRows(1);
        ramManufacturerField.setMinLength(1);
        ramManufacturerField.setMaxLength(30);
        ramManufacturerField.setClearButtonVisible(true);

        RadioButtonGroup<String> ramModuleTypeRadio = new RadioButtonGroup<>("Тип модуля:");
        ramModuleTypeRadio.setItems("DIMM", "SO-DIMM");

        ComboBox<String> ramMemoryTypeBox = new ComboBox<>("Тип памяти:");
        ramMemoryTypeBox.setItems("DDR3", "DDR3L", "DDR4", "DDR5");

        NumberField ramFrequencyField = new NumberField("Тактовая частота:");
        ramFrequencyField.setSuffixComponent(new Div("МГц"));
        ramFrequencyField.setClearButtonVisible(true);

        NumberField ramCasLatencyField = new NumberField("CAS-латентность (CL):");
        Checkbox ramEccSupportCheck = new Checkbox("ECC-память");
        ramCasLatencyField.setClearButtonVisible(true);

        dialog.addToDialogBody(
                ramModelField, ramManufacturerField, ramModuleTypeRadio,
                ramMemoryTypeBox, ramFrequencyField, ramCasLatencyField, ramEccSupportCheck
        );

        return this;
    }

    public DeviceDialogBuilder withOpticalFields() {
        ComboBox<String> opticalRewriteTypeBox = new ComboBox<>("Тип записи:");
        opticalRewriteTypeBox.setItems("R", "-R","+R","RW", "-RW","+RW");
        opticalRewriteTypeBox.setClearButtonVisible(true);

        NumberField opticalSpeedMultiplierField = new NumberField("Множитель скорости:");
        opticalSpeedMultiplierField.setSuffixComponent(new Div("x"));
        opticalSpeedMultiplierField.setClearButtonVisible(true);

        NumberField opticalLayersField = new NumberField("Количество слоёв:");
        opticalLayersField.setClearButtonVisible(true);

        dialog.addToDialogBody(opticalRewriteTypeBox, opticalSpeedMultiplierField, opticalLayersField);

        return this;
    }

    public DeviceDialogBuilder withFloppyFields() {
        ComboBox<String> floppyFormatBox = new ComboBox<>("Формат:");
        floppyFormatBox.setItems("2\"", "2.5\"", "3\"", "3.5\"", "4\"", "5.25\"", "8\"");
        floppyFormatBox.setClearButtonVisible(true);

        ComboBox<FloppyDensity> floppyDensityBox = new ComboBox<>("Плотность:");
        floppyDensityBox.setItems(FloppyDensity.values());
        floppyDensityBox.setClearButtonVisible(true);

        dialog.addToDialogBody(floppyFormatBox, floppyDensityBox);

        return this;
    }

    public DeviceDialogBuilder withHddFields() {
        ComboBox<HddInterface> hddInterfaceComboBox = new ComboBox<>("Интерфейс:");
        hddInterfaceComboBox.setItems(HddInterface.values());
        hddInterfaceComboBox.setRequiredIndicatorVisible(true);
        hddInterfaceComboBox.setClearButtonVisible(true);

        RadioButtonGroup<String> hddFormatRadio = new RadioButtonGroup<>("Формат:");
        hddFormatRadio.setItems("2.5\"", "3.5\"");

        NumberField hddRpmField = new NumberField("RPM:");
        hddRpmField.setSuffixComponent(new Div("об/мин"));
        hddRpmField.setClearButtonVisible(true);

        NumberField hddCacheField = new NumberField("Размер кэша:");
        hddCacheField.setSuffixComponent(new Div("МБ"));
        hddCacheField.setClearButtonVisible(true);

        dialog.addToDialogBody(hddFormatRadio, hddRpmField, hddCacheField);

        return this;
    }

    public DeviceDialogBuilder withSsdFields() {
        ComboBox<SsdInterface> ssdInterfaceComboBox = new ComboBox<>("Интерфейс:");
        ssdInterfaceComboBox.setItems(SsdInterface.values());
        ssdInterfaceComboBox.setRequiredIndicatorVisible(true);
        ssdInterfaceComboBox.setClearButtonVisible(true);

        ComboBox<String> ssdNandTypeBox = new ComboBox<>("Количество бит на ячейку:");
        ssdNandTypeBox.setItems(
                "SLC (Single Level Cell / 1 бит)", "MLC (Multi Level Cell / 2 бита)",
                "TLC (Triple Level Cell / 3 бита)", "QLC (Quad Level Cell / 4 бита)"
        );
        ssdNandTypeBox.setClearButtonVisible(true);

        NumberField ssdTbwField = new NumberField("Максимальный ресурс записи:");
        ssdTbwField.setSuffixComponent(new Div("ТБ"));
        ssdTbwField.setClearButtonVisible(true);

        dialog.addToDialogBody(ssdNandTypeBox, ssdTbwField);

        return this;
    }

    public DeviceDialogBuilder withPowerField() {
        NumberField powerConsumptionField = new NumberField("Энергопотребление (Вт):");
        powerConsumptionField.setSuffixComponent(new Div("Вт"));
        powerConsumptionField.setClearButtonVisible(true);

        dialog.addToDialogBody(powerConsumptionField);

        return this;
    }

    public DeviceDialogBuilder withSpeedFields() {
        NumberField readSpeedField = new NumberField("Скорость записи:");
        readSpeedField.setSuffixComponent(new Div("МБ/с"));
        readSpeedField.setClearButtonVisible(true);

        NumberField writeSpeedField = new NumberField("Скорость чтения:");
        writeSpeedField.setSuffixComponent(new Div("МБ/с"));
        writeSpeedField.setClearButtonVisible(true);

        dialog.addToDialogBody(readSpeedField, writeSpeedField);

        return this;
    }

    public CustomDialog build() {
        return dialog;
    }
}
