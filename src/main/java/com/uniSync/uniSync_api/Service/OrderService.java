package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order placeOrder(Long userId, Map<Long, Integer> itemIdToQuantity);
    List<Order> getUserOrders(Long userId);
}

