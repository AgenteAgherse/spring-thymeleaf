package com.db2.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "detallefactura")
@Setter
@Getter
public class BillDetails {

    public BillDetails(Long idProducto, Long idFactura, Integer cantidad, Double total) {
        this.idProducto = idProducto;
        this.idFactura = idFactura;
        this.cantidad = cantidad;
        this.total = total;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id;

    @Column(name = "idproducto")
    private Long idProducto;

    @Column(name = "idfactura")
    private Long idFactura;
    private Integer cantidad;
    private Double total;

}
