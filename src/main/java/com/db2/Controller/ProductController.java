package com.db2.Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.db2.Forms.FilterProducts;
import com.db2.Model.Product;
import com.db2.Repository.CategoriaRepository;
import com.db2.Repository.ProductRepository;


@Controller
public class ProductController {
    private static final String detaillsPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\details.txt";
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/categorias/{index}")
    public String verProductos(@PathVariable String index, Model model) {
        Integer cat = Integer.parseInt(index);
        List<Product> productos = productRepository.findByCategoriaAndStockGreaterThan(cat).get();       
        
        model.addAttribute("productos", productos);
        return "productos";
    }

    @GetMapping("/crearProducto")
    public String crearProductoForm(Model model) {
        model.addAttribute("producto", new Product());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "createProduct";
    }

    @PostMapping("/categorias/crearProducto")
    public String guardarProducto(@ModelAttribute Product producto) throws Exception{
        if(
            producto.getCategoria().equals("none") ||
            producto.getNombre().equals("") ||
            producto.getStock() < 0 ||
            producto.getValor() < 0) 
        {
            return "redirect:/error";
        }
        productRepository.save(producto);
        return "redirect:/";
    }

    @GetMapping("/editarProducto")
    public String editarProducto(Model model) {
        model.addAttribute("busqueda", new FilterProducts());
        model.addAttribute("category", categoriaRepository.findAll());
        return "edicionproductos";
    }

    @PostMapping("/editProduct")
    public String verProducto(@ModelAttribute FilterProducts busqueda, Model model) {
        Optional<Product> producto = productRepository.findById(busqueda.getId());
        Optional<String> categoria = categoriaRepository.getCategoriaById(producto.get().getCategoria().longValue());
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            if (categoria.isPresent()) {
                
                model.addAttribute("categoria", categoria.get());
            }
            else {
                model.addAttribute("categoria", "Ninguna Categoria");
            }
            return "producto";
        }
        return "redirect:/error";
    }

    @PostMapping("/editStock")
    public String editStock(@ModelAttribute Product producto) {
        System.out.println(producto.getId());
        if (productRepository.existsById(producto.getId())) {
            productRepository.save(producto);
            return "redirect:/";
        }

        return "redirect:/error";
    }

    @PostMapping("/addItem/{id}")
    private String additem(@PathVariable Integer id) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(detaillsPath, true))) {
            writer.write("" + id);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while appending the text: " + e.getMessage());
        }

        return "redirect:/";
    }
}
