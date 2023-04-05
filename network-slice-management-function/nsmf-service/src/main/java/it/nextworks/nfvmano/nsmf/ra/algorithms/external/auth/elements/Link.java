package it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth.elements;

import it.nextworks.nfvmano.libs.vs.common.topology.LinkType;

public class Link {

    private String linkId;
    private Node source;
    private Node destination;

    private float bandwidth; // in Kb/sec; current bw only.

    private LinkType linkType;

    private float delay;

    public Link(){}

    public Link(String linkId, Node source, Node destination, int bandwidth, LinkType linkType, float delay) {
        this.linkId = linkId;
        this.source = source;
        this.destination = destination;
        this.bandwidth = bandwidth;
        this.linkType = linkType;
        this.delay = delay;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public float getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(float bandwidth) {
        this.bandwidth = bandwidth;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }
}
