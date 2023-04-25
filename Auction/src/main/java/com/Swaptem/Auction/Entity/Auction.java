package com.Swaptem.Auction.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int auctionId;
    @OneToOne
    private AuctionParticipant owner;
    @OneToMany
    private List<AuctionItem> ownerItems;
    private int minimalOffer;
    @OneToMany
    private List<AuctionParticipant> participants;
    private int currentOffer;
    @OneToOne
    private AuctionParticipant currentOfferParticipant;
    private boolean active;


    public Auction(AuctionParticipant ownerInput, List<AuctionItem> ownerItemsInput, int minimalOfferInput){
        this.owner = ownerInput;
        this.ownerItems = ownerItemsInput;
        this.minimalOffer = minimalOfferInput;
    }

}
