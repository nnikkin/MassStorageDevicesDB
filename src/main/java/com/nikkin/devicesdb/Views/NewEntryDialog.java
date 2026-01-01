/*package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Domain.Bytes;
import com.nikkin.devicesdb.Domain.UsbInterface;
import com.nikkin.devicesdb.Entities.FlashDrive;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;


public class NewEntryDialog extends CustomDialog {

    public NewEntryDialog() {
        setHeaderTitle("Добавление новой записи");
        createDialogComponents();
    }

    private void createDialogComponents() {
        TextArea nameField = new TextArea("Название:");
        NumberField capacityField = new NumberField("Объём:");

        // RAM related fields
        TextArea ramModelField = new TextArea("Модель:");
        TextArea ramManufacturerField = new TextArea("Производитель:");
        RadioButtonGroup<String> ramModuleTypeRadio = new RadioButtonGroup<>("Тип модуля:");
        ramModuleTypeRadio.setItems("DIMM", "SO-DIMM");
        ComboBox<String> ramMemoryTypeBox = new ComboBox<>("Тип памяти:");
        ramMemoryTypeBox.setItems("DDR3", "DDR3L", "DDR4", "DDR5");
        NumberField ramFrequencyField = new NumberField("Тактовая частота:");
        ramFrequencyField.setSuffixComponent(new Div("МГц"));
        NumberField ramCasLatencyField = new NumberField("CAS-латентность (CL):");
        Checkbox ramEccSupportCheck = new Checkbox("ECC-память");

        // Flash drive related fields
        ComboBox<UsbInterface> usbInterfaceBox = new ComboBox<>("Интерфейс:");
        usbInterfaceBox.setItems(UsbInterface.values());
        usbInterfaceBox.setItemLabelGenerator(UsbInterface::getLabel);
        ComboBox<String> usbTypeBox = new ComboBox<>("Версия USB:");
        usbTypeBox.setItems("1.0", "2.0", "3.0", "3.1", "3.2");

        // Optical disc related fields
        ComboBox<String> opticalRewriteTypeBox = new ComboBox<>("Тип записи:");
        opticalRewriteTypeBox.setItems("R", "-R","+R","RW", "-RW","+RW");
        NumberField opticalSpeedMultiplierField = new NumberField("Множитель скорости:");
        opticalSpeedMultiplierField.setSuffixComponent(new Div("x"));
        NumberField opticalLayersField = new NumberField("Количество слоёв:");

        // Floppy related fields
        ComboBox<String> floppyFormatBox = new ComboBox<>("Формат:");
        floppyFormatBox.setItems("2\"", "2.5\"", "3\"", "3.5\"", "4\"", "5.25\"", "8\"");
        ComboBox<String> floppyDensityBox = new ComboBox<>("Плотность:");
        floppyDensityBox.setItems("SD", "DD", "HD", "ED");

        // HDD related fields
        RadioButtonGroup<String> hddFormatRadio = new RadioButtonGroup<>("Формат:");
        hddFormatRadio.setItems("2.5\"", "3.5\"");
        ComboBox<String> hddInterfaceBox = new ComboBox<>("Интерфейс:");
        hddInterfaceBox.setItems("SATA", "SCSI", "SAS");
        NumberField hddRpmField = new NumberField("RPM:");
        hddRpmField.setSuffixComponent(new Div("об/мин"));
        NumberField hddCacheField = new NumberField("Размер кэша:");
        hddCacheField.setSuffixComponent(new Div("МБ"));

        // SSD related fields
        ComboBox<String> ssdNandTypeBox = new ComboBox<>("Количество бит на ячейку:");
        ssdNandTypeBox.setItems("SLC", "MLC", "TLC", "QLC");
        NumberField ssdTbwField = new NumberField("Максимальный ресурс записи:");
        ssdTbwField.setSuffixComponent(new Div("ТБ"));

        // other optional fields
        NumberField powerConsumptionField = new NumberField("Энергопотребление (Вт):");
        powerConsumptionField.setSuffixComponent(new Div("Вт"));
        NumberField readSpeedField = new NumberField("Скорость записи:");
        readSpeedField.setSuffixComponent(new Div("МБ/с"));
        NumberField writeSpeedField = new NumberField("Скорость чтения:");
        writeSpeedField.setSuffixComponent(new Div("МБ/с"));

        // Настройки полей
        for (TextArea field : new TextArea[]{nameField, ramManufacturerField, ramModelField}) {
            field.setMinRows(1);
            field.setMaxRows(1);
        }

        ComboBox<Bytes> suffixBox = new ComboBox<>("");
        suffixBox.setWidth(10, Unit.VW);
        suffixBox.setItems(Bytes.values());
        suffixBox.setItemLabelGenerator(Bytes::getLabel);
        suffixBox.setValue(Bytes.MB);
        capacityField.setSuffixComponent(suffixBox);

        addToDialogBody(
                nameField, capacityField, ramModelField, ramManufacturerField, ramModuleTypeRadio, ramMemoryTypeBox,
                ramFrequencyField, ramCasLatencyField, ramEccSupportCheck, usbInterfaceBox, usbTypeBox,
                opticalSpeedMultiplierField, opticalRewriteTypeBox, opticalLayersField, floppyFormatBox,
                floppyDensityBox, hddFormatRadio, hddInterfaceBox, hddRpmField, hddCacheField, ssdNandTypeBox,
                ssdTbwField, powerConsumptionField, readSpeedField, writeSpeedField
        );

        if (storageClass == FlashDrive.class) {
            ramModelField.setVisible(false);
            ramManufacturerField.setVisible(false);
            ramFrequencyField.setVisible(false);
            ramCasLatencyField.setVisible(false);
            ramEccSupportCheck.setVisible(false);
            ramMemoryTypeBox.setVisible(false);
            ramModuleTypeRadio.setVisible(false);

            opticalLayersField.setVisible(false);
            opticalRewriteTypeBox.setVisible(false);
            opticalSpeedMultiplierField.setVisible(false);

            hddFormatRadio.setVisible(false);
            hddCacheField.setVisible(false);
            hddInterfaceBox.setVisible(false);
            hddRpmField.setVisible(false);

            ssdTbwField.setVisible(false);
            ssdNandTypeBox.setVisible(false);

            floppyFormatBox.setVisible(false);
            floppyDensityBox.setVisible(false);

            powerConsumptionField.setVisible(false);
        }
    }
}
*/