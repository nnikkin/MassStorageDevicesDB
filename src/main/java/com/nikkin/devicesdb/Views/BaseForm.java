package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Domain.Bytes;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.Binder;

import java.util.Optional;

public abstract class BaseForm<D> extends FormLayout {
    private Binder<D> binder;

    public BaseForm() {}

    public void setBinder(Binder<D> binder) {
        if (binder == null)
            throw new RuntimeException("binder не может быть null");

        this.binder = binder;
    }

    public boolean isValid() {
        if (binder == null)
            throw new RuntimeException("binder не может быть null");

        return binder.validate().isOk();
    }

    protected float toMegabytes(float value, Bytes unit) {
        if (unit == Bytes.MB)
            return value;
        else {
            if (unit.getRank() > Bytes.MB.getRank())
                return (float) (value * Math.pow(1024, unit.getRank() - Bytes.MB.getRank()));

            else {
                if (unit == Bytes.BIT)
                    return (float) ((value / 8) / Math.pow(1024, Bytes.MB.getRank() - 1));

                return (float) (value / Math.pow(1024, Bytes.MB.getRank() - unit.getRank()));
            }
        }
    }
    protected abstract void setDto(D dto);
    protected abstract void setupFields();
    protected abstract void clearFields();
    protected abstract void setupBinder();
    protected abstract Optional<D> getFormDataObject();
}