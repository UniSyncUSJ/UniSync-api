package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Item;
import com.uniSync.uniSync_api.Model.Order;
import com.uniSync.uniSync_api.Model.OrderItem;
import com.uniSync.uniSync_api.Model.User;
import com.uniSync.uniSync_api.Repository.ItemRepository;
import com.uniSync.uniSync_api.Repository.OrderRepository;
import com.uniSync.uniSync_api.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Order placeOrder(Long userId, Map<Long, Integer> itemIdToQuantity) {
        // 1. Retrieve the user placing the order
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Create a new order and set initial info
        Order order = new Order();
        order.setBuyer(user);                          // Set the buyer of the order
        order.setOrderDate(LocalDateTime.now());       // Timestamp of the order
        order.setStatus("PENDING");                    // Initial status

        List<OrderItem> orderItems = new ArrayList<>(); // Prepare list to hold ordered items

        // 3. Loop through each item ID and quantity in the order request
        for (Map.Entry<Long, Integer> entry : itemIdToQuantity.entrySet()) {
            Long itemId = entry.getKey();      // The ID of the item
            int quantity = entry.getValue();   // How many of that item is being ordered

            // 3.1. Fetch the item from the database
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            // 3.2. Check if there's enough stock
            if (item.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock for item: " + item.getName());
            }

            // 3.3. Reduce the stock and save the item
            item.setStock(item.getStock() - quantity);
            itemRepository.save(item); // Save the updated stock

            // 3.4. Create an OrderItem (represents one type of product in this order)
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setQuantity(quantity);
            orderItem.setPriceAtOrderTime(item.getPrice()); // Save price in case it changes later
            orderItem.setOrder(order);                      // Link back to parent order

            orderItems.add(orderItem); // Add to the order's list of items
        }

        // 4. Add all order items to the order
        order.setOrderItems(orderItems);

        // 5. Save and return the full order with all items
        return orderRepository.save(order);
    }


    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByBuyer_Id(userId);
    }
}

