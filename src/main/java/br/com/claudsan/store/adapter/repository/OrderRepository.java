package br.com.claudsan.store.adapter.repository;

import br.com.claudsan.store.application.domain.Order;
import br.com.claudsan.store.application.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findAll(Pageable pageable);

    List<Order> findByItemItemIdAndOrderStatus(Long itemId, OrderStatus orderStatus);
}