package com.Swaptem.Auction.Service;

import com.Swaptem.Auction.DAL.AuctionItemRepository;
import com.Swaptem.Auction.DAL.AuctionParticipantRepository;
import com.Swaptem.Auction.DAL.AuctionRepository;
import com.Swaptem.Auction.Entity.Auction;
import com.Swaptem.Auction.Entity.AuctionParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    final AuctionRepository auctionRepository;
    final AuctionParticipantRepository participantRepository;
    final AuctionItemRepository itemRepository;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository, AuctionParticipantRepository participantRepository, AuctionItemRepository itemRepository){
        this.auctionRepository = auctionRepository;
        this.participantRepository = participantRepository;
        this.itemRepository = itemRepository;
    }

    public boolean StartAuction(Auction auctionInput){
        boolean succes = false;
        Auction auctionResult = auctionRepository.save(auctionInput);
        if(auctionResult != null){
            succes = true;
        }
        return succes;
    }

    public Auction GetAuction(int auctionIdInput){
        Auction auction = auctionRepository.findById(auctionIdInput).orElse(null);
        return auction;
    }

    public boolean AddParticipant(int auctionIdInput, int participantIdInput){
        Optional<Auction> optionalAuction = auctionRepository.findById(auctionIdInput);
        if(optionalAuction.isPresent()){
            Auction auction = optionalAuction.get();

            AuctionParticipant participant = new AuctionParticipant(participantIdInput, auction);
            participantRepository.save(participant);

            List<AuctionParticipant> participants =  auction.getParticipants();
            participants.add(participant);
            auction.setParticipants(participants);
            auctionRepository.save(auction);
            return true;
        }
        return false;
    }

    public boolean RemoveParticipant(int auctionIdInput, int participantIdInput){
        Optional<AuctionParticipant> optionalParticipant = participantRepository.findById(participantIdInput);
        Optional<Auction> optionalAuction = auctionRepository.findById(auctionIdInput);

        if(optionalParticipant.isPresent() && optionalAuction.isPresent()){
            AuctionParticipant participant = optionalParticipant.get();
            Auction auction = optionalAuction.get();
            if(auction.getParticipants().contains(participant)){
                List<AuctionParticipant> participants =  auction.getParticipants();
                participants.remove(participant);
                auction.setParticipants(participants);
                auctionRepository.save(auction);
                participantRepository.deleteById(participantIdInput);
                return true;
            }
        }
        return false;
    }

    public boolean UpdateOffer(int auctionIdInput, int participantIdInput, int currencyOfferInput){
        Optional<AuctionParticipant> optionalParticipant = participantRepository.findById(participantIdInput);
        Optional<Auction> optionalAuction = auctionRepository.findById(auctionIdInput);

        if(optionalParticipant.isPresent() && optionalAuction.isPresent()){
            AuctionParticipant participant = optionalParticipant.get();
            Auction auction = optionalAuction.get();
            if(auction.getParticipants().contains(participant) && auction.getCurrentOffer() < currencyOfferInput){
                auction.setCurrentOffer(currencyOfferInput);
                auction.setCurrentOfferParticipant(participant);
                auctionRepository.save(auction);
                return true;
            }
        }
        return false;
    }
}
