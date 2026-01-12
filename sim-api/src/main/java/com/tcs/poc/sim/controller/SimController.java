package com.tcs.poc.sim.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.tcs.poc.sim.service.SimService;
import com.tcs.poc.sim.dto.SimResponse;

@RestController
@RequestMapping("/sim")
public class SimController {
 @Autowired SimService service;

 @PostMapping("/action/{iccid}/{action}")
 public SimResponse act(@PathVariable String iccid,@PathVariable String action){
  return service.process(iccid, action);
 }
}
