package com.ecommerce.repository;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find orders by user
    List<Order> findByUser(User user);

    // Find orders by status
    List<Order> findByStatus(String status);

    // Find orders placed between two dates
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
