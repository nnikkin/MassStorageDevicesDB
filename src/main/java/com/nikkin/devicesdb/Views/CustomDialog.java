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

public class CustomDialog extends Dialog {
    private final VerticalLayout dialogLayout = new VerticalLayout();
    private final Button okDialogButton = new Button();
    private final Button closeDialogButton = new Button();

    public CustomDialog() {
        setHeaderTitle("Заголовок");
        Create();
        okDialogButton.setText("OK");
        closeDialogButton.setText("Отмена");
    }

    public CustomDialog(String title, String okText, String closeText) {
        setHeaderTitle(title);
        Create();
        okDialogButton.setText(okText);
        closeDialogButton.setText(closeText);
    }

    public CustomDialog(String title) {
        setHeaderTitle(title);
        Create();
        okDialogButton.setText("OK");
        closeDialogButton.setText("Отмена");
    }

    private void Create() {
        setWidth(35, Unit.VW);
        dialogLayout.setWidthFull();
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        okDialogButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getFooter().add(closeDialogButton, okDialogButton);

        add(dialogLayout);

        closeDialogButton.addClickListener(e -> close());
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);
    }

    private void Create(Component... bodyComponents) {
        setWidth(35, Unit.VW);
        dialogLayout.setWidthFull();
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.add(bodyComponents);

        okDialogButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getFooter().add(closeDialogButton, okDialogButton);

        add(dialogLayout);

        closeDialogButton.addClickListener(e -> close());
    }

    public void setOkButtonText(String text) {okDialogButton.setText(text);}

    public void setCloseButtonText(String text) {closeDialogButton.setText(text);}

    public void setOkButtonEnabled(boolean flag) {okDialogButton.setEnabled(flag);}

    public void setCloseButtonEnabled(boolean flag) {closeDialogButton.setEnabled(flag);}

    public void addToDialogBody(Component... components) {
        dialogLayout.add(components);
    }

    public void addOkClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        okDialogButton.addClickListener(listener);
    }
}
