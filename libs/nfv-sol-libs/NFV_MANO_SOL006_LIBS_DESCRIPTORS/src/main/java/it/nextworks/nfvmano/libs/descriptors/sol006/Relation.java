package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Relation {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("entities")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "day1_2_relation_fk", referencedColumnName = "uuid")
    private List<RelationEntities> entities = null;

    public Relation name(String name) {
        this.name = name;
        return this;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Relation entities(List<RelationEntities> entities) {
        this.entities = entities;
        return this;
    }

    public Relation addEntitiesItem(RelationEntities relationEntities) {
        if(this.entities == null)
            this.entities = new ArrayList<>();

        this.entities.add(relationEntities);
        return this;
    }

    public List<RelationEntities> getEntities() { return entities; }

    public void setEntities(List<RelationEntities> entities) { this.entities = entities; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Relation relation = (Relation) o;

        return Objects.equals(this.name, relation.name) &&
                Objects.equals(this.entities, relation.entities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, entities);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class Relation {\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    entities: ").append(toIndentedString(entities)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
