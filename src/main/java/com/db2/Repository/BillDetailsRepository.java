package com.db2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db2.Model.BillDetails;

public interface BillDetailsRepository extends JpaRepository<BillDetails, Long>{
    
}
