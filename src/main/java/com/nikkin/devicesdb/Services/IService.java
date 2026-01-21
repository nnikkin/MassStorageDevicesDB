package com.nikkin.devicesdb.Services;

import java.util.List;

public interface IService<D> {
    D add(D dto);
    D getById(Integer id);
    List<D> getAll();
    D delete(Integer id);
    D update(Integer id, D new_dto);
    int getItemsCount();
}
