package com.jc.platform.core.entity;

import java.io.Serializable;

public abstract class BaseEntity<T> implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSave() {
        if (getId() == null || getId() == 0) {
            return true;
        }
        return false;
    }
}
