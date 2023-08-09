package it.nextworks.nfvmano.nsmf.record;

import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceSubnetType;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceSubnetInstance;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.*;
import it.nextworks.nfvmano.nsmf.record.elements.*;
import it.nextworks.nfvmano.nsmf.record.repos.NetworkSliceInstanceRepo;
import it.nextworks.nfvmano.nsmf.record.repos.NetworkSliceSubnetInstanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NsiRecordService {

    @Autowired
    private NetworkSliceInstanceRepo networkSliceInstanceRepo;

    @Autowired
    private NetworkSliceSubnetInstanceRepo networkSliceSubnetInstanceRepo;

    public UUID createNetworkSliceInstanceEntry(String nstId, String vsInstanceId, String tenantId, String name, int dlDataRate, int ulDataRate, String upfName) {
        NetworkSliceInstanceRecord instanceRecord = new NetworkSliceInstanceRecord(null, nstId,
                vsInstanceId,
                NetworkSliceInstanceRecordStatus.CREATED,
                tenantId,
                name,
                new ArrayList<>(),
                dlDataRate,
                ulDataRate,
                upfName);

        networkSliceInstanceRepo.saveAndFlush(instanceRecord);
        return instanceRecord.getId();
    }

    public NetworkSliceInstanceRecord getNetworkSliceInstanceRecord(UUID nsiId) throws NotExistingEntityException {
        Optional<NetworkSliceInstanceRecord> record = networkSliceInstanceRepo.findById(nsiId);
        if(record.isPresent()){
            return record.get();
        }else throw new NotExistingEntityException("Network Slice Instance with ID: "+nsiId.toString()+" NOT found in DB");
    }

    public NetworkSliceSubnetInstanceRecord getNetworkSliceSubnetInstanceRecord(UUID nssiId) throws NotExistingEntityException {
        Optional<NetworkSliceSubnetInstanceRecord> record = networkSliceSubnetInstanceRepo.findByNssiIdentifier(nssiId);
        if(record.isPresent()){
            return record.get();
        }else throw new NotExistingEntityException("Network Slice Subnet Instance with ID: "+nssiId.toString()+" NOT found in DB");
    }

    public List<NetworkSliceInstance> getAllNetworkSliceInstance() {
        return networkSliceInstanceRepo.findAll().stream()
                .map(current -> current.getNetworkSliceInstance())
                .collect(Collectors.toList());

    }

    public List<NetworkSliceSubnetInstance> getAllNetworkSliceSubnetInstance() {
        return networkSliceSubnetInstanceRepo.findAll().stream()
                .map(current -> current.getNetworkSliceSubnetInstance())
                .collect(Collectors.toList());
    }

    public NetworkSliceInstance getNetworkSliceInstance(String nsiId) throws NotExistingEntityException {
        return this.getNetworkSliceInstanceRecord(UUID.fromString(nsiId)).getNetworkSliceInstance();
    }

    public List<NetworkSliceInstance> getNsInstanceFromNssi(String nssiId) {
        return networkSliceInstanceRepo.findAll().stream()
                .filter(current -> current.getNetworkSliceSubnetInstanceIds().contains(UUID.fromString(nssiId)))
                .map(current -> current.getNetworkSliceInstance())
                .collect(Collectors.toList());

    }

    public void updateNsInstanceStatus(UUID nsiId, NetworkSliceInstanceRecordStatus status, String errorMsg) throws NotExistingEntityException {
        NetworkSliceInstanceRecord record  =    getNetworkSliceInstanceRecord(nsiId);
        record.setStatus(status);
        record.setErrorMsg(errorMsg);
        networkSliceInstanceRepo.saveAndFlush(record);
    }

    public void updateUpfName(UUID nsiId, String upfName, String errorMsg) throws NotExistingEntityException {
        NetworkSliceInstanceRecord record  =  getNetworkSliceInstanceRecord(nsiId);
        record.setUpfName(upfName);
        record.setErrorMsg(errorMsg);
        networkSliceInstanceRepo.saveAndFlush(record);
    }

    public void addNetworkSubSliceCoreIdentifier(UUID nsiId, UUID nssiId, String nsstId) throws NotExistingEntityException {
        NssResourceAllocationRecord nssResourceAllocationRecord = new NssResourceAllocationRecord(NssResourceAllocationType.COMPUTE,
                new ArrayList<VirtualLinkResourceAllocationRecord>(),
                new HashMap<>(),
                new ArrayList<>());

        NetworkSliceSubnetInstanceRecord instanceRecord = new NetworkSliceSubnetInstanceRecord( nsstId,
                nssiId,
                nsiId,
                NetworkSliceSubnetRecordStatus.INSTANTIATED,
                SliceSubnetType.CORE,
                nssResourceAllocationRecord );
        networkSliceSubnetInstanceRepo.saveAndFlush(instanceRecord);

        NetworkSliceSubnetInstanceRecord networkSliceSubnetInstanceRecord = getNetworkSliceSubnetInstanceRecord(nssiId);
        NetworkSliceInstanceRecord record  =  getNetworkSliceInstanceRecord(nsiId);
        record.addNetworkSliceSubnetInstance(networkSliceSubnetInstanceRecord);
        networkSliceInstanceRepo.saveAndFlush(record);
    }
    public void updateNsInstanceStatus(UUID nsiId, NetworkSliceInstanceRecordStatus status, String imsi, String errorMsg) throws NotExistingEntityException {
        NetworkSliceInstanceRecord record  =    getNetworkSliceInstanceRecord(nsiId);
        record.setStatus(status);
        record.setErrorMsg(errorMsg);

        record.getImsiAssociatedWith().add(imsi);
        networkSliceInstanceRepo.saveAndFlush(record);
    }

    public void updateNsInstanceStatus(UUID nsiId, NetworkSliceInstanceRecordStatus status, String scalingType, int newDataRate, String errorMsg) throws NotExistingEntityException {
        NetworkSliceInstanceRecord record  =    getNetworkSliceInstanceRecord(nsiId);
        record.setStatus(status);
        record.setErrorMsg(errorMsg);
        if(scalingType.equals("UL_SCALING")){
            record.setUlDataRate(newDataRate);
        }
        if(scalingType.equals("DL_SCALING")){
            record.setDlDataRate(newDataRate);
        }

        networkSliceInstanceRepo.saveAndFlush(record);
    }

    public void createNetworkSliceSubnetInstanceEntry(String nsstId, UUID nssiIdentifier, UUID parentNsiId, SliceSubnetType sliceSubnetType, NssResourceAllocation resourceAllocation) throws NotExistingEntityException {
        NssResourceAllocationRecord raRecord = createNssResourceAllocationRecord(resourceAllocation);
        NetworkSliceSubnetInstanceRecord instanceRecord = new NetworkSliceSubnetInstanceRecord( nsstId,
                nssiIdentifier,
                parentNsiId,
                NetworkSliceSubnetRecordStatus.INSTANTIATING,
                sliceSubnetType,
                raRecord );

        networkSliceSubnetInstanceRepo.saveAndFlush(instanceRecord);
        Optional<NetworkSliceInstanceRecord> nsiRecord = networkSliceInstanceRepo.findById(parentNsiId);
        if(nsiRecord.isPresent()){
            nsiRecord.get().addNetworkSliceSubnetInstance(instanceRecord);
            networkSliceInstanceRepo.saveAndFlush(nsiRecord.get());
        }else throw new NotExistingEntityException("Could not find parent NSI with id: "+parentNsiId);

    }

    private NssResourceAllocationRecord createNssResourceAllocationRecord(NssResourceAllocation resourceAllocation){
        List<VirtualLinkResourceAllocationRecord> vlRecords = new ArrayList<>();
        List<TransportFlowAllocationRecord> tsRecords = new ArrayList<>();
        Map<String, String> vnfPlacement = new HashMap<>();
        NssResourceAllocationRecord raRecord = null;
        if(resourceAllocation.getAllocationType().equals(NssResourceAllocationType.COMPUTE)){
            for(VirtualLinkResourceAllocation vl: ((ComputeNssResourceAllocation)resourceAllocation).getvLinkResources()){
                vlRecords.add(new VirtualLinkResourceAllocationRecord(vl.getNsdId(), vl.getVirtualLinkId(), vl.getIngressSipId(), vl.getEgressSipId(),
                        vl.getServiceClassifierAllocation(), vl.getDefaultGw(), vl.isDefault()));
            }
            vnfPlacement=((ComputeNssResourceAllocation)resourceAllocation).getVnfPlacement();


        } else if(resourceAllocation.getAllocationType().equals(NssResourceAllocationType.TRANSPORT)){
            for(TransportFlowAllocation ta: ((TransportNssResourceAllocation) resourceAllocation).getTransportAllocations()){
                tsRecords.add(new TransportFlowAllocationRecord(ta.getTransportFlowType(), ta.getDefaultGw(), ta.isActive()));
            }

        }
        raRecord= new NssResourceAllocationRecord(resourceAllocation.getAllocationType(), vlRecords,vnfPlacement,tsRecords);
        return raRecord;
    }

    public void updateNetworkSliceSubnetInstaceAllocation(UUID nssiId, NssResourceAllocation resourceAllocation) throws NotExistingEntityException {
        NetworkSliceSubnetInstanceRecord nssiRecord = getNetworkSliceSubnetInstanceRecord(nssiId);
        NssResourceAllocationRecord raRecord = createNssResourceAllocationRecord(resourceAllocation);
        nssiRecord.setResourceAllocationRecord(raRecord);
        networkSliceSubnetInstanceRepo.save(nssiRecord);
    }

    public void updateNetworkSliceSubnetStatus(UUID nssiId, NetworkSliceSubnetRecordStatus status )throws NotExistingEntityException{
        NetworkSliceSubnetInstanceRecord nssiRecord = getNetworkSliceSubnetInstanceRecord(nssiId);
        nssiRecord.setStatus(status);
        networkSliceSubnetInstanceRepo.save(nssiRecord);
    }



}
