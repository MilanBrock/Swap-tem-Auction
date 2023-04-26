package com.Swaptem.Auction.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuctionOfferDTO {
    public int auctionId;
    public int participantId;
    public int offerAmount;
}
