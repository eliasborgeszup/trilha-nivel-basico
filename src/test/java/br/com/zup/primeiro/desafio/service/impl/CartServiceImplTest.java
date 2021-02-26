package br.com.zup.primeiro.desafio.service.impl;

import br.com.zup.primeiro.desafio.entity.Cart;
import br.com.zup.primeiro.desafio.enums.Status;

import static org.junit.Assert.*;

public class CartServiceImplTest {

    public static Cart buildCart() {
        return new Cart("aebc5a38-3f9e-4a11-80a1-d86f5693c643",
                CustomerServiceImplTest.buildCustomer(),
                Status.OPEN);
    }
}