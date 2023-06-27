package com.db2.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.db2.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    public Optional<List<Product>> findByNombre(String nombre);
    public Optional<List<Product>> findByCategoria(Integer categoria);
    public Optional<List<Product>> findByNombreAndCategoria(String nombre, Integer categoria);

    @Query("SELECT u FROM Product u WHERE u.stock > 0 AND u.categoria = :categoria AND u.nombre LIKE %:nombre%")
    public Optional<List<Product>> encontrarPorNombreParecido(@Param("categoria") Integer categoria, @Param("nombre") String nombre);
    
    @Query("SELECT u FROM Product u WHERE u.stock > 0 AND u.categoria = :categoria")
    public Optional<List<Product>> findByCategoriaAndStockGreaterThan(@Param("categoria") Integer categoria);
    
    @Query("SELECT u FROM Product u WHERE u.stock <= 0")
    public Optional<List<Product>> findByNoStock();

    @Query("SELECT u FROM Product u WHERE u.stock > 0")
    public Optional<List<Product>> findWithStock();

}
