package com.db2.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.db2.Forms.DetailBuy;
import com.db2.Forms.DetailsCount;
import com.db2.Model.Bill;
import com.db2.Model.BillDetails;
import com.db2.Model.Product;
import com.db2.Model.User;
import com.db2.Repository.BillDetailsRepository;
import com.db2.Repository.BillRepository;
import com.db2.Repository.ProductRepository;
import com.db2.Repository.UserRepository;

@Controller
public class BillController {
    
    private static final String detaillsPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\details.txt";

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BillDetailsRepository billDetailsRepository;

    private ArrayList<DetailsCount> getAllCart() throws Exception{
        ArrayList<DetailsCount> cart = new ArrayList<>();
        DetailsCount details;
        try (BufferedReader reader = new BufferedReader(new FileReader(detaillsPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Long number = Long.parseLong(line);
                int index = foundIndex(cart, number);

                if (index == -1) {
                    Product producto = productRepository.findById(number).get();
                    details = new DetailsCount();
                    details.setId(number);
                    details.setName(producto.getNombre());
                    details.setVlrUnit(producto.getValor());
                    details.setCount(1);
                    cart.add(details);
                }
                //En caso que lo encuentre.
                else {
                    cart.get(index).setCount(cart.get(index).getCount() + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return cart;
    }


    private int foundIndex(ArrayList<DetailsCount> cart, Long findNumber) {
        for(int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId() == findNumber) {
                return i;
            }
        }
        return -1;
    }


    @GetMapping("/historialPagos")
    public String verHistorial(Model model) {
        ArrayList<DetailsCount> details = new ArrayList<>();
        try {
            details = getAllCart();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Integer id = 0;
        model.addAttribute("details", details);
        model.addAttribute("person", new User());
        model.addAttribute("id", id);

        return "carrito";
    }
    

    //Método para publicar al usuario
    @PostMapping("/historialPagos/realizarCompra")
    public String registerUserAndBuy(@ModelAttribute User person, Model model) throws Exception { 
        List<BillDetails> compra = new ArrayList<>();
        if (userRepository.existsById(person.getId())) compra = addBillAndDetails(person, 1);
        else compra = addBillAndDetails(person, 0);
        
        //Opción para crear
        List<DetailBuy> detalles = new ArrayList<>();
        for (BillDetails c: compra) {
            Product producto = productRepository.findById(c.getIdProducto()).get();
            detalles.add(new DetailBuy(producto.getNombre(), c.getCantidad(), (c.getTotal() / c.getCantidad()) ));
        }

        deleteFileContent();
        model.addAttribute("id", person.getId());
        model.addAttribute("details", detalles);
        return "comprarealizada";
    }

    
    @GetMapping("/CompraRealizada")
    public String terminado(Model model) {
        deleteFileContent();
        return "redirect:/";
    }


    private static void deleteFileContent() {
		String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\details.txt";
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("An error occurred while clearing the file content: " + e.getMessage());
        }
	}

    private List<BillDetails> addBillAndDetails(User person, int opcion) throws Exception {
        ArrayList<DetailsCount> details = getAllCart();
        userRepository.save(person);
        System.out.println(details.size());
        Bill nuevaFactura = new Bill();
        nuevaFactura.setIdCliente(person.getId());
        nuevaFactura.setTotal(0.0);
        nuevaFactura = billRepository.save(nuevaFactura);

        for (DetailsCount iterable : details) {
            billDetailsRepository.save(new BillDetails(iterable.getId(), nuevaFactura.getId(),iterable.getCount(), iterable.getVlrUnit()));
        }
        return billDetailsRepository.getDetails(nuevaFactura.getId()).get();
    }
}
