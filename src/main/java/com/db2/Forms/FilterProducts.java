package com.db2.Forms;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class FilterProducts {
    private Integer categoria;
    private String nombre;
    private Long id;

    public FilterProducts() {
        this.categoria = 0;
        this.nombre = "";
        this.id = 0L;
    }
}
