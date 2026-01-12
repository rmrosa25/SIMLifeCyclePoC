package com.tcs.poc.sim.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.tcs.poc.sim.repository.SimRepository;
import com.tcs.poc.sim.dto.SimResponse;

@Service
public class SimService {
 @Autowired SimRepository repo;
 public SimResponse process(String iccid,String action){
  return repo.call(iccid, action);
 }
}
