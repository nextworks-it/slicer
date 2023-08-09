package it.nextworks.nfvmano.libs.descriptors.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS.NSInterfaces;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Nslcm implements DescriptorInformationElement {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation instantiate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation instantiateStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation instantiateEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation terminate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation terminateStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation terminateEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation update;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation updateStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation updateEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation heal;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation healStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation healEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation scale;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation scaleStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nslcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation scaleEnd;
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @OneToOne
    @JsonIgnore
    private NSInterfaces nsInterfaces;

    public Nslcm() {
    }

    public Nslcm(LcmOperation instantiate, LcmOperation instantiateStart, LcmOperation instantiateEnd, LcmOperation terminate, LcmOperation terminateStart, LcmOperation terminateEnd, LcmOperation update, LcmOperation updateStart, LcmOperation updateEnd, LcmOperation heal, LcmOperation healStart, LcmOperation healEnd, LcmOperation scale, LcmOperation scaleStart, LcmOperation scaleEnd) {
        this.instantiate = instantiate;
        this.instantiateStart = instantiateStart;
        this.instantiateEnd = instantiateEnd;
        this.terminate = terminate;
        this.terminateStart = terminateStart;
        this.terminateEnd = terminateEnd;
        this.update = update;
        this.updateStart = updateStart;
        this.updateEnd = updateEnd;
        this.heal = heal;
        this.healStart = healStart;
        this.healEnd = healEnd;
        this.scale = scale;
        this.scaleStart = scaleStart;
        this.scaleEnd = scaleEnd;

    }

    public Nslcm(NSInterfaces nsInterfaces, LcmOperation instantiate, LcmOperation instantiateStart, LcmOperation instantiateEnd, LcmOperation terminate, LcmOperation terminateStart, LcmOperation terminateEnd, LcmOperation update, LcmOperation updateStart, LcmOperation updateEnd, LcmOperation heal, LcmOperation healStart, LcmOperation healEnd, LcmOperation scale, LcmOperation scaleStart, LcmOperation scaleEnd) {
        this.nsInterfaces = nsInterfaces;
        this.instantiate = instantiate;
        this.instantiateStart = instantiateStart;
        this.instantiateEnd = instantiateEnd;
        this.terminate = terminate;
        this.terminateStart = terminateStart;
        this.terminateEnd = terminateEnd;
        this.update = update;
        this.updateStart = updateStart;
        this.updateEnd = updateEnd;
        this.heal = heal;
        this.healStart = healStart;
        this.healEnd = healEnd;
        this.scale = scale;
        this.scaleStart = scaleStart;
        this.scaleEnd = scaleEnd;
    }

    public Long getId() {
        return id;
    }

    public NSInterfaces getNsInterfaces() {
        return nsInterfaces;
    }

    @JsonProperty("instantiate")
    public LcmOperation getInstantiate() {
        return instantiate;
    }

    @JsonProperty("instantiateStart")
    public LcmOperation getInstantiateStart() {
        return instantiateStart;
    }

    @JsonProperty("instantiateEnd")
    public LcmOperation getInstantiateEnd() {
        return instantiateEnd;
    }

    @JsonProperty("terminate")
    public LcmOperation getTerminate() {
        return terminate;
    }

    @JsonProperty("terminateStart")
    public LcmOperation getTerminateStart() {
        return terminateStart;
    }

    @JsonProperty("terminateEnd")
    public LcmOperation getTerminateEnd() {
        return terminateEnd;
    }

    @JsonProperty("update")
    public LcmOperation getUpdate() {
        return update;
    }

    @JsonProperty("updateStart")
    public LcmOperation getUpdateStart() {
        return updateStart;
    }

    @JsonProperty("updateEnd")
    public LcmOperation getUpdateEnd() {
        return updateEnd;
    }

    @JsonProperty("heal")
    public LcmOperation getHeal() {
        return heal;
    }

    @JsonProperty("healStart")
    public LcmOperation getHealStart() {
        return healStart;
    }

    @JsonProperty("healEnd")
    public LcmOperation getHealEnd() {
        return healEnd;
    }

    @JsonProperty("scale")
    public LcmOperation getScale() {
        return scale;
    }

    @JsonProperty("scaleStart")
    public LcmOperation getScaleStart() {
        return scaleStart;
    }

    @JsonProperty("scaleEnd")
    public LcmOperation getScaleEnd() {
        return scaleEnd;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
