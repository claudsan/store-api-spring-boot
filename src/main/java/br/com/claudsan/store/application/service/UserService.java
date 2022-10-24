package br.com.claudsan.store.application.service;

import br.com.claudsan.store.adapter.dto.UserViewDTO;
import br.com.claudsan.store.adapter.metrics.CustomMetrics;
import br.com.claudsan.store.application.domain.User;
import br.com.claudsan.store.adapter.repository.UserRepository;
import io.micrometer.core.annotation.Counted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserService {

    private static final String METRIC_NAME = "store.user";
    @Autowired
    private UserRepository repository;

    @Autowired
    private CustomMetrics metrics;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Counted("store.new_users")
    @Transactional
    public UserViewDTO create(String name, String email) {
        User newUser = repository.save(User.builder().name(name).email(email).build());
        logger.info(String.format("User created %s", newUser));
        metrics.counter(METRIC_NAME,"created",newUser.getUserId().toString());
        return transformToView(newUser);
    }

    @Transactional
    public UserViewDTO update(Long id, String name, String email) {
        User user = repository.save(User.builder().userId(id).name(name).email(email).build());
        logger.info(String.format("User updated %s", user));
        metrics.counter(METRIC_NAME,"updated",user.getUserId().toString());
        return transformToView(user);
    }

    public UserViewDTO getUser(Long userId) {
        User user = repository.findById(userId).get();
        return transformToView(user);
    }

    private UserViewDTO transformToView(User user){
        return UserViewDTO.builder().userId(user.getUserId()).name(user.getName()).email(user.getEmail()).build();
    }

    public Page<User> list(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return repository.findAll(paging);
    }
}
