package br.com.zup.primeiro.desafio.service.impl;


import br.com.zup.primeiro.desafio.controller.request.cartItem.CreatedCartItemRequest;
import br.com.zup.primeiro.desafio.controller.request.cart.UpdateCartRequest;
import br.com.zup.primeiro.desafio.controller.response.marvel.ResultsResponse;
import br.com.zup.primeiro.desafio.entity.Cart;
import br.com.zup.primeiro.desafio.entity.CartItem;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.repository.CartItemRepository;
import br.com.zup.primeiro.desafio.repository.CartRepository;
import br.com.zup.primeiro.desafio.service.CartItemService;
import br.com.zup.primeiro.desafio.service.CartService;
import br.com.zup.primeiro.desafio.service.CustomerService;
import br.com.zup.primeiro.desafio.service.MarvelComicsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository repository;

    private final CartService cartService;

    private final MarvelComicsService marvelService;

    public String create(Long idComic, CreatedCartItemRequest request) {
        log.info("Create cartItem = {}", request);

        return new CartItem().create(repository,
                request,
                marvelService.findById(idComic).getData().getResults().stream().findFirst().orElseThrow(),
                cartService.findById(cartService.create(request.getCustomerId())));
    }

    public CartItem findById(String idCart) {
        return null;
    }

    public Page<CartItem> findAll(Pageable page) {
        return repository.findAll(page);
    }

    public String update(String idCart, UpdateCartRequest request) {
        return null;
    }

    public void delete(String idCart) {
    }

    private CartItem buildCardItem(Long idComic, CreatedCartItemRequest request) {


        return new CartItem();
    }
}
