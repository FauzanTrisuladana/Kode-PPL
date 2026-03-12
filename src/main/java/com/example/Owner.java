package com.example;

import java.util.Objects;

public class Owner {
    private final String id;
    private final String name;
    private final String email;

    public Owner(String id, String name, String email) {
        this.id = requireValue(id, "id");
        this.name = requireValue(name, "name");
        this.email = requireValue(email, "email");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    private String requireValue(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " tidak boleh kosong");
        }
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Owner owner)) {
            return false;
        }
        return id.equals(owner.id)
                && name.equals(owner.name)
                && email.equals(owner.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}