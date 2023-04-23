package com.Swaptem.Auction.DAL;

import com.Swaptem.Auction.Entity.AuctionParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionParticipantRepository extends JpaRepository<AuctionParticipant, Integer> {
}
