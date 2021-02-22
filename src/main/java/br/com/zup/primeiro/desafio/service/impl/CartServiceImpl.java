package br.com.zup.primeiro.desafio.service.impl;


import br.com.zup.primeiro.desafio.controller.request.cart.UpdateCartRequest;
import br.com.zup.primeiro.desafio.entity.Cart;
import br.com.zup.primeiro.desafio.repository.CartRepository;
import br.com.zup.primeiro.desafio.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository repository;

    public String create(String idComic) {
        return null;
    }

    public Cart findById(String idCart) {
        return null;
    }

    public Page<Cart> findAll(Pageable page) {
        return repository.findAll(page);
    }

    @Override
    public String update(String idCart, UpdateCartRequest updateCartRequest) {
        return null;
    }

    public void delete(String idCart) {
    }
}
