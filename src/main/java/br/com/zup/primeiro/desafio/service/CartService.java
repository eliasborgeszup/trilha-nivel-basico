package br.com.zup.primeiro.desafio.service;

import br.com.zup.primeiro.desafio.controller.request.cart.UpdateCartRequest;
import br.com.zup.primeiro.desafio.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {
    public String create(String idComic);

    public Cart findById(String idCart);

    public Page<Cart> findAll(Pageable page);

    public String update(String idCart, UpdateCartRequest updateCartRequest);

    public void delete(String idCart);
}
