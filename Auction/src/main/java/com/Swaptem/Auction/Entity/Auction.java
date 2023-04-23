package com.Swaptem.Auction.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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


}
