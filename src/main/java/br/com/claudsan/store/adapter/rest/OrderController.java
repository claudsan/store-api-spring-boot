package br.com.claudsan.store.adapter.rest;


import br.com.claudsan.store.adapter.dto.ItemViewDTO;
import br.com.claudsan.store.adapter.dto.OrderRequestDTO;
import br.com.claudsan.store.adapter.dto.OrderUpdateDTO;
import br.com.claudsan.store.adapter.dto.OrderViewDTO;
import br.com.claudsan.store.application.events.StoreEventPublisher;
import br.com.claudsan.store.application.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Tag(name = "Order")
public class OrderController {

    @Autowired
    private StoreEventPublisher publisher;

    @Autowired
    private OrderService service;

    @GetMapping("/{id}")
    public OrderViewDTO order(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping(name = "/list", params = {"page", "size"})
    public Page<OrderViewDTO> order(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {
        return service.list(page, size);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public OrderUpdateDTO view(@RequestBody OrderUpdateDTO order) {
        service.update(order);
        return order;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderRequestDTO order) {
        publisher.fireEvent(order);
    }
}
