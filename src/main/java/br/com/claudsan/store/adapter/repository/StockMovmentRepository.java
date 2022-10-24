package br.com.claudsan.store.adapter.repository;

import br.com.claudsan.store.application.domain.Order;
import br.com.claudsan.store.application.domain.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovmentRepository extends PagingAndSortingRepository<StockMovement, Long> {
    Page<StockMovement> findAll(Pageable pageable);

    List<StockMovement> findByOrderOrderId(Long orderId);
}