package com.brightwaters.deception.model.postgres;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.*;

@Entity
public class DeceptionUser {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "user_sequence"),
        @Parameter(name = "initial_value", value = "4"),
        @Parameter(name = "increment_size", value = "1")
        }
    )
    private Long Id;
    private String username;
    private String password;
    private String nanoWallet;
    private LocalDateTime nanoDt;
    
    
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNanoWallet() {
        return nanoWallet;
    }
    public void setNanoWallet(String nanoWallet) {
        this.nanoWallet = nanoWallet;
    }
    public LocalDateTime getNanoDt() {
        return nanoDt;
    }
    public void setNanoDt(LocalDateTime nanoDt) {
        this.nanoDt = nanoDt;
    }

    
}
