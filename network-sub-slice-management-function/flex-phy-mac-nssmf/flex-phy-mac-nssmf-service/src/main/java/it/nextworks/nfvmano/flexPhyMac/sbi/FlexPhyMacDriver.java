package it.nextworks.nfvmano.flexPhyMac.sbi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FlexPhyMacDriver {

    private String ipFlexPhyMacBs;
    private int portFlexPhyMacBs;
    private DatagramSocket socket;
    private InetAddress address;
    private final static Logger LOG = LoggerFactory.getLogger(FlexPhyMacDriver.class);


    public FlexPhyMacDriver(String ipFlexPhyMacBs, int portFlexPhyMacBs){
        this.ipFlexPhyMacBs= ipFlexPhyMacBs;
        this.portFlexPhyMacBs = portFlexPhyMacBs;
    }

    public FlexPhyMacResourceAllocation clearResources() throws ExecutionException {
        List<FlexPhyMacUeAllocation> allocations = new ArrayList<>();

        FlexPhyMacUeAllocation flexPhyMacUeAllocationUe0 = new FlexPhyMacUeAllocation(FlexiblePhyMacConfiguration.ID_UE0,FlexiblePhyMacConfiguration.NO_ALLOCATION);
        FlexPhyMacUeAllocation flexPhyMacUeAllocationUe1 = new FlexPhyMacUeAllocation(FlexiblePhyMacConfiguration.ID_UE1,FlexiblePhyMacConfiguration.NO_ALLOCATION);
        FlexPhyMacUeAllocation flexPhyMacUeAllocationUe2 = new FlexPhyMacUeAllocation(FlexiblePhyMacConfiguration.ID_UE2,FlexiblePhyMacConfiguration.NO_ALLOCATION);
        allocations.add(flexPhyMacUeAllocationUe0);
        allocations.add(flexPhyMacUeAllocationUe1);
        allocations.add(flexPhyMacUeAllocationUe2);

        FlexPhyMacResourceAllocation flexPhyMacResourceAllocation
                = new FlexPhyMacResourceAllocation(allocations);
        if(!sendResourceAllocationRequest(flexPhyMacResourceAllocation))
            throw new ExecutionException(new Throwable("Error during allocation on flexible phy MAC"));

        return flexPhyMacResourceAllocation;

    }

    public boolean sendResourceAllocationRequest(FlexPhyMacResourceAllocation flexPhyMacResourceAllocation) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonPayload = null;
        try {
            jsonPayload = ow.writeValueAsString(flexPhyMacResourceAllocation);
            LOG.info("Payload to send through the socket");
            jsonPayload = jsonPayload.replace("\"ues\"","\"UEs\"");
            jsonPayload = jsonPayload.replace("\"id\"","\"ID\"");

            LOG.info(jsonPayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            return false;
        }

        byte[] buf;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            return false;
        }
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        LOG.info("Converting into byte");
        buf = jsonPayload.getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ipFlexPhyMacBs), portFlexPhyMacBs);
            LOG.info("Sending datagram packet to "+ipFlexPhyMacBs+":"+portFlexPhyMacBs);
            socket.send(packet);
            socket.close();
            //packet = new DatagramPacket(buf, buf.length);
            //socket.receive(packet);
            //String received = new String(packet.getData(), 0, packet.getLength());
            //LOG.debug("Received message. "+received);
        } catch (IOException e) {
            LOG.error(e.getMessage());

            return false;
        }
        LOG.info("Datagram successfully sent ");
        return true;
    }
}
