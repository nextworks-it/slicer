package it.nextworks.nfvmano.test;


import it.nextworks.nfvmano.test.tests.SlicerE2ETest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ServiceStartup {
    @PostConstruct
    public void configComService() {
        //Once start, it executes tests
        SlicerE2ETest slicerE2ETest = new SlicerE2ETest();
        slicerE2ETest.onBoardVSBWithNstID();;
    }
}
