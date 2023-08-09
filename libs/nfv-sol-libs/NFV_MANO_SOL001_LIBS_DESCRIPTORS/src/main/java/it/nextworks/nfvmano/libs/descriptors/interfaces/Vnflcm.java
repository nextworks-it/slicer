package it.nextworks.nfvmano.libs.descriptors.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFInterfaces;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Vnflcm implements DescriptorInformationElement {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation instantiate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation instantiateStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation instantiateEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation terminate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation terminateStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation terminateEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation modifyInformation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation modifyInformationStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation modifyInformationEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation changeFlavour;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation changeFlavourStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation changeFlavourEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation changeExternalConnectivity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation changeExternalConnectivityStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation changeExternalConnectivityEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation operate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation operateStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation operateEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation heal;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation healStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation healEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation scale;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation scaleStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation scaleEnd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation scaleToLevel;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation scaleToLevelStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnflcm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    LcmOperation scaleToLevelEnd;
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @OneToOne
    @JsonIgnore
    private VNFInterfaces vnfInterfaces;


    public Vnflcm() {
    }

    public Vnflcm(LcmOperation instantiate, LcmOperation instantiateStart, LcmOperation instantiateEnd, LcmOperation terminate, LcmOperation terminateStart, LcmOperation terminateEnd, LcmOperation modifyInformation, LcmOperation modifyInformationStart, LcmOperation modifyInformationEnd, LcmOperation changeFlavour, LcmOperation changeFlavourStart, LcmOperation changeFlavourEnd, LcmOperation changeExternalConnectivity, LcmOperation changeExternalConnectivityStart, LcmOperation changeExternalConnectivityEnd, LcmOperation operate, LcmOperation operateStart, LcmOperation operateEnd, LcmOperation heal, LcmOperation healStart, LcmOperation healEnd, LcmOperation scale, LcmOperation scaleStart, LcmOperation scaleEnd, LcmOperation scaleToLevel, LcmOperation scaleToLevelStart, LcmOperation scaleToLevelEnd) {
        this.instantiate = instantiate;
        this.instantiateStart = instantiateStart;
        this.instantiateEnd = instantiateEnd;
        this.terminate = terminate;
        this.terminateStart = terminateStart;
        this.terminateEnd = terminateEnd;
        this.modifyInformation = modifyInformation;
        this.modifyInformationStart = modifyInformationStart;
        this.modifyInformationEnd = modifyInformationEnd;
        this.changeFlavour = changeFlavour;
        this.changeFlavourStart = changeFlavourStart;
        this.changeFlavourEnd = changeFlavourEnd;
        this.changeExternalConnectivity = changeExternalConnectivity;
        this.changeExternalConnectivityStart = changeExternalConnectivityStart;
        this.changeExternalConnectivityEnd = changeExternalConnectivityEnd;
        this.operate = operate;
        this.operateStart = operateStart;
        this.operateEnd = operateEnd;
        this.heal = heal;
        this.healStart = healStart;
        this.healEnd = healEnd;
        this.scale = scale;
        this.scaleStart = scaleStart;
        this.scaleEnd = scaleEnd;
        this.scaleToLevel = scaleToLevel;
        this.scaleToLevelStart = scaleToLevelStart;
        this.scaleToLevelEnd = scaleToLevelEnd;
    }

    public Vnflcm(VNFInterfaces vnfInterfaces, LcmOperation instantiate, LcmOperation instantiateStart, LcmOperation instantiateEnd, LcmOperation terminate, LcmOperation terminateStart, LcmOperation terminateEnd, LcmOperation modifyInformation, LcmOperation modifyInformationStart, LcmOperation modifyInformationEnd, LcmOperation changeFlavour, LcmOperation changeFlavourStart, LcmOperation changeFlavourEnd, LcmOperation changeExternalConnectivity, LcmOperation changeExternalConnectivityStart, LcmOperation changeExternalConnectivityEnd, LcmOperation operate, LcmOperation operateStart, LcmOperation operateEnd, LcmOperation heal, LcmOperation healStart, LcmOperation healEnd, LcmOperation scale, LcmOperation scaleStart, LcmOperation scaleEnd, LcmOperation scaleToLevel, LcmOperation scaleToLevelStart, LcmOperation scaleToLevelEnd) {
        this.vnfInterfaces = vnfInterfaces;
        this.instantiate = instantiate;
        this.instantiateStart = instantiateStart;
        this.instantiateEnd = instantiateEnd;
        this.terminate = terminate;
        this.terminateStart = terminateStart;
        this.terminateEnd = terminateEnd;
        this.modifyInformation = modifyInformation;
        this.modifyInformationStart = modifyInformationStart;
        this.modifyInformationEnd = modifyInformationEnd;
        this.changeFlavour = changeFlavour;
        this.changeFlavourStart = changeFlavourStart;
        this.changeFlavourEnd = changeFlavourEnd;
        this.changeExternalConnectivity = changeExternalConnectivity;
        this.changeExternalConnectivityStart = changeExternalConnectivityStart;
        this.changeExternalConnectivityEnd = changeExternalConnectivityEnd;
        this.operate = operate;
        this.operateStart = operateStart;
        this.operateEnd = operateEnd;
        this.heal = heal;
        this.healStart = healStart;
        this.healEnd = healEnd;
        this.scale = scale;
        this.scaleStart = scaleStart;
        this.scaleEnd = scaleEnd;
        this.scaleToLevel = scaleToLevel;
        this.scaleToLevelStart = scaleToLevelStart;
        this.scaleToLevelEnd = scaleToLevelEnd;
    }

    public Long getId() {
        return id;
    }

    public VNFInterfaces getVnfInterfaces() {
        return vnfInterfaces;
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

    @JsonProperty("modifyInformation")
    public LcmOperation getModifyInformation() {
        return modifyInformation;
    }

    @JsonProperty("modifyInformationStart")
    public LcmOperation getModifyInformationStart() {
        return modifyInformationStart;
    }

    @JsonProperty("modifyInformationEnd")
    public LcmOperation getModifyInformationEnd() {
        return modifyInformationEnd;
    }

    @JsonProperty("changeFlavour")
    public LcmOperation getChangeFlavour() {
        return changeFlavour;
    }

    @JsonProperty("changeFlavourStart")
    public LcmOperation getChangeFlavourStart() {
        return changeFlavourStart;
    }

    @JsonProperty("changeFlavourEnd")
    public LcmOperation getChangeFlavourEnd() {
        return changeFlavourEnd;
    }

    @JsonProperty("changeExternalConnectivity")
    public LcmOperation getChangeExternalConnectivity() {
        return changeExternalConnectivity;
    }

    @JsonProperty("changeExternalConnectivityStart")
    public LcmOperation getChangeExternalConnectivityStart() {
        return changeExternalConnectivityStart;
    }

    @JsonProperty("changeExternalConnectivityEnd")
    public LcmOperation getChangeExternalConnectivityEnd() {
        return changeExternalConnectivityEnd;
    }

    @JsonProperty("operate")
    public LcmOperation getOperate() {
        return operate;
    }

    @JsonProperty("operateStart")
    public LcmOperation getOperateStart() {
        return operateStart;
    }

    @JsonProperty("operateEnd")
    public LcmOperation getOperateEnd() {
        return operateEnd;
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

    @JsonProperty("scaleToLevel")
    public LcmOperation getScaleToLevel() {
        return scaleToLevel;
    }

    @JsonProperty("scaleToLevelStart")
    public LcmOperation getScaleToLevelStart() {
        return scaleToLevelStart;
    }

    @JsonProperty("scaleToLevelEnd")
    public LcmOperation getScaleToLevelEnd() {
        return scaleToLevelEnd;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
