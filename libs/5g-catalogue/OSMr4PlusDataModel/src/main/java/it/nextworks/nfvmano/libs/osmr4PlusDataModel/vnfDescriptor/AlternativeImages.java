package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class AlternativeImages {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vim-type")
    private String vimType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String image;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("image-checksum")
    private String imageChecksum;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getVimType() {
        return vimType;
    }

    public void setVimType(String vimType) {
        this.vimType = vimType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageChecksum() {
        return imageChecksum;
    }

    public void setImageChecksum(String imageChecksum) {
        this.imageChecksum = imageChecksum;
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
        return "AlternativeImages{" +
                "vimType='" + vimType + '\'' +
                ", image='" + image + '\'' +
                ", imageChecksum='" + imageChecksum + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
