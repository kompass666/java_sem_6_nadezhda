package org.nadezhdaPoliaeva.service;

import org.nadezhdaPoliaeva.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll();
    Item getById(Long id);
    Item create(Item item);
    Item update(Long id, Item item);
    void delete(Long id);
}