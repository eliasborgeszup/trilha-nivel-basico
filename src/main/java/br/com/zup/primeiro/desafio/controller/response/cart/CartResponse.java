package br.com.zup.primeiro.desafio.controller.response.cart;

import br.com.zup.primeiro.desafio.entity.Cart;
import br.com.zup.primeiro.desafio.entity.Customer;
import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CartResponse {
    private String idCart;
    private Customer customer;
    private String idMarvel;
    private Integer quantity;
    private String title;
    private String url;

    public static CartResponse fromCart(Cart cart) {
        return new CartResponse(cart.getIdCart(), cart.getCustomer(), cart.getIdMarvel(),
                cart.getQuantity(), cart.getTitle(), cart.getUrl());
    }
}
