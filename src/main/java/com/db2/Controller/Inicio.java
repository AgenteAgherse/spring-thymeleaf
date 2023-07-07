package com.db2.Controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.db2.Forms.FilterProducts;
import com.db2.Forms.findUser;
import com.db2.Model.Product;
import com.db2.Repository.CategoriaRepository;
import com.db2.Repository.ProductRepository;

@Controller
@RequestMapping("/")
public class Inicio {
    

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping(value = "/")
    public String verInicio(Model model) {
        model.addAttribute("filtro", new FilterProducts());
        model.addAttribute("productos", productRepository.findWithStock().get());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("noDisponible", productRepository.findByNoStock().get());
        model.addAttribute("findU", new findUser());
        return "index";
    }

    @PostMapping(value = "/busqueda")
    public String verFiltrado(@ModelAttribute("filtro") FilterProducts filtro, Model model) {

        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("noDisponible", productRepository.findByNoStock().get());
        model.addAttribute("findU", new findUser());
        Optional<List<Product>> productosFiltrados;
        String nombre = filtro.getNombre();
        if (filtro.getCategoria() == 0) {
            productosFiltrados = productRepository.findByNombre(nombre);
            model.addAttribute("productos", productosFiltrados.get());
        }
        else if (nombre.equals("") || nombre == null) {
            productosFiltrados = productRepository.findByCategoria(filtro.getCategoria());
            model.addAttribute("productos", productosFiltrados.get());
        }
        else {
            productosFiltrados = productRepository.findByNombreAndCategoria(nombre, filtro.getCategoria());
            model.addAttribute("productos", productosFiltrados.get());
        }
        return "index";
    }


    @GetMapping("/error")
    public String getError() {
        return "error";
    }
}
