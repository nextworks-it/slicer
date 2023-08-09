package it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

@Embeddable
public class StorageObject {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String zipfile;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String folder;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fs;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String descriptor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("pkg-dir")
    private String pkgDir;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;

    @Transient
    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getZipfile() {
        return zipfile;
    }

    public void setZipfile(String zipfile) {
        this.zipfile = zipfile;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getPkgDir() {
        return pkgDir;
    }

    public void setPkgDir(String pkgDir) {
        this.pkgDir = pkgDir;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "StorageObject{" +
                "zipfile='" + zipfile + '\'' +
                ", folder='" + folder + '\'' +
                ", fs='" + fs + '\'' +
                ", descriptor='" + descriptor + '\'' +
                ", pkgDir='" + pkgDir + '\'' +
                ", path='" + path + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
