package it.nextworks.nfvmano.nssmf.service.factory;

import it.nextworks.nfvmano.libs.vs.common.utils.AppContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.Driver;

@Service
public class DriverFactory {
    private static final Logger log = LoggerFactory.getLogger(Driver.class);
    @Autowired
    private AppContextService appContextService;

    public DriverFactory() {
    }

    public Object getDriver(String driverName) throws NoSuchBeanDefinitionException {
        ApplicationContext applicationContext = this.appContextService.getAppContext();
        return applicationContext.getBean(driverName);
    }
}
