package com.Swaptem.Auction.DAL;

import com.Swaptem.Auction.Entity.Auction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionRepositoryCustom extends AuctionRepository {
    public Optional<Auction> findAuctionByAuctionIdAndParticipantsContains(int auctionId, int participantId);
    public Optional<Auction> findAuctionByAuctionIdAndActive(int auctionId, boolean active);
}
