package br.com.claudsan.store.application.domain;

import br.com.claudsan.store.adapter.dto.ItemViewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@Builder
@Entity
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
public class Item implements BaseDomain{

    @Id
    @SequenceGenerator(name = "itemEntitySeq", sequenceName = "SEQ_ITEM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemreq")
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "quantity_avaiable", nullable = false)
    @Min(value = 0,message = "quantityAvaiable value must be greater than zero ")
    private Long quantityAvaiable;

    @Override
    public ItemViewDTO toDTO() {
        return ItemViewDTO.builder()
                .quantity(quantityAvaiable)
                .name(name)
                .id(itemId)
                .build();
    }
}
