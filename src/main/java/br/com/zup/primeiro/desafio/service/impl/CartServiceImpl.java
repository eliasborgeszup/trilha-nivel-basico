package br.com.zup.primeiro.desafio.service.impl;

import br.com.zup.primeiro.desafio.controller.request.cart.UpdateCartRequest;
import br.com.zup.primeiro.desafio.entity.Cart;
import br.com.zup.primeiro.desafio.exceptions.NotFoundException;
import br.com.zup.primeiro.desafio.repository.CartRepository;
import br.com.zup.primeiro.desafio.service.CartService;
import br.com.zup.primeiro.desafio.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@AllArgsConstructor
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository repository;

    private final CustomerService customerService;

    public String create(String customerId) {
        return repository.findByCustomerId(customerId)
                .map(Cart::getId)
                .orElseGet(() -> Cart.create(repository, customerService.findById(customerId)));
    }

    public Cart findById(String id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error("Cart not found, idCart = {} not found", id);

            throw new NotFoundException(format("CartServiceImpl: findById, idCart = {} not found",
                    id));
        });
    }

    public Cart findByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId).orElseThrow(() -> {
            log.error("Cart not found, id customer = {} not found", customerId);

            throw new NotFoundException(format("CartServiceImpl: findByCustomerId, id customer = {} not found",
                    customerId));
        });
    }

    public Page<Cart> findAll(Pageable page) {
        return repository.findAll(page);
    }

    public String update(String idCart, UpdateCartRequest request) {
        return null;
    }

    public void delete(String idCart) {
    }
}
