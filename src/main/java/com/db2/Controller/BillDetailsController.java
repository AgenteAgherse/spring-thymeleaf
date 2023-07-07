package com.db2.Controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.db2.Forms.DetailBuy;
import com.db2.Forms.findUser;
import com.db2.Model.BillDetails;
import com.db2.Model.Product;
import com.db2.Repository.BillDetailsRepository;
import com.db2.Repository.BillRepository;
import com.db2.Repository.ProductRepository;

@Controller
public class BillDetailsController {
    
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailsRepository billDetailsRepository;

    @Autowired ProductRepository productRepository;


    @PostMapping(path="/buscarFacturas")
    public String buscarFactura(@ModelAttribute findUser findU) {
        return "redirect:/verFacturas/" + findU.getId();
    }

    /**
     * Se obtiene la lista de facturas de la persona.
     * @param id
     * @param model
     * @return
     */
    ///verFacturas/' + ${id}
    @GetMapping(path = "/verFacturas/{idClient}")
    public String billDetailPage(@PathVariable Long idClient, Model model) {
        model.addAttribute("titulo", idClient + "- Facturas");
        if (idClient == null) return "redirect:/";

        model.addAttribute("facturas", billRepository.getByClient(idClient).get());
        model.addAttribute("idClient", idClient);
        return "facturas"; 
    }

    //'/verFacturas' + ${id} + '/' + ${factura.id} 
    @GetMapping(path = "/factura/{facturaId}")
    public String billInfoPage(@PathVariable Long facturaId, Model model) {
        List<BillDetails> detalles = billDetailsRepository.getDetails(facturaId).get();
        if (detalles == null) return "redirect:/";

        List<DetailBuy> productos = new ArrayList<>();
        for (BillDetails det: detalles) {
            Product product = productRepository.findById(det.getIdProducto()).get();
            DetailBuy detailbuy=new DetailBuy(product.getNombre(), det.getCantidad(), det.getTotal());
            productos.add(detailbuy);
        }
        model.addAttribute("detailBuy", productos);
        return "factura";
    }
}
