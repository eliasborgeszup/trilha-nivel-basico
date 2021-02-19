package br.com.zup.primeiro.desafio.controller;


import br.com.zup.primeiro.desafio.config.PageSizeValidator;
import br.com.zup.primeiro.desafio.controller.request.cart.UpdateCartRequest;
import br.com.zup.primeiro.desafio.controller.response.cart.CartResponse;
import br.com.zup.primeiro.desafio.entity.Cart;
import br.com.zup.primeiro.desafio.service.CartService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/carts")
public class CartController {
    private static final Integer SIZE_MAX_PAGE = 100;

    private final CartService service;

    @ResponseStatus(CREATED)
    @PostMapping
    public String created(String idComics) {
        return service.create(idComics);
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/{idCart}")
    public Cart findById(@PathVariable String idCart) {
        return service.findById(idCart);
    }

    @ResponseStatus(OK)
    @GetMapping
    public Page<CartResponse> findAll(
            @PageableDefault(sort = "name", direction = ASC, page = 0, size = 20) Pageable page) {

        PageSizeValidator.validate(SIZE_MAX_PAGE, page.getPageSize());

        return service.findAll(page).map(CartResponse::fromCart);
    }

    @ResponseStatus(OK)
    @PutMapping(value = "/{idCart}")
    public String update(@PathVariable String idCart, @Valid @RequestBody UpdateCartRequest updateCartRequest) {
        return service.update(idCart, updateCartRequest);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{idCart}")
    public void delete(@PathVariable String idCart) {
        service.delete(idCart);
    }
}
