package it.nextworks.nfvmano.sebastian.nsmf.test;

import it.nextworks.nfvmano.sebastian.nsmf.sbi.QoSService;
import org.json.JSONObject;
import org.junit.Test;

import java.util.*;

public class QoSServiceTest {
    QoSService qos = new QoSService();

    @Test
    public void createJson(){
        Map<String, Object> map = new HashMap<>();
        String[] value2 = new String[] { "value11", "value12", "value13" };
        Map<String, String> map2 = new HashMap<>();
        map2.put("la_chiave", "il_valore");
        map.put("key_1", "value1");
        map.put("key_2", value2);
        map.put("the_key", map2);
        JSONObject json = new JSONObject(map);
        System.out.println(json);
    }

    @Test
    public void setQos() throws Exception{
        Map<String, Object> qosContraint = new HashMap<>();
        Map<String, Object> ranCoreContraint = new HashMap<>();
        ranCoreContraint.put("bandIncDir",  "DL");
        ranCoreContraint.put("bandIncVal:",  10);
        ranCoreContraint.put("bandUnitScale",  "MB");
        List<Map<String, Object>> ranArray = new ArrayList<>();
        ranArray.add(ranCoreContraint);
        qosContraint.put("ran_core_constraints", ranArray);
        JSONObject json = new JSONObject(qosContraint);
        System.out.println(json);
        qos.setTargetUrl("http://10.30.8.76:8080");
        System.out.println(qos.setQoS(UUID.fromString("bf5cbb04-345c-479f-8511-f6f01b2b822d"), json));

    }
}
