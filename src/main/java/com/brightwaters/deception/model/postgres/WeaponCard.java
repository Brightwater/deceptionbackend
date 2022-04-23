package com.brightwaters.deception.model.postgres;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.array.ListArrayType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;

@Entity
@Table(name = "weapon_card")
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType.class
)
public class WeaponCard {
    @Id
    @SequenceGenerator(name="service_weaponcard_id_seq", sequenceName = "service_weaponcard_id_seq",allocationSize = 1)
    @GeneratedValue(generator="service_weaponcard_id_seq")
    private Long id;

    @Type(type = "list-array")
    @Column(
        name = "tags",
        columnDefinition = "text[]"
    )
    private ArrayList<String> tags;
    private String description;
    private String imageUrl;
    private String killMethod;
    private String name;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getKillMethod() {
        return killMethod;
    }
    public void setKillMethod(String killMethod) {
        this.killMethod = killMethod;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
