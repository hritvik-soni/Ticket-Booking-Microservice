package com.learning.busservice.repository;

import com.learning.busservice.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBusRepo extends JpaRepository<Bus,Integer> {
    Bus findByBusNumber(String busNumber);

}
