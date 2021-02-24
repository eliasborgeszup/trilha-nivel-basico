package br.com.zup.primeiro.desafio.service;

import br.com.zup.primeiro.desafio.controller.request.cartItem.CreatedCartItemRequest;
import br.com.zup.primeiro.desafio.controller.request.cart.UpdateCartRequest;
import br.com.zup.primeiro.desafio.entity.Cart;
import br.com.zup.primeiro.desafio.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartItemService {
    public String create(Long idComic, CreatedCartItemRequest request);

    public CartItem findById(String idCart);

    public Page<CartItem> findAll(Pageable page);

    public String update(String idCart, UpdateCartRequest request);

    public void delete(String idCart);
}
