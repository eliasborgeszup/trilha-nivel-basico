package br.com.zup.primeiro.desafio.repository;

import br.com.zup.primeiro.desafio.entity.Cart;
import br.com.zup.primeiro.desafio.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    Page<CartItem> findAll(Pageable pageable);
}
