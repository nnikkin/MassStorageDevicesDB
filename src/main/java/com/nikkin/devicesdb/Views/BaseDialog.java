package com.nikkin.devicesdb.Views;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.function.SerializableConsumer;

public class BaseDialog<D> extends CustomDialog {
    private final BaseForm<D> form;
    private final SerializableConsumer<D> onSaveCallback;
    private boolean isEditMode;

    public BaseDialog(D dto, BaseForm<D> form, SerializableConsumer<D> onSaveCallback) {
        this.onSaveCallback = onSaveCallback;

        this.form = form;
        isEditMode = false;

        if (dto != null) {
            isEditMode = true;
            form.setDto(dto);
        }

        addToDialogBody(form);

        addOkClickListener(e -> save());
    }

    private void save() {
        if (!form.isValid()) {
            Notification notification = Notification.show(
                    "Пожалуйста, исправьте ошибки в форме",
                    3000,
                    Notification.Position.MIDDLE
            );
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        form.getFormDataObject().ifPresent(entity -> {
            try {
                onSaveCallback.accept(entity);
                close();

                Notification notification = Notification.show(
                        isEditMode ? "Запись обновлена" : "Запись создана",
                        3000,
                        Notification.Position.MIDDLE
                );
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (Exception e) {
                Notification notification = Notification.show(
                        "Ошибка при сохранении: " + e.getMessage(),
                        5000,
                        Notification.Position.MIDDLE
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }
}
