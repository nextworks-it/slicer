/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.libs.vs.common.topology;

/**
 * Created by Marco Capitani on 24/04/17.
 *
 * @author Marco Capitani (m.capitani AT nextworks.it)
 */
public class TopologyLink {

    private String linkId;
    private TopologyNode source;
    private TopologyNode destination;
    private TopologyCp sourceCp;
    private TopologyCp destinationCp;

    private double power; // in W/Kb

    private float bandwidth; // in Kb/sec; current bw only.

    private LinkType linkType;

    private float delay;

    public TopologyLink(){}

    public TopologyLink(String linkId,
                        TopologyNode source, TopologyNode destination,
                        TopologyCp sourceCp, TopologyCp destinationCp,
                        double power, float bandwidth, LinkType linkType, float delay) {
        if (source == null) {
            throw new IllegalArgumentException("Source Id must not be null.");
        }
        if (destination == null) {
            throw new IllegalArgumentException("Destination Id must not be null.");
        }
        this.linkId = linkId;
        this.source = source;
        this.destination = destination;
        this.power = power;
        this.bandwidth = bandwidth;
        this.sourceCp = sourceCp;
        this.destinationCp = destinationCp;
        this.linkType=linkType;
        this.delay=delay;
    }
    public TopologyLink(String linkId,
                        TopologyNode source, TopologyNode destination,
                        TopologyCp sourceCp, TopologyCp destinationCp, LinkType linkType, float delay) {
        if (source == null) {
            throw new IllegalArgumentException("Source Id must not be null.");
        }
        if (destination == null) {
            throw new IllegalArgumentException("Destination Id must not be null.");
        }
        this.linkId = linkId;
        this.source = source;
        this.destination = destination;
        this.sourceCp = sourceCp;
        this.destinationCp = destinationCp;
        this.linkType=linkType;
        this.delay=delay;
    }

    public TopologyNode opposite(TopologyNode node) {
        if (node.equals(source)) {
            return destination;
        } else if (node.equals(destination)) {
            return source;
        } else {
            throw new IllegalArgumentException(
                    String.format(
                            "Node %s not present in link %s",
                            node, this
                    )
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TopologyLink link = (TopologyLink) o;

        return source.equals(link.source)
                && destination.equals(link.destination);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + destination.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Link(%s -> %s)", source, destination);
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public TopologyNode getSource() {
        return source;
    }

    public void setSource(TopologyNode source) {
        this.source = source;
    }

    public TopologyNode getDestination() {
        return destination;
    }

    public void setDestination(TopologyNode destination) {
        this.destination = destination;
    }

    public TopologyCp getSourceCp() {
        return sourceCp;
    }

    public void setSourceCp(TopologyCp sourceCp) {
        this.sourceCp = sourceCp;
    }

    public TopologyCp getDestinationCp() {
        return destinationCp;
    }

    public void setDestinationCp(TopologyCp destinationCp) {
        this.destinationCp = destinationCp;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
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
