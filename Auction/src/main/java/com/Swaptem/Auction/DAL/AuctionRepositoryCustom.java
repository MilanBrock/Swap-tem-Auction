package com.Swaptem.Auction.DAL;

import com.Swaptem.Auction.Entity.Auction;
import com.Swaptem.Auction.Entity.AuctionItem;
import com.Swaptem.Auction.Entity.AuctionParticipant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepositoryCustom extends AuctionRepository {
    public Optional<Auction> findAuctionByAuctionIdAndParticipantsContains(int auctionId, AuctionParticipant participant);
    public Optional<Auction> findAuctionByAuctionIdAndActive(int auctionId, boolean active);
    Optional<List<Auction>> findAllByActive(boolean active);
    Optional<Auction> findAuctionByAuctionIdAndOwner_ParticipantIdAndActive(int auctionId, int participantId, boolean active);
    Optional<Auction> findAuctionByOwnerItemsContainingAndActive(AuctionItem item, boolean active);
}
