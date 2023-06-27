package com.db2.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.db2.Model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    @Query("SELECT u.nombre FROM Categoria u WHERE u.id = :id")
    public Optional<String> getCategoriaById(@Param("id") Long id);
}
