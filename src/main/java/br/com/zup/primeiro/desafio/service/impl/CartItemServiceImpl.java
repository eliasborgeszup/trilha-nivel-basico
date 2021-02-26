package br.com.zup.primeiro.desafio.service.impl;

import br.com.zup.primeiro.desafio.controller.request.cartItem.CreateCartItemRequest;
import br.com.zup.primeiro.desafio.controller.request.cartItem.UpdateCartItemRequest;
import br.com.zup.primeiro.desafio.entity.CartItem;
import br.com.zup.primeiro.desafio.exceptions.NotFoundException;
import br.com.zup.primeiro.desafio.repository.CartItemRepository;
import br.com.zup.primeiro.desafio.service.CartItemService;
import br.com.zup.primeiro.desafio.service.CartService;
import br.com.zup.primeiro.desafio.service.MarvelComicsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.*;

@AllArgsConstructor
@Slf4j
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository repository;

    private final CartService cartService;

    private final MarvelComicsService marvelService;

    public String create(Long idComic, CreateCartItemRequest request) {
        log.info("Create createdCartItemRequest = {}", request);

        return CartItem.create(repository,
                request,
                marvelService.findById(idComic).getData().getResults().stream().findFirst().orElseThrow(),
                cartService.findById(cartService.create(request.getCustomerId())));
    }

    public CartItem findById(String id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error("CartItem not found, idCartItem = {} not found", id);

            throw new NotFoundException(format("CartItemServiceImpl: findById, idCartItem = {} not found",
                    id));
        });
    }

    public Page<CartItem> findAll(Pageable page) {
        return repository.findAll(page);
    }

    public String update(String id, UpdateCartItemRequest request) {
        CartItem cartItem = repository.findById(id).orElseThrow(() -> {
            log.error("CartItem not update, id CardItem = {} not found", id);

            throw new NotFoundException(format("CartItemServiceImpl: update, id CardItem = %s not found", id));
        });

        log.info("Update CartItem, id = {}, customerBefore = {}, customerAfter = {}",
                id,
                request,
                cartItem);

        return cartItem.update(request, repository);
    }

    public void delete(String id) {
        CartItem.delete(repository.findById(id).orElseThrow(() -> {
            log.error("CartItem not delete, id CardItem = {} not found", id);

            throw new NotFoundException(format("CartItemServiceImpl: delete, idCardItem = %s not found", id));
        }), repository);

        log.info("Delete idCardItem = {}", id);
    }
}
