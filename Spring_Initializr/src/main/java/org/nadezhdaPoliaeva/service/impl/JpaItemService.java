package org.nadezhdaPoliaeva.service.impl;

import org.nadezhdaPoliaeva.model.Item;
import org.nadezhdaPoliaeva.service.ItemService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile("jpa")
public class JpaItemService implements ItemService {

    private final Map<Long, Item> storage = new HashMap<>();

    @Override
    public List<Item> getAll() {
        return storage.values().stream().sorted(Comparator.comparing(Item::getId)).toList();
    }

    @Override
    public Item getById(Long id) {
        return storage.get(id);
    }

    @Override
    public Item create(Item item) {
        Long id = item.getId();
        if (id == null) id = nextId();
        item.setId(id);
        storage.put(id, item);
        return item;
    }

    @Override
    public Item update(Long id, Item item) {
        item.setId(id);
        storage.put(id, item);
        return item;
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

    private long nextId() {
        return storage.keySet().stream().mapToLong(x -> x).max().orElse(0L) + 1;
    }
}