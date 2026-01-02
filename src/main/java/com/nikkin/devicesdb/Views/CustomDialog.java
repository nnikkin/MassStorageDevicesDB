package com.nikkin.devicesdb.Views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableConsumer;

import java.util.Collection;

public class CustomDialog extends Dialog {
    private final VerticalLayout dialogLayout = new VerticalLayout();
    private final Component[] dialogComponents;
    private final Button okDialogButton = new Button();
    private final Button cancelDialogButton = new Button();

    public CustomDialog() {
        setHeaderTitle("Заголовок");
        dialogComponents = null;
        Create();
        okDialogButton.setText("OK");
        cancelDialogButton.setText("Отмена");
    }

    public CustomDialog(String title) {
        setHeaderTitle(title);
        dialogComponents = null;
        Create();
        okDialogButton.setText("OK");
        cancelDialogButton.setText("Отмена");
    }

    public CustomDialog(String title, Component... dialogComponents) {
        setHeaderTitle(title);
        this.dialogComponents = dialogComponents;
        Create();
        okDialogButton.setText("OK");
        cancelDialogButton.setText("Отмена");
    }

    public CustomDialog(String title, String okText, String cancelText, Component... dialogComponents) {
        setHeaderTitle(title);
        this.dialogComponents = dialogComponents;
        Create();
        okDialogButton.setText(okText);
        cancelDialogButton.setText(cancelText);
    }

    private void Create() {
        setWidth(35, Unit.VW);
        dialogLayout.setWidthFull();
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        if (dialogComponents != null)
            dialogLayout.add(dialogComponents);

        okDialogButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getFooter().add(cancelDialogButton, okDialogButton);

        add(dialogLayout);

        cancelDialogButton.addClickListener(e -> close());
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);
    }

    public void setOkButtonText(String text) {okDialogButton.setText(text);}

    public void setCancelDialogButtonText(String text) {cancelDialogButton.setText(text);}

    public void setOkButtonEnabled(boolean flag) {okDialogButton.setEnabled(flag);}

    public void setCancelButtonEnabled(boolean flag) {cancelDialogButton.setEnabled(flag);}

    public void setOkButtonVisible(boolean flag) {okDialogButton.setVisible(flag);}

    public void setCancelButtonVisible(boolean flag) {cancelDialogButton.setVisible(flag);}

    public void addToDialogBody(Component... components) {
        dialogLayout.add(components);
    }

    public void addOkClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        okDialogButton.addClickListener(listener);
    }

    public void addCancelClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        cancelDialogButton.addClickListener(listener);
    }
}
