package br.com.zup.primeiro.desafio.service;

import br.com.zup.primeiro.desafio.controller.request.cartItem.CreatedCartItemRequest;
import br.com.zup.primeiro.desafio.controller.request.cartItem.UpdateCartItemRequest;
import br.com.zup.primeiro.desafio.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartItemService {
    String create(Long idComic, CreatedCartItemRequest request);

    CartItem findById(String id);

    Page<CartItem> findAll(Pageable page);

    String update(String id, UpdateCartItemRequest request);

    void delete(String id);
}
