package br.com.claudsan.store.adapter.repository;

import br.com.claudsan.store.application.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
    Page<Item> findAll(Pageable pageable);
}