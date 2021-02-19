package br.com.zup.primeiro.desafio.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "idCart" })
@ToString
public class Cart {
    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String idCart;

    @Column(updatable = false, unique = true, nullable = false)
    private String idMarvel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Integer quantity;


}
