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
        Map<String, Object> ranCoreContraint2 = new HashMap<>();
        ranCoreContraint.put("bandIncDir",  "DL");
        ranCoreContraint.put("bandIncVal",  "10");
        ranCoreContraint.put("bandUnitScale",  "MB");

        ranCoreContraint2.put("bandIncDir",  "UL");
        ranCoreContraint2.put("bandIncVal",  "10");
        ranCoreContraint2.put("bandUnitScale",  "MB");
        List<Map<String, Object>> ranArray = new ArrayList<>();
        ranArray.add(ranCoreContraint);

        ranArray.add(ranCoreContraint2);
        qosContraint.put("ran_core_constraints", ranArray);
        JSONObject json = new JSONObject(qosContraint);
        System.out.println(json);
        String url = "http://10.8.202.11:8080";
        qos.setTargetUrl(url);
        //System.out.println(qos.setQoS(UUID.fromString("bf5cbb04-345c-479f-8511-f6f01b2b822d"), json));
        System.out.println(qos.setQoS(UUID.fromString("bf5cbb04-345c-479f-8511-f6f01b2b822d"), json));
    }

    @Test
    public void redirect() throws Exception{
        // { "FromServer" : "10.8.105.16", "ToServer" : "10.8.105.17" }
        Map<String, String> qosRedirectParameters = new HashMap<>();
        Map<String, Object> qosRedirectParametersParent = new HashMap<>();
        qosRedirectParameters.put("FromServer", "192.168.100.6");
        qosRedirectParameters.put("ToServer", "192.168.100.7");
        qosRedirectParametersParent.put("routes", qosRedirectParameters);
        JSONObject json = new JSONObject(qosRedirectParametersParent);
        System.out.println(json);
        String url = "http://10.8.202.11:8080"; // CPSR
        qos.setTargetUrl(url);
        System.out.println(qos.redirectTraffic(UUID.fromString("f5b01594-520e-11e9-8647-d663bd873d93"),
                "CORE", "208920100001103", json));
    }
}
