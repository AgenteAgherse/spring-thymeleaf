package com.db2.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.db2.Model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long>{
    
    @Query("SELECT u.total FROM Bill u WHERE u.id = :id")
    public Optional<Integer> getPrice(@Param("id") Long id);
}
