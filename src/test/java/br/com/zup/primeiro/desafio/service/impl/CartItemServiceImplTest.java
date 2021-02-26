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
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
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

    @Test(expected = NotFoundException.class)
    public void shouldNotCreateWhenMarvelNotFound() {
        //Given
        when(marvelService.findById(any())).thenThrow(NotFoundException.class);

        //When
        marvelService.findById(82965L);
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotCreateWhenCartNotFound() {
        //Given
        when(cartService.findById(any())).thenThrow(NotFoundException.class);

        //When
        cartService.findById("aebc5a38-3f9e-4a11-80a1-d86f5693c643");
    }

    @Test
    public void findByIdAndReturnCartItem() {
        // Given
        CartItem cartItem = buildCartItem();
        when(repository.findById(buildId())).thenReturn(Optional.of(cartItem));

        // When
        CartItem cardItemBD = service.findById(buildId());

        // Then
        assertNotNull(cardItemBD);
        assertEquals(cartItem, cardItemBD);
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotFindByIdWhenNotExists() {
        // Given
        when(repository.findById(buildId())).thenReturn(Optional.empty());

        // When
        service.findById(buildId());
    }

    @Test
    public void findAllCartItensAndReturnList() {
        // Given
        Pageable page = PageRequest.of(0, 10, Sort.by("quantity").ascending());

        Page<CartItem> cartItems = new PageImpl<CartItem>(buildListCartItens());
        when(repository.findAll(page)).thenReturn(cartItems);

        // When
        Page<CartItem> cartItemsBD = service.findAll(page);

        // Then
        assertNotNull(cartItemsBD);
        assertFalse(cartItemsBD.isEmpty());
        assertEquals(cartItems, cartItemsBD);
    }

/*
    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }*/

    private String buildId() {
        return "8f8a0875-f5ef-4301-9da7-70a170445013";
    }

    private CartItem buildCartItem() {
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

    private CreateCartItemRequest buildCreateCartItemRequest() {
        return new CreateCartItemRequest(100, "0e23df59-b4b3-4b05-8309-41b7e66aac73");
    }

    private List<CartItem> buildListCartItens() {
        List<CartItem> cartItemList = new ArrayList<>();

        cartItemList.add(buildCartItem());
        cartItemList.add(buildCartItem());

        return cartItemList;
    }
}