package br.com.zup.primeiro.desafio.entity;

import br.com.zup.primeiro.desafio.controller.request.cartItem.CreatedCartItemRequest;
import br.com.zup.primeiro.desafio.controller.response.marvel.ResultsResponse;
import br.com.zup.primeiro.desafio.enums.Status;
import br.com.zup.primeiro.desafio.repository.CartItemRepository;
import br.com.zup.primeiro.desafio.repository.CartRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
public class CartItem {
    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String id;

    @Column(nullable = true)
    private Long externalId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    public String create(CartItemRepository repository, CreatedCartItemRequest request, ResultsResponse resultsComicsResponse, Cart cart) {
        this.id = UUID.randomUUID().toString();
        this.externalId = resultsComicsResponse.getId();
        this.title = resultsComicsResponse.getTitle();
        this.url = resultsComicsResponse.getResourceURI();
        this.quantity = request.getQuantity();
        this.cart = cart;

        return repository.save(this).id;
    }
}
