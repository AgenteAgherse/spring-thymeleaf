package com.db2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;
import com.db2.Model.BillDetails;

public interface BillDetailsRepository extends JpaRepository<BillDetails, Long>{
    
    @Query("SELECT u FROM BillDetails u WHERE u.idFactura = :factura")
    public Optional<List<BillDetails>> getDetails(@Param("factura") Long factura);

}
