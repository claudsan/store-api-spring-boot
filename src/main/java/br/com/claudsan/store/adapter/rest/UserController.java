package br.com.claudsan.store.adapter.rest;

import br.com.claudsan.store.adapter.dto.UserRequestDTO;
import br.com.claudsan.store.adapter.dto.UserViewDTO;
import br.com.claudsan.store.adapter.utils.ValidateEmail;
import br.com.claudsan.store.application.domain.User;
import br.com.claudsan.store.application.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.NoSuchElementException;

@RestController
@Tag(name = "Users")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public UserViewDTO create(@RequestParam String name, @RequestParam @Valid @Email String email){
        ValidateEmail.valid(email);
        return userService.create(name, email);
    }

    @GetMapping("/{id}")
    public UserViewDTO view(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public UserViewDTO view(@RequestBody UserViewDTO user){
        ValidateEmail.valid(user.getEmail());
        return userService.update(user.getUserId(), user.getName(), user.getEmail());
    }

    @GetMapping(name = "/list", params = { "page", "size" })
    public Page<User> list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size) {

        Page<User> resultPage = userService.list(page, size);
        if(!resultPage.hasContent()){
            throw new NoSuchElementException("No itens in page request");
        }
        return resultPage;
    }
}
