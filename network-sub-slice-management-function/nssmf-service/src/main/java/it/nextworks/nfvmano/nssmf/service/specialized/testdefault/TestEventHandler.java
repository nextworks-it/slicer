/*
 * Copyright (c) 2021 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.nssmf.service.specialized.testdefault;


import it.nextworks.nfvmano.nssmf.service.NssmfLcmService;
import it.nextworks.nfvmano.nssmf.service.messages.notification.NssiStatusChangeNotiticationMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.InstantiateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.TerminateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.nssmanagement.NssLcmEventHandler;


public class TestEventHandler extends NssLcmEventHandler {


    public TestEventHandler() {
        this.setEnableAutoNotification(true);
    }

//    @Subscribe
//    private void testEvent(NssiStatusChangeNotiticationMessage message){
//        log.debug("Test event invoked");
//    }

    @Override
    protected void processInstantiateNssRequest(InstantiateNssiRequestMessage message) {
        log.debug("Invoked processInstantiateNssRequest of class " + TestEventHandler.class.getName());
        //Test
//        NssmfLcmService service = new NssmfLcmService();
//        if (service.checkNssIdExistence(super.getNetworkSubSliceInstanceId()))
//            log.info("SINGLETON!!!");
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            log.debug("something");
        }

        TestInstantiationPayload testInstantiationPayload = (TestInstantiationPayload) message.getInstantiateNssiRequest();
        System.out.println("NssiID: "+ this.getNetworkSubSliceInstanceId().toString() + "\nMessage Content:\n"+
                testInstantiationPayload.getInstantiationDetail1()+"\n"+testInstantiationPayload.getGetInstantiationDetail2());

        NssiStatusChangeNotiticationMessage notif = new NssiStatusChangeNotiticationMessage();
        notif.setSuccess(true);
        this.getEventBus().post(notif);


    }
    @Override
    protected void processTerminateNssRequest(TerminateNssiRequestMessage message) {
        log.debug("Invoked processTerminateNssRequest of class " + TestEventHandler.class.getName());
        NssiStatusChangeNotiticationMessage notif = new NssiStatusChangeNotiticationMessage();
        notif.setSuccess(true);
        this.getEventBus().post(notif);
    }

}

