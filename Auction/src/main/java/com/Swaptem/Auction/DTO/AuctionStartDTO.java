package com.Swaptem.Auction.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionStartDTO {
    public int ownerId;
    public int[] ownerItems;
    public int minimalOffer;
}
