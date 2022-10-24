package br.com.claudsan.store.adapter.rest;

import br.com.claudsan.store.adapter.dto.StockMovementViewDTO;
import br.com.claudsan.store.adapter.dto.StockMovementRequestDTO;
import br.com.claudsan.store.application.service.StockMovmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@Tag(name = "StockMovement")
@RequestMapping("/stock-movement")
public class StockMovementController {


    @Autowired
    private StockMovmentService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody StockMovementRequestDTO stock) {
        service.create(stock);
    }

    @GetMapping("/{id}")
    public StockMovementViewDTO view(@PathVariable Long id) {
        return service.getStockMovement(id);
    }

    @GetMapping(name = "/list", params = {"page", "size"})
    public Page<StockMovementViewDTO> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {

        Page<StockMovementViewDTO> resultPage = service.list(page, size);
        if (!resultPage.hasContent()) {
            throw new NoSuchElementException("No itens in page request");
        }
        return resultPage;
    }
}
