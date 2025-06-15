package com.uniSync.uniSync_api.Controller;

import com.uniSync.uniSync_api.Model.Item;
import com.uniSync.uniSync_api.Model.Order;
import com.uniSync.uniSync_api.Service.ItemService;
import com.uniSync.uniSync_api.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/marketplace")
public class MarketplaceController {

    private ItemService itemService;
    private OrderService orderService;

    public MarketplaceController(ItemService itemService, OrderService orderService) {
        this.itemService = itemService;
        this.orderService = orderService;
    }

    // View all items
    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    // View items by faculty
    @GetMapping("/faculty/{facultyId}/items")
    public ResponseEntity<List<Item>> getItemsByFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(itemService.getItemsByFaculty(facultyId));
    }

    // View items by department
    @GetMapping("/department/{departmentId}/items")
    public ResponseEntity<List<Item>> getItemsByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(itemService.getItemsByDepartment(departmentId));
    }

    // View items by society
    @GetMapping("/society/{societyId}/items")
    public ResponseEntity<List<Item>> getItemsBySociety(@PathVariable Long societyId) {
        return ResponseEntity.ok(itemService.getItemsBySociety(societyId));
    }

    // Place an order
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> placeOrder(
            @PathVariable Long userId,
            @RequestBody Map<Long, Integer> itemIdToQuantity // itemId -> quantity
    ) {
        Order order = orderService.placeOrder(userId, itemIdToQuantity);
        return ResponseEntity.ok(order);
    }

    // View a user's orders
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }
}

