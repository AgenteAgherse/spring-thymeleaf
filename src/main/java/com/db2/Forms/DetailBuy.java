package com.db2.Forms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailBuy {
    private String name;
    private Integer quantity;
    private Double price;
}
