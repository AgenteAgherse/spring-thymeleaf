package com.db2.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Long id;

    @Column(name = "codigo_cat")
    private Integer categoria;

    private String nombre;

    @Column(name = "valor_unitario")
    private Double valor;
    
    @Column(name = "enStock")
    private Integer stock;

}
