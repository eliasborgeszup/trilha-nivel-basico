package br.com.zup.primeiro.desafio.controller.response.cartItem;

import br.com.zup.primeiro.desafio.entity.CartItem;
import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CartItemResponse {
    private final String id;
    private final Long externalId;
    private final String title;
    private final String url;
    private final Integer quantity;

    public static CartItemResponse fromCartItem(CartItem cartItem) {
        return new CartItemResponse(cartItem.getId(), cartItem.getExternalId(),
                cartItem.getTitle(), cartItem.getUrl(), cartItem.getQuantity());
    }

}
