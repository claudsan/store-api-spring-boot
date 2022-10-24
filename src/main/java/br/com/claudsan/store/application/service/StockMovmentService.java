package br.com.claudsan.store.application.service;

import br.com.claudsan.store.adapter.dto.StockMovementViewDTO;
import br.com.claudsan.store.adapter.dto.StockMovementRequestDTO;
import br.com.claudsan.store.adapter.metrics.CustomMetrics;
import br.com.claudsan.store.adapter.repository.ItemRepository;
import br.com.claudsan.store.adapter.repository.StockMovmentRepository;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class StockMovmentService {

    private static final String METRIC_NAME = "store.stock";

    @Autowired
    private CustomMetrics metrics;

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private StoreEventPublisher publisher;

    @Autowired
    private StockMovmentRepository repository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public void createByOrder(StockMovement stock) {
        repository.save(stock);
        if (stock.getTypeMovement() == TypeMovementStock.IN) {
            publisher.fireEvent(stock.getItem());
        }
        loggerMovement(stock);
    }

    @Transactional
    public void create(StockMovementRequestDTO stockMovmentRequestDTO) {
        StockMovement stock = StockMovement.builder()
                .item(Item.builder().itemId(stockMovmentRequestDTO.getItemId()).build())
                .quantity(stockMovmentRequestDTO.getQuantity())
                .creationDate(new Date())
                .typeMovement(stockMovmentRequestDTO.getTypeMovementStock())
                .build();

        Item fullItem = itemRepository.findById(stock.getItem().getItemId()).get();
        if(stockMovmentRequestDTO.getTypeMovementStock() == TypeMovementStock.OUT && fullItem.getQuantityAvaiable() == 0)
            throw new RuntimeException("Operation not performed, item has no available quantity.");

        if(stockMovmentRequestDTO.getTypeMovementStock() == TypeMovementStock.IN)
            fullItem.setQuantityAvaiable(fullItem.getQuantityAvaiable() + stockMovmentRequestDTO.getQuantity());
        else
            fullItem.setQuantityAvaiable(fullItem.getQuantityAvaiable() - stockMovmentRequestDTO.getQuantity());

        repository.save(stock);
        publisher.fireEvent(fullItem);

        loggerMovement(stock);
        metrics.counter(METRIC_NAME,"type","manual","stock",stockMovmentRequestDTO.toString());
    }

    public StockMovementViewDTO getStockMovement(Long id) {
        StockMovement stock = repository.findById(id).get();
        return stock.toDTO();
    }

    public Page<StockMovementViewDTO> list(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return toPageDTO(repository.findAll(paging));
    }

    private Page<StockMovementViewDTO> toPageDTO(Page<StockMovement> page) {
        return page.map((item)-> StockMovementViewDTO.builder()
                .item(item.getItem().toDTO())
                .quantity(item.getQuantity())
                .typeMovement(item.getTypeMovement())
                .orderId(item.getOrder().getOrderId())
                .build()
        );
    }
    private void loggerMovement(StockMovement stock){
        logger.info(String.format("StockMovement created: [itemId: %d, quantity: %d, type: %s]",
                stock.getItem().getItemId(), stock.getQuantity(), stock.getTypeMovement().name()));

    }
}
