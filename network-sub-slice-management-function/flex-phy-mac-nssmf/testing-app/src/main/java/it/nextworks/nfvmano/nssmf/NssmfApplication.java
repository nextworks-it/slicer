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

package it.nextworks.nfvmano.nssmf;


import it.nextworks.nfvmano.nssmf.flex_phy_mac.nssmanagement.FlexPhyMacClient;
import it.nextworks.nfvmano.nssmf.flex_phy_mac.nssmanagement.informantionModel.FlexPhyMacResourceAllocationPayload;
import it.nextworks.nfvmano.nssmf.flex_phy_mac.nssmanagement.informantionModel.FlexPhyMacUeAllocation;

import java.util.ArrayList;
import java.util.List;

public class NssmfApplication {

    public static void main(String[] args) {
        FlexPhyMacClient flexPhyMacClient = new FlexPhyMacClient("127.0.0.1",9999);
        List<FlexPhyMacUeAllocation> allocationsList = new ArrayList<>();
        FlexPhyMacUeAllocation ue0 = new FlexPhyMacUeAllocation("ID_UE0","80");
        FlexPhyMacUeAllocation ue1 = new FlexPhyMacUeAllocation("ID_UE1","10");
        FlexPhyMacUeAllocation ue2 = new FlexPhyMacUeAllocation("ID_UE2","10");
        allocationsList.add(ue0);
        allocationsList.add(ue1);
        allocationsList.add(ue2);
        FlexPhyMacResourceAllocationPayload flexPhyMacResourceAllocationPayload = new FlexPhyMacResourceAllocationPayload(allocationsList);
        flexPhyMacClient.sendResourceAllocationRequest(flexPhyMacResourceAllocationPayload);

    }

}