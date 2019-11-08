package com.logdb.utils;

import org.springframework.data.domain.Page;

public class Pager<T>{

    private final Page<T> objects;

    public Pager(Page<T> objects) {
        this.objects = objects;
    }

    public int getPageIndex() {
        return objects.getNumber() + 1;
    }

    public int getPageSize() {
        return objects.getSize();
    }

    public boolean hasNext() {
        return objects.hasNext();
    }

    public boolean hasPrevious() {
        return objects.hasPrevious();
    }

    public int getTotalPages() {
        return objects.getTotalPages();
    }

    public long getTotalElements() {
        return objects.getTotalElements();
    }

    public Page<T> getObjects() {
        return objects;
    }



    public boolean indexOutOfBounds() {
        return getPageIndex() < 0 || getPageIndex() > getTotalElements();
    }

}
