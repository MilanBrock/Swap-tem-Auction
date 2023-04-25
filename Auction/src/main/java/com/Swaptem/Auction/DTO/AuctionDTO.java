package com.Swaptem.Auction.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuctionDTO {
    public int ownerId;
    public int[] ownerItems;
    public int minimalOffer;
    public int [] participants;
    public int  currentOfferParticipant;
    public int currentOffer;
}
