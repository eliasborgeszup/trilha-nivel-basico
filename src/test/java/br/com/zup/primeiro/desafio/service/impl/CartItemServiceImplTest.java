package br.com.zup.primeiro.desafio.service.impl;

import br.com.zup.primeiro.desafio.controller.request.cartItem.CreateCartItemRequest;
import br.com.zup.primeiro.desafio.controller.response.marvel.ComicsResponse;
import br.com.zup.primeiro.desafio.entity.Cart;
import br.com.zup.primeiro.desafio.entity.CartItem;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.enums.Status;
import br.com.zup.primeiro.desafio.exceptions.NotFoundException;
import br.com.zup.primeiro.desafio.repository.CartItemRepository;
import br.com.zup.primeiro.desafio.service.CartItemService;
import br.com.zup.primeiro.desafio.service.CartService;
import br.com.zup.primeiro.desafio.service.MarvelComicsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartItemServiceImplTest {

    @Mock
    CartItemRepository repository;

    @Mock
    MarvelComicsService marvelService;

    @Mock
    CartService cartService;

    @InjectMocks
    CartItemServiceImpl service;

    @Test
    public void shouldCreateAndReturnId() {
        //Given
        CreateCartItemRequest request = buildCreateCartItemRequest();
        CartItem cartItem = buildCartItem();

        when(marvelService.findById(any())).thenReturn(MarvelComicsServiceImplTest.buildComicsResponse());
        when(cartService.findById(any())).thenReturn(CartServiceImplTest.buildCart());
        when(repository.save(any())).thenReturn(cartItem);

        //Then
        String id = service.create(82965L, request);

        //When
        assertEquals(id, cartItem.getId());
    }

/*    @Test(expected = NotFoundException.class)
    public void shouldNotCreateWhenCartNotFound() {
        //Given
        CreateCartItemRequest request = buildCreateCartItemRequest();
        CartItem cartItem = buildCartItem();

        when(marvelService.findById(any())).thenReturn(MarvelComicsServiceImplTest.buildComicsResponse());
        when(cartService.findById(any())).thenReturn(null);
        when(repository.save(any())).thenReturn(cartItem);

        //When
        cartService.findById("82965L");
    }*/

    @Test
    public void findById() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    private CartItem buildCartItem(){
        return new CartItem("0e23df219-b4b3-4b05-8309-41b7e66aac73",
                82965L,
                "Marvel Previews (2017)",
                "http://gateway.marvel.com/v1/public/comics/82965",
                1,
                buildCart());
    }

    private Cart buildCart() {
        return new Cart("aebc5a38-3f9e-4a11-80a1-d86f5693c643",
                CustomerServiceImplTest.buildCustomer(),
                Status.OPEN);
    }

    private CreateCartItemRequest buildCreateCartItemRequest(){
        return new CreateCartItemRequest(100, "0e23df59-b4b3-4b05-8309-41b7e66aac73");
    }

}