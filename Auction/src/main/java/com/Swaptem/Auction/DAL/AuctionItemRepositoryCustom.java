package com.Swaptem.Auction.DAL;

import com.Swaptem.Auction.Entity.AuctionItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionItemRepositoryCustom extends AuctionItemRepository{
    Optional<List<AuctionItem>> findAllByAuction_AuctionId(int auctionId);
}
