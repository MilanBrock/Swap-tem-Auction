package com.Swaptem.Auction.DAL;

import com.Swaptem.Auction.Entity.AuctionParticipant;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionParticipantRepositoryCustom extends AuctionParticipantRepository{
    Optional<AuctionParticipant> findAuctionParticipantByAuction_AuctionId(int auctionId);
}
