package com.brightwaters.deception.model.postgres;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.array.ListArrayType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "clue_card")
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType.class
)
public class ClueCard {
    @Id
    @SequenceGenerator(name="service_cluecard_id_seq", sequenceName = "service_cluecard_id_seq",allocationSize = 1)
    @GeneratedValue(generator="service_cluecard_id_seq")
    private Long Id;
    @Type(type = "list-array")
    @Column(
        name = "tags",
        columnDefinition = "text[]"
    )
    private ArrayList<String> tags;
    private String description;
    private String imageUrl;
    private String name;
    @Type(type = "list-array")
    @Column(
        name = "sus_votes",
        columnDefinition = "text[]"
    )
    private ArrayList<String> susVotes;
    private String belongsTo;
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<String> getSusVotes() {
        return susVotes;
    }
    public void setSusVotes(ArrayList<String> susVotes) {
        this.susVotes = susVotes;
    }
    public String getBelongsTo() {
        return belongsTo;
    }
    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    

}
