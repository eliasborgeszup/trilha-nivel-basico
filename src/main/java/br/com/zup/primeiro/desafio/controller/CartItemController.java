package br.com.zup.primeiro.desafio.controller;


import br.com.zup.primeiro.desafio.controller.request.cartItem.CreatedCartItemRequest;
import br.com.zup.primeiro.desafio.controller.request.cart.UpdateCartRequest;
import br.com.zup.primeiro.desafio.controller.response.cartItem.CartItemIDResponse;
import br.com.zup.primeiro.desafio.controller.response.cartItem.CartItemResponse;
import br.com.zup.primeiro.desafio.entity.CartItem;
import br.com.zup.primeiro.desafio.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cartItens")
public class CartItemController {
    private static final Integer SIZE_MAX_PAGE = 100;

    private final CartItemService service;

    @ResponseStatus(CREATED)
    @PostMapping(value = "/{idComics}")
    public CartItemIDResponse created(@PathVariable Long idComics, @Valid @RequestBody CreatedCartItemRequest request) {
        return new CartItemIDResponse(service.create(idComics, request));
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/{idCart}")
    public CartItemResponse findById(@PathVariable String idCart) {
        return CartItemResponse.fromCartItem(service.findById(idCart));
    }

/*    @ResponseStatus(OK)
    @GetMapping
    public Page<CartItem> findAll(
            @PageableDefault(sort = "name", direction = ASC, page = 0, size = 20) Pageable page) {

        PageSizeValidator.validate(SIZE_MAX_PAGE, page.getPageSize());

        return service.findAll(page).map(CartItem::fromCart);
    }*/

    @ResponseStatus(OK)
    @PutMapping(value = "/{idCart}")
    public String update(@PathVariable String idCart, @Valid @RequestBody UpdateCartRequest request) {
        return service.update(idCart, request);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{idCart}")
    public void delete(@PathVariable String idCart) {
        service.delete(idCart);
    }
}
