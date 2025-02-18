package com.lorenzozagallo.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// JPA - Java Persistence API
@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

}

// Explicação:
// @Entity e @Table: Define a persistência e o nome da tabela.
// @Id e @GeneratedValue: Define a chave primária.
// @Enumerated: Mapeia enums para o banco de dados.
// @ManyToOne, @OneToMany, @OneToOne: Define os relacionamentos.
// @EmbeddedId: Usado para chaves compostas.
// @JoinColumn: Define as chaves estrangeiras.
// @MapsId: Herda o ID de outra entidade.
