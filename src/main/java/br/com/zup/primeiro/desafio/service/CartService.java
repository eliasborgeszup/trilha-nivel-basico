package br.com.zup.primeiro.desafio.service;

import br.com.zup.primeiro.desafio.controller.request.cartItem.CreatedCartItemRequest;
import br.com.zup.primeiro.desafio.controller.request.cart.UpdateCartRequest;
import br.com.zup.primeiro.desafio.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {
    String create(String customerId);

    Cart findById(String idCart);

    Cart findByCustomerId(String customerId);

    Page<Cart> findAll(Pageable page);

    String update(String idCart, UpdateCartRequest request);

    void delete(String idCart);
}
