package com.megait.mymall.service;

import com.megait.mymall.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
}
