package com.example.wechatcontact.model;

/**
 * Data model for a contact item
 */
public class Contact {

    private final String name;
    private final String firstLetter;

    public Contact(String name) {
        this.name = name;

        // Get first letter for grouping
        this.firstLetter = name.substring(0,1).toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getFirstLetter() {
        return firstLetter;
    }
}