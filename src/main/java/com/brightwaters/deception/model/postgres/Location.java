package com.brightwaters.deception.model.postgres;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.vladmihalcea.hibernate.type.array.ListArrayType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "location")
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType.class
)
public class Location {
    @Id
    @SequenceGenerator(name="service_location_id_seq", sequenceName = "service_location_id_seq",allocationSize = 1)
    @GeneratedValue(generator="service_location_id_seq")
    private Long Id;
    private String name;
    @Type(type = "list-array")
    @Column(
        name = "tags",
        columnDefinition = "text[]"
    )
    private ArrayList<String> tags;
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    @Override
    public String toString() {
        return "Location [Id=" + Id + ", name=" + name + ", tags=" + tags + "]";
    }
}
