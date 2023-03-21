package com.example.socketchat.domain.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class BasicRepository<T> {

    protected final Map<String, T> items = new HashMap<>();

    public Optional<T> findById(String id) {
        return Optional.of(items.get(id));
    }

    public Optional<T> delete(String id) {
        return Optional.of(items.remove(id));
    }

    abstract public void save(T item);
}
