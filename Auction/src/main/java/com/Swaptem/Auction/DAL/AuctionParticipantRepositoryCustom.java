package com.Swaptem.Auction.DAL;

import com.Swaptem.Auction.Entity.AuctionParticipant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionParticipantRepositoryCustom extends AuctionParticipantRepository{
    Optional<List<AuctionParticipant>> findAuctionParticipantByAuction_AuctionId(int auctionId);
}
