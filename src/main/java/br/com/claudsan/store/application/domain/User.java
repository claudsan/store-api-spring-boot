package br.com.claudsan.store.application.domain;

import br.com.claudsan.store.adapter.dto.UserViewDTO;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "store_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements BaseDomain{

    @Id
    @SequenceGenerator(name = "userEntitySeq", sequenceName = "SEQ_USER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userreq")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;


    @Override
    public UserViewDTO toDTO() {
        return UserViewDTO.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .build();
    }
}
