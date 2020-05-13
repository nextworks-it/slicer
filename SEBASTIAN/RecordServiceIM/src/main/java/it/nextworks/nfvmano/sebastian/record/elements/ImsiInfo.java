package it.nextworks.nfvmano.sebastian.record.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

@Entity
public class ImsiInfo {
    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID uuid; //FOR JPA

    @Column(/*unique=true,*/ nullable=false)
    private String id; //manually inserted
    private String name;
    private String plmn;

    private String slice;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(targetClass=String.class)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> imsis;

    public ImsiInfo(){
        //FOR JPA;
    }

    public ImsiInfo(String id, String name, String plmn, String slice, List<String> imsis) {
        this.id = id;
        this.name = name;
        this.plmn = plmn;
        this.slice = slice;
        this.imsis = imsis;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlmn() {
        return plmn;
    }


    public List<String> getImsis() {
        return imsis;
    }

    public String getSlice() {
        return slice;
    }

    public void setSlice(String slice) {
        this.slice = slice;
    }

    public UUID getUuid() {
        return uuid;
    }


}

