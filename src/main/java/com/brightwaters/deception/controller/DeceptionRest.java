package com.brightwaters.deception.controller;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;

import com.brightwaters.deception.model.h2.GameState;
import com.brightwaters.deception.model.postgres.ClueCard;
import com.brightwaters.deception.model.postgres.HintCard;
import com.brightwaters.deception.model.postgres.WeaponCard;
import com.brightwaters.deception.repository.h2.GameStateRepository;
import com.brightwaters.deception.repository.postgres.ClueCardRepository;
import com.brightwaters.deception.repository.postgres.HintCardRepository;
import com.brightwaters.deception.repository.postgres.WeaponCardRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeceptionRest {

    @Autowired
    GameStateRepository rep;

    @Autowired
    HintCardRepository hintRep;

    @Autowired
    ClueCardRepository cRep;

    @Autowired
    WeaponCardRepository weaponRep;
    
    @GetMapping("/deception/test")
    private void test() {
       System.out.println("Hello world");
    }

    
    // @GetMapping("/deception/putTest")
    // private GameState testPut() {
    //     String t = "hello world";
    //     GameState geeb = new GameState();
    //     geeb.setTest(t);
    //     rep.save(geeb);
        
    //     return geeb;
       
    // }
    
    @GetMapping("/deception/getHint")
    private HintCard getHint() {
        HintCard card = hintRep.findByName("Weather");
        System.out.println(card);
        return card;
    }

    @GetMapping("/deception/getWeap")
    private WeaponCard getWeap() {
        WeaponCard card = weaponRep.findByName("Axe");
        System.out.println(card);
        return card;
    }

    @GetMapping("/deception/getClue")
    private ClueCard getc() {
        ClueCard cards = cRep.findByName("Hairpin");
       
        return cards;
    }

    @GetMapping("/deception/getAllClue")
    private List<ClueCard> getAc() {
        List<ClueCard> cards = cRep.findAllCards();
       
        return cards;
    }
    

}
