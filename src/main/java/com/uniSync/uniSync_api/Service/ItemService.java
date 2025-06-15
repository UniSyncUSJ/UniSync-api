package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Item;

import java.util.List;

public interface ItemService {
    Item addItem(Item item);
    Item updateItem(Long itemId, Item updatedItem);
    Item updateStock(Long itemId, int addedStock);
    void deleteItem(Long itemId);
    List<Item> getAllItems();
    List<Item> getItemsByFaculty(Long facultyId);
    List<Item> getItemsByDepartment(Long departmentId);
    List<Item> getItemsBySociety(Long societyId);
}

