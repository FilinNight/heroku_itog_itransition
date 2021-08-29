package com.example.collectionsApp.helperСlasses.checkbox;

import com.example.collectionsApp.models.User;

import java.util.List;

public class CheckBoxUtil {
    private List<User> checkedItems;

    public List<User> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(List<User> checkedItems) {
        this.checkedItems = checkedItems;
    }
}