package br.com.claudsan.store.adapter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class UserViewDTO implements Serializable {
    private Long userId;
    private String name;
    private String email;
}
