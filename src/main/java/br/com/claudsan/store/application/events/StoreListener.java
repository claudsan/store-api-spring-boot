package br.com.claudsan.store.application.events;

import br.com.claudsan.store.adapter.dto.OrderRequestDTO;
import br.com.claudsan.store.adapter.metrics.CustomMetrics;
import br.com.claudsan.store.application.domain.Item;
import br.com.claudsan.store.application.domain.StockMovement;
import br.com.claudsan.store.application.service.ItemService;
import br.com.claudsan.store.application.service.OrderService;
import br.com.claudsan.store.application.service.StockMovmentService;
import io.micrometer.core.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StoreListener implements ApplicationListener<StoreEvent> {
    private static final String METRIC_NAME = "store.events";
    private Logger logger = LoggerFactory.getLogger(StoreListener.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private StockMovmentService stockMovmentService;

    @Autowired
    private CustomMetrics metrics;
    @Override
    public void onApplicationEvent(@NonNull StoreEvent event) {
        switch (event.getMessage().getClass().getSimpleName()){
            case "OrderRequestDTO":
                logger.info(String.format("Event order received %s", event));
                orderService.createOrder((OrderRequestDTO) event.getMessage());
                break;
            case "StockMovmentRequestDTO":
                logger.info(String.format("Event movement received %s", event));
                stockMovmentService.createByOrder((StockMovement) event.getMessage());
                break;
            case "StockMovement":
                StockMovement stockMovement = (StockMovement) event.getMessage();
                logger.info(String.format("Event Item update for pending orders received %s", stockMovement));
                orderService.udpatePendingOrders(stockMovement);
                break;
            case "Item":
                Item item = (Item) event.getMessage();
                logger.info(String.format("Event for update item received %s", item));
                itemService.update(item.getItemId(), item.getName(), item.getQuantityAvaiable());
                break;
            default:
                logger.error(String.format("Event unknown received %s", event));
        }
        metrics.counter(METRIC_NAME,"type","received", "object", event.getMessage().getClass().getSimpleName());
    }

}
