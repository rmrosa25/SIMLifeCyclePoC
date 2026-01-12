ALTER SESSION SET CURRENT_SCHEMA = SIMAPP;

CREATE OR REPLACE PROCEDURE PROCESS_SIM_LIFECYCLE (
  p_iccid IN VARCHAR2,
  p_action IN VARCHAR2,
  p_result OUT VARCHAR2,
  p_message OUT VARCHAR2
) AS
  v_status VARCHAR2(20);
  v_usage NUMBER := 0;
BEGIN
  SELECT STATUS INTO v_status FROM IOT_SIM WHERE ICCID = p_iccid;

  IF p_action = 'ACTIVATE' THEN
    UPDATE IOT_SIM SET STATUS='ACTIVE' WHERE ICCID=p_iccid;
    p_result := 'SUCCESS';
    p_message := 'Activated';
  ELSIF p_action = 'SUSPEND' THEN
    SELECT NVL(SUM(USAGE_MB),0) INTO v_usage FROM SIM_USAGE WHERE ICCID=p_iccid;
    IF v_usage > 5000 THEN
      UPDATE IOT_SIM SET STATUS='SUSPENDED' WHERE ICCID=p_iccid;
      p_result := 'SUCCESS';
      p_message := 'Suspended due to usage';
    ELSE
      p_result := 'FAIL';
      p_message := 'Usage below threshold';
    END IF;
  ELSE
    p_result := 'FAIL';
    p_message := 'Unknown action';
  END IF;

  INSERT INTO SIM_AUDIT_LOG VALUES (SIM_AUDIT_SEQ.NEXTVAL,p_iccid,p_action,p_message,SYSDATE);
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    p_result := 'ERROR';
    p_message := SQLERRM;
END;
/
