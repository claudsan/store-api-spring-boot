package br.com.claudsan.store.application.service;

import br.com.claudsan.store.adapter.dto.OrderRequestDTO;
import br.com.claudsan.store.adapter.dto.OrderUpdateDTO;
import br.com.claudsan.store.adapter.dto.OrderViewDTO;
import br.com.claudsan.store.adapter.dto.StockMovementViewDTO;
import br.com.claudsan.store.adapter.metrics.CustomMetrics;
import br.com.claudsan.store.adapter.repository.OrderRepository;
import br.com.claudsan.store.application.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;

@Service
public class OrderService {

    private static final String METRIC_NAME = "store.order";
    private Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository repository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CustomMetrics metrics;

    @Autowired
    private StockMovmentService stockMovmentService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void createOrder(OrderRequestDTO orderRequest) {
        Order newOrder = repository.save(orderRequest.toOrder());
        udpateOrderItens(newOrder);
        logger.info(String.format("Order created Id: %d Status: %s", newOrder.getOrderId(), newOrder.getOrderStatus().name()));
        if(newOrder.getOrderId() != null)
            metrics.counter(METRIC_NAME,"new",orderRequest.toOrder().toString());
    }

    private Order udpateOrderItens(Order order) {
        Item item = itemService.findById(order.getItem().getItemId());
        if(item.getQuantityAvaiable() > 0){
            long quantityPending = abs(order.getQuantity() - order.getQuantityServed());
            if(quantityPending > item.getQuantityAvaiable()){
                createOutMovement(order, item, item.getQuantityAvaiable());
                order.setQuantityServed(quantityPending - item.getQuantityAvaiable());
                item.setQuantityAvaiable(0L);
                itemService.update(item);
                order.setOrderStatus(OrderStatus.PENDING);
                metrics.counter(METRIC_NAME,"type","pending","order",order.toString());
            }else{
                createOutMovement(order, item, quantityPending);
                item.setQuantityAvaiable(item.getQuantityAvaiable() - quantityPending);
                itemService.update(item);
                order.setQuantityServed(order.getQuantity());
                order.setOrderStatus(OrderStatus.FINISHED);
                emailService.sendOrderNotification(order);
                metrics.counter(METRIC_NAME,"type","finished","order",order.toString());
            }
        }else{
            order.setOrderStatus(OrderStatus.PENDING);
            metrics.counter(METRIC_NAME,"type","pending","order",order.toString());
        }
        repository.save(order);
        return order;

    }

    private void createOutMovement(Order order, Item item, Long quantity) {
        StockMovement stock = StockMovement.builder()
                .order(order)
                .item(item)
                .creationDate(new Date())
                .quantity(quantity)
                .typeMovement(TypeMovementStock.OUT)
                .build();
        stockMovmentService.createByOrder(stock);
    }

    public OrderViewDTO findById(Long id) {
        Order order = repository.findById(id).get();
        return order.toDTO();
    }

    public void udpatePendingOrders(StockMovement item) {
        List<Order> orders = repository.findByItemItemIdAndOrderStatus(item.getItem().getItemId(), OrderStatus.PENDING);
        orders.forEach(order -> udpateOrderItens(order));
    }

    public Page<OrderViewDTO> list(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return toPageDTO(repository.findAll(paging));
    }

    private Page<OrderViewDTO> toPageDTO(Page<Order> page) {
        return page.map((order)-> OrderViewDTO.builder()
                .item(order.getItem().toDTO())
                .quantity(order.getQuantity())
                .orderId(order.getOrderId())
                .creationDate(order.getCreationDate())
                .orderStatus(order.getOrderStatus())
                .user(order.getUser().toDTO())
                .quantityServed(order.getQuantityServed())
                .stockMovements(order.getStockMovements().stream().map(stockMovement -> stockMovement.toDTO())
                        .collect(Collectors.toList()))
                .build()
        );
    }

    public void update(OrderUpdateDTO orderUpdateDTO) {
        Order order = repository.findById(orderUpdateDTO.getOrderID()).get();
        if(order.getQuantityServed() > 0)
            throw new RuntimeException("It is not allowed to change order data already in service");
        order.setQuantity(orderUpdateDTO.getQuantity());
        order.setUser(User.builder().userId(orderUpdateDTO.getUserId()).build());
        repository.save(order);
    }
}
