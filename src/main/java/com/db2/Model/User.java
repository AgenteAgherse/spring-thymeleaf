package com.db2.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {

    @Id
    @Column(name = "identificacion")
    private Long id;
    
    @Column(name = "tipo_cedula")
    private String tipoCedula;

    private String nombre;
    private String apellido;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String correo;

}
