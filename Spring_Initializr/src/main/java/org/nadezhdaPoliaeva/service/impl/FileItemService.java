package org.nadezhdaPoliaeva.service.impl;

import org.nadezhdaPoliaeva.model.Item;
import org.nadezhdaPoliaeva.service.ItemService;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Profile("file")
public class FileItemService implements ItemService {

    private final Map<Long, Item> storage = new HashMap<>();

    @PostConstruct
    public void init() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("data/items.csv").getInputStream(), StandardCharsets.UTF_8)
        )) {
            // skip header
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");

                Long id = Long.parseLong(parts[0].trim());
                String name = parts[1].trim();
                Double price = Double.parseDouble(parts[2].trim());

                storage.put(id, new Item(id, name, price));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV", e);
        }
    }

    @Override
    public List<Item> getAll() {
        return storage.values().stream().sorted(Comparator.comparing(Item::getId)).toList();
    }

    @Override
    public Item getById(Long id) {
        Item item = storage.get(id);
        if (item == null) throw new ResponseStatusException(NOT_FOUND, "Item not found: " + id);
        return item;
    }

    @Override
    public Item create(Item item) {
        Long id = item.getId();
        if (id == null) {
            id = nextId();
            item.setId(id);
        }
        storage.put(id, item);
        return item;
    }

    @Override
    public Item update(Long id, Item item) {
        if (!storage.containsKey(id)) throw new ResponseStatusException(NOT_FOUND, "Item not found: " + id);
        item.setId(id);
        storage.put(id, item);
        return item;
    }

    @Override
    public void delete(Long id) {
        if (storage.remove(id) == null) throw new ResponseStatusException(NOT_FOUND, "Item not found: " + id);
    }

    private long nextId() {
        return storage.keySet().stream().mapToLong(x -> x).max().orElse(0L) + 1;
    }
}