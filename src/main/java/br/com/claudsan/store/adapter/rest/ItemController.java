package br.com.claudsan.store.adapter.rest;

import br.com.claudsan.store.adapter.dto.ItemRequestDTO;
import br.com.claudsan.store.adapter.dto.ItemViewDTO;
import br.com.claudsan.store.application.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@Tag(name = "Itens")
@RequestMapping("/itens")
public class ItemController {


    @Autowired(required = true)
    private ItemService itemService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ItemViewDTO create(@RequestBody ItemRequestDTO item) {
        return itemService.create(item.getName(), item.getQuantity());
    }

    @GetMapping("/{id}")
    public ItemViewDTO view(@PathVariable Long id) {
        return itemService.getItem(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ItemViewDTO view(@RequestBody ItemViewDTO item) {
        itemService.update(item.getId(), item.getName(), item.getQuantity());
        return item;
    }

    @GetMapping(name = "/list", params = {"page", "size"})
    public Page<ItemViewDTO> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {

        Page<ItemViewDTO> resultPage = itemService.list(page, size);
        if (!resultPage.hasContent()) {
            throw new NoSuchElementException("No itens in page request");
        }
        return resultPage;
    }
}
