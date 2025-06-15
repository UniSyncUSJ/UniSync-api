package com.uniSync.uniSync_api.Controller;

import com.uniSync.uniSync_api.Model.Item;
import com.uniSync.uniSync_api.Service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/items")
public class AdminItemController {

    private ItemService itemService;

    public AdminItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Add a new item
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item savedItem = itemService.addItem(item);
        return ResponseEntity.ok(savedItem);
    }

    // Update an existing item
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Item item = itemService.updateItem(id, updatedItem);
        return ResponseEntity.ok(item);
    }

    // Increase stock for an item
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Item> updateStock(@PathVariable Long id, @RequestParam int add) {
        Item item = itemService.updateStock(id, add);
        return ResponseEntity.ok(item);
    }

    // Delete an item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}

