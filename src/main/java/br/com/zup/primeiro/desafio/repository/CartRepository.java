package br.com.zup.primeiro.desafio.repository;

import br.com.zup.primeiro.desafio.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Page<Cart> findAll(Pageable pageable);

    Optional<Cart> findByCustomerId(String id);
}
