package br.com.zup.primeiro.desafio.entity;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	
    @Column(nullable = false)
	private String name;
	
    @Column(nullable = false)
	private Date birthDate;
    
    @Column(nullable = false, unique = true, length = 11)
	private String cpf;
    
    @Column(nullable = false)
	private String email;
    
    @Column(nullable = false, unique = true, length = 11)
	private String phone;
    
    @Column(nullable = false)
	private String adress;
}
