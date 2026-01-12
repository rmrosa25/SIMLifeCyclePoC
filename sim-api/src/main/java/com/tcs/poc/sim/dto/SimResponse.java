package com.tcs.poc.sim.dto;
public class SimResponse {
 private String result;
 private String message;
 public SimResponse(String r,String m){this.result=r;this.message=m;}
 public String getResult(){return result;}
 public String getMessage(){return message;}
}
