package br.com.zup.primeiro.desafio.controller;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.data.domain.Sort.Direction.ASC;

import br.com.zup.primeiro.desafio.config.PageSizeValidator;
import br.com.zup.primeiro.desafio.controller.request.cartItem.CreateCartItemRequest;
import br.com.zup.primeiro.desafio.controller.request.cartItem.UpdateCartItemRequest;
import br.com.zup.primeiro.desafio.controller.response.cartItem.CartItemIDResponse;
import br.com.zup.primeiro.desafio.controller.response.cartItem.CartItemResponse;
import br.com.zup.primeiro.desafio.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cartItens")
public class CartItemController {
    private static final Integer SIZE_MAX_PAGE = 100;

    private final CartItemService service;

    @ResponseStatus(CREATED)
    @PostMapping(value = "/{idComics}")
    public CartItemIDResponse create(@PathVariable Long idComics, @Valid @RequestBody CreateCartItemRequest request) {
        return new CartItemIDResponse(service.create(idComics, request));
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/{id}")
    public CartItemResponse findById(@PathVariable String id) {
        return CartItemResponse.fromCartItem(service.findById(id));
    }

    @ResponseStatus(OK)
    @GetMapping
    public Page<CartItemResponse> findAll(
            @PageableDefault(sort = "quantity", direction = ASC, size = 20) Pageable page) {

        PageSizeValidator.validate("CartItemController", SIZE_MAX_PAGE, page.getPageSize());

        return service.findAll(page).map(CartItemResponse::fromCartItem);
    }

    @ResponseStatus(OK)
    @PutMapping(value = "/{id}")
    public CartItemIDResponse update(@PathVariable String id, @Valid @RequestBody UpdateCartItemRequest request) {
        return new CartItemIDResponse(service.update(id, request));
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
