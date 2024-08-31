package com.example.foodOrder.repository;

import com.example.foodOrder.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
