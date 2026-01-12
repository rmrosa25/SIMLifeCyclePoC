package com.tcs.poc.sim.repository;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.tcs.poc.sim.dto.SimResponse;

@Repository
public class SimRepository {
 @Autowired JdbcTemplate jdbc;

 public SimResponse call(String iccid,String action){
  SimpleJdbcCall call = new SimpleJdbcCall(jdbc).withProcedureName("PROCESS_SIM_LIFECYCLE");
  Map<String,Object> out = call.execute(
   new MapSqlParameterSource().addValue("p_iccid",iccid).addValue("p_action",action)
  );
  return new SimResponse((String)out.get("p_result"),(String)out.get("p_message"));
 }
}
