package br.com.zup.primeiro.desafio.controller;


import br.com.zup.primeiro.desafio.config.PageSizeValidator;
import br.com.zup.primeiro.desafio.controller.request.cart.UpdateCartRequest;
import br.com.zup.primeiro.desafio.controller.response.cartItem.CartItemResponse;
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

//    @ResponseStatus(CREATED)
//    @PostMapping(value = "/{idComics}")
//    public CartIDResponse created(@PathVariable Long idComics, @Valid @RequestBody CreatedCartItemRequest request) {
//        return new CartIDResponse(service.create(idComics, request));
//    }

    @ResponseStatus(OK)
    @GetMapping(value = "/{idCart}")
    public Cart findById(@PathVariable String idCart) {
        return service.findById(idCart);
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/customers/{customerId}")
    public Cart findByCustomerId(@PathVariable String customerId) { return service.findByCustomerId(customerId); }

/*    @ResponseStatus(OK)
    @GetMapping
    public Page<CartItemResponse> findAll(
            @PageableDefault(sort = "name", direction = ASC, page = 0, size = 20) Pageable page) {

        PageSizeValidator.validate(SIZE_MAX_PAGE, page.getPageSize());

        return service.findAll(page).map(CartResponse::fromCart);
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
