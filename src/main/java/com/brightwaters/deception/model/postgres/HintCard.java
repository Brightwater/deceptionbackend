package com.brightwaters.deception.model.postgres;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.array.ListArrayType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;


@Entity
@Table(name = "hint_card")
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType.class
)
public class HintCard {
    @Id
    @SequenceGenerator(name="service_hintcard_id_seq", sequenceName = "service_hintcard_id_seq",allocationSize = 1)
    @GeneratedValue(generator="service_hintcard_id_seq")
    private Long Id;
    private String name;
    @Type(type = "list-array")
    @Column(
        name = "options",
        columnDefinition = "text[]"
    )
    private ArrayList<String> options;
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
    public ArrayList<String> getOptions() {
        return options;
    }
    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
   
    
   
    
    
}
