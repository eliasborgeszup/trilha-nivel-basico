package br.com.zup.primeiro.desafio.entity;

import br.com.zup.primeiro.desafio.enums.Status;
import br.com.zup.primeiro.desafio.repository.CartRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cart {
    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartItem> cartItemList;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private Cart(String id, Customer customer, Status status) {
        this.id = id;
        this.customer = customer;
        this.status = status;
    }

    public static String create(CartRepository repository, Customer customer) {
        return repository.save(new Cart(UUID.randomUUID().toString(),
                customer,
                Status.OPEN
        )).id;
    }
}
