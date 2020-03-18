package it.nextworks.nfvmano.sebastian.nsmf.nstadvertiser;

import it.nextworks.nfvmano.catalogues.template.repo.NsTemplateRepository;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.RemoteTenantInfo;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.nsmf.nbi.VsmfNstAdvertiserRestClient;
import it.nextworks.nfvmano.sebastian.nsmf.nstadvertiser.NstAdvertisingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
public class NstOnboardingInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(NstOnboardingInterceptor.class);

    @Autowired
    NsTemplateRepository nsTemplateRepository;

    @Autowired
    AdminService adminService;

    private String vsmfHostname;

    private VsmfNstAdvertiserRestClient vsmfNstAdvertiserRestClient;

    private NstAdvertisingManager nstAdvertisingManager;
    private BlockingQueue<Object> requests;

    public NstOnboardingInterceptor() {
        requests=new ArrayBlockingQueue<Object>(100);
    }

    public void setVsmfHostname(String vsmfHostname) {
        this.vsmfHostname = vsmfHostname;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    private String getTenantId(String hostname){
        List<Tenant> tenantList=adminService.getAllTenants();
        for(Tenant tenant: tenantList){
            for(RemoteTenantInfo remoteTenantInfo:tenant.getRemoteTenantInfos()){
                log.info(remoteTenantInfo.getRemoteTenantName());
                if(remoteTenantInfo.getHost().contains(hostname)){
                    return tenant.getUsername();
                }
            }
        }
        return null;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        final String NST_PATH = "ns/catalogue/nstemplate";
        String requestUrl = httpServletRequest.getRequestURL().toString();

        if (httpServletRequest.getMethod().equals("POST") &&
                requestUrl.indexOf(NST_PATH) != -1 &&
                httpServletResponse.getStatus() == HttpStatus.CREATED.value()) {
            if (vsmfNstAdvertiserRestClient == null) {
                log.info("Starting NST Advertiser.");
                String tenantId =  getTenantId(vsmfHostname);
                vsmfNstAdvertiserRestClient = new VsmfNstAdvertiserRestClient(vsmfHostname, adminService, tenantId);
                nstAdvertisingManager = new NstAdvertisingManager(vsmfNstAdvertiserRestClient,requests);
                Thread thread =new Thread(nstAdvertisingManager);
                thread.start();
            }
            List<NST> nstList = nsTemplateRepository.findAll();
            NST lastNST = nstList.get(nstList.size() - 1);
            try {
                requests.put(lastNST);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        if (httpServletRequest.getMethod().equals("DELETE") &&
                requestUrl.indexOf(NST_PATH) != -1 &&
                httpServletResponse.getStatus() == HttpStatus.OK.value()) {
            String nstUUIDtoBeDeleted = requestUrl.split(NST_PATH)[1];

            try {
                requests.put(nstUUIDtoBeDeleted);
            }  catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
