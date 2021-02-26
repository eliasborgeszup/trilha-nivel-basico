package br.com.zup.primeiro.desafio.entity;

import br.com.zup.primeiro.desafio.controller.request.cartItem.CreateCartItemRequest;
import br.com.zup.primeiro.desafio.controller.request.cartItem.UpdateCartItemRequest;
import br.com.zup.primeiro.desafio.controller.response.marvel.ResultsResponse;
import br.com.zup.primeiro.desafio.repository.CartItemRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
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
    @JoinColumn(name = "cart_id", foreignKey = @ForeignKey(name = "FK_ID_CART"))
    @JsonBackReference
    @ToString.Exclude
    private Cart cart;

    public static String create(CartItemRepository repository, CreateCartItemRequest request, ResultsResponse resultsComicsResponse, Cart cart) {
        return repository.save(new CartItem(UUID.randomUUID().toString(),
                resultsComicsResponse.getId(),
                resultsComicsResponse.getTitle(),
                resultsComicsResponse.getResourceURI(),
                request.getQuantity(),
                cart)).id;
    }

    public static void delete(CartItem cartItem, CartItemRepository repository) {
        repository.delete(cartItem);
    }

    public String update(UpdateCartItemRequest request, CartItemRepository repository) {
        this.quantity = request.getQuantity();

        return repository.save(this).id;
    }
}
