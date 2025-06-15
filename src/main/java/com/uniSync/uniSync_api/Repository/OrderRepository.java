package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository {

    List<Order> findByBuyer_Id(Long userId);
}
