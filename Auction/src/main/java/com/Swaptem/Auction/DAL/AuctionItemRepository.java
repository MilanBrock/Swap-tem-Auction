package com.Swaptem.Auction.DAL;

import com.Swaptem.Auction.Entity.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionItemRepository extends JpaRepository<AuctionItem, Integer> {
}
