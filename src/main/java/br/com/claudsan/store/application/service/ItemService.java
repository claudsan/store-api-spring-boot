package br.com.claudsan.store.application.service;

import br.com.claudsan.store.adapter.dto.ItemViewDTO;
import br.com.claudsan.store.adapter.metrics.CustomMetrics;
import br.com.claudsan.store.adapter.repository.ItemRepository;
import br.com.claudsan.store.application.domain.Item;
import br.com.claudsan.store.application.domain.StockMovement;
import br.com.claudsan.store.application.domain.TypeMovementStock;
import br.com.claudsan.store.application.events.StoreEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

@Component
public class ItemService {
    private static final String METRIC_NAME = "store.item";
    @Autowired
    private ItemRepository repository;
    @Autowired
    private StoreEventPublisher publisher;

    @Autowired
    private CustomMetrics metrics;
    Logger logger = LoggerFactory.getLogger(ItemService.class);
    @Transactional
    public ItemViewDTO create(String name, long quantity) {
        Item newItem = repository.save(Item.builder().name(name).quantityAvaiable(quantity).build());
        logger.info(String.format("item created %s", newItem));
        metrics.counter(METRIC_NAME,"type","new","item",newItem.toString());
        return ItemViewDTO.builder().id(newItem.getItemId()).name(newItem.getName()).quantity(quantity).build();
    }

    @Transactional
    public ItemViewDTO update(Long id, String name, long quantity) {
        Long oldQuantity = repository.findById(id).get().getQuantityAvaiable();
        Item item = repository.save(Item.builder().itemId(id).name(name).quantityAvaiable(quantity).build());

        logger.info(String.format("item updated %s", item));
        repository.save(item);

        if(oldQuantity == 0 && item.getQuantityAvaiable() > 0){
            createInStockMovement(item);
        }
        metrics.counter(METRIC_NAME,"type","update","item",item.toString());
        return ItemViewDTO.builder().id(item.getItemId()).build();
    }

    @Transactional
    public Item update(Item item) {
        repository.save(item);
        logger.info(String.format("item updated by stock movement %s", item));
        metrics.counter(METRIC_NAME,"type","update","item",item.toString());
        return item;
    }

    public ItemViewDTO getItem(Long itemId) {
        Item item = repository.findById(itemId).get();
        return ItemViewDTO.builder().id(item.getItemId()).name(item.getName()).quantity(item.getQuantityAvaiable()).build();
    }

    public Page<ItemViewDTO> list(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return toPageItemView(repository.findAll(paging));
    }

    public Item findById(Long itemId) {
        return repository.findById(itemId).get();
    }

    private void createInStockMovement(Item item) {
        StockMovement stock = StockMovement.builder()
                .item(item)
                .creationDate(new Date())
                .quantity(item.getQuantityAvaiable())
                .typeMovement(TypeMovementStock.IN)
                .build();
        publisher.fireEvent(stock);
        logger.info(String.format("Stock in event published for item: %s", item));
    }

    public Page<ItemViewDTO> toPageItemView(Page<Item> page) {
        return page.map((item)-> ItemViewDTO.builder()
                .id(item.getItemId())
                .name(item.getName())
                .quantity(item.getQuantityAvaiable()).build());
    }
}
