package com.Swaptem.Auction.Service;

import com.Swaptem.Auction.DAL.AuctionItemRepository;
import com.Swaptem.Auction.DAL.AuctionParticipantRepository;
import com.Swaptem.Auction.DAL.AuctionRepository;
import com.Swaptem.Auction.DAL.AuctionRepositoryCustom;
import com.Swaptem.Auction.DTO.AuctionDTO;
import com.Swaptem.Auction.DTO.AuctionStartDTO;
import com.Swaptem.Auction.Entity.Auction;
import com.Swaptem.Auction.Entity.AuctionItem;
import com.Swaptem.Auction.Entity.AuctionParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    final AuctionRepositoryCustom auctionRepository;
    final AuctionParticipantRepository participantRepository;
    final AuctionItemRepository itemRepository;

    @Autowired
    public AuctionService(AuctionRepositoryCustom auctionRepository, AuctionParticipantRepository participantRepository, AuctionItemRepository itemRepository){
        this.auctionRepository = auctionRepository;
        this.participantRepository = participantRepository;
        this.itemRepository = itemRepository;
    }

    public boolean StartAuction(AuctionStartDTO auctionInput){
        boolean succes = false;

        Auction auction = new Auction();
        auction = auctionRepository.save(auction); // Assign auction ID

        AuctionParticipant owner = new AuctionParticipant(auctionInput.ownerId, true, auction);
        participantRepository.save(owner);

        List<AuctionItem> ownerItems = new ArrayList<>();
        for(int i = 0; i < auctionInput.ownerItems.length; i++){
            AuctionItem item = new AuctionItem(auctionInput.getOwnerItems()[i],auction);
            itemRepository.save(item);
            ownerItems.add(item);
        }

        auction.setOwner(owner);
        auction.setOwnerItems(ownerItems);
        auction.setMinimalOffer(auctionInput.minimalOffer);
        auction.setActive(true);
        Auction auctionResult = auctionRepository.save(auction);

        if(auctionResult != null){
            succes = true;
        }
        return succes;
    }

    public AuctionDTO GetAuction(int auctionIdInput, boolean activeInput){
        Auction auction = auctionRepository.findAuctionByAuctionIdAndActive(auctionIdInput, activeInput).orElse(null);
        AuctionDTO auctionDTO = null;

        if(auction != null){
            int[] ownerItems = new int [auction.getOwnerItems().size()];
            for(int i = 0; i < auction.getOwnerItems().size(); i++){
                ownerItems[i] = auction.getOwnerItems().get(i).getItemId();
            }

            int[] participants = new int [auction.getParticipants().size()];
            for(int i = 0; i < auction.getParticipants().size(); i++){
                participants[i] = auction.getParticipants().get(i).getParticipantId();
            }

            int currentOfferParticipant = 0;
            if(auction.getCurrentOfferParticipant() != null){
                currentOfferParticipant = auction.getCurrentOfferParticipant().getParticipantId();
            }


            auctionDTO = new AuctionDTO(auction.getOwner().getParticipantId(),ownerItems,auction.getMinimalOffer(),participants, currentOfferParticipant, auction.getCurrentOffer());
        }


        return auctionDTO;
    }

    public boolean AddParticipant(int auctionIdInput, int participantIdInput){
        Optional<Auction> optionalAuction = auctionRepository.findAuctionByAuctionIdAndParticipantsContains(auctionIdInput, participantIdInput);
        if(optionalAuction.isPresent()){
            Auction auction = optionalAuction.get();

            AuctionParticipant participant = new AuctionParticipant(participantIdInput, auction);
            //participantRepository.save(participant);

            List<AuctionParticipant> participants =  auction.getParticipants();
            participants.add(participant);
            auction.setParticipants(participants);
            auctionRepository.save(auction);
            return true;
        }
        return false;
    }

    public boolean RemoveParticipant(int auctionIdInput, int participantIdInput){
//        Optional<AuctionParticipant> optionalParticipant = participantRepository.findById(participantIdInput);
//        Optional<Auction> optionalAuction = auctionRepository.findById(auctionIdInput);
        Auction auction = auctionRepository.findAuctionByAuctionIdAndParticipantsContains(auctionIdInput, participantIdInput).orElse(null);

        if(auction != null){
            auction.getParticipants();
//            AuctionParticipant participant = optionalParticipant.get();
//            Auction auction = optionalAuction.get();
//            if(auction.getParticipants().contains(participant)){
//                List<AuctionParticipant> participants =  auction.getParticipants();
//                participants.remove(participant);
//                auction.setParticipants(participants);
//                auctionRepository.save(auction);
//                participantRepository.deleteById(participantIdInput);
                return true;
//            }
        }
        return false;
    }

    public boolean UpdateOffer(int auctionIdInput, int participantIdInput, int currencyOfferInput){
//        Optional<AuctionParticipant> optionalParticipant = participantRepository.findById(participantIdInput);
//        Optional<Auction> optionalAuction = auctionRepository.findById(auctionIdInput);
//
//        if(optionalParticipant.isPresent() && optionalAuction.isPresent()){
//            AuctionParticipant participant = optionalParticipant.get();
//            Auction auction = optionalAuction.get();
//            if(auction.getParticipants().contains(participant) && auction.getCurrentOffer() < currencyOfferInput){
//                auction.setCurrentOffer(currencyOfferInput);
//                auction.setCurrentOfferParticipant(participant);
//                auctionRepository.save(auction);
//                return true;
//            }
//        }
        return false;
    }
}
