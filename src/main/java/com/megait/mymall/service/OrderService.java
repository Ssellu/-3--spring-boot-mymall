package com.megait.mymall.service;

import com.megait.mymall.domain.*;
import com.megait.mymall.repository.ItemRepository;
import com.megait.mymall.repository.MemberRepository;
import com.megait.mymall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    private final ItemService itemService;

    @PostConstruct
    @Transactional
    protected void createTestUserLikes() {
        Member member = memberRepository.findByEmail("admin@test.com").orElse(null);
        List<Item> itemList = itemRepository.findAll();
        itemList.forEach(e -> itemService.addLike(member, e.getId()));
    }

    @Transactional
    public void addCart(Member member0, List<Long> itemIds) {
        final Member member = memberRepository.getById(member0.getId());

        Optional<Order> optional = orderRepository.findByStatusAndMember(OrderStatus.CART, member);

        log.info("order : {}", optional.isPresent());

        final Order order = optional.orElseGet(
                ()->
                        orderRepository.save(
                                Order.builder()
                                        .member(member)
                                        .status(OrderStatus.CART)
                                        .orderDate(LocalDateTime.now())
                                        .build()
                        )
        );

        List<Item> newItemList = itemRepository.findAllById(itemIds);

        List<OrderItem> newOrderItemList = newItemList.stream().map(item -> OrderItem.builder()
                .item(item)
                .order(order)
                .count(1)
                .orderPrice(item.getPrice())
                .build()).collect(Collectors.toList());
        if (order.getOrderItems() == null) {
            order.setOrderItems(new ArrayList<>());
        }
        order.getOrderItems().addAll(newOrderItemList);
    }

    public List<OrderItem> getCart(Member member) {
        Optional<Order> optional = orderRepository.findByStatusAndMember(OrderStatus.CART, member);
        return optional.orElseThrow(()->new IllegalStateException("empty.cart")).getOrderItems();
    }


    public int getTotalPrice(List<OrderItem> list) {
        return list.stream().mapToInt(orderItem -> orderItem.getItem().getPrice()).sum();
    }
}