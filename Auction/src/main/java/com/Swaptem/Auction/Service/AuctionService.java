package com.Swaptem.Auction.Service;

import com.Swaptem.Auction.DAL.*;
import com.Swaptem.Auction.DTO.AuctionDTO;
import com.Swaptem.Auction.DTO.AuctionOfferDTO;
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
    final AuctionParticipantRepositoryCustom participantRepository;
    final AuctionItemRepositoryCustom itemRepository;

    @Autowired
    public AuctionService(AuctionRepositoryCustom auctionRepository, AuctionParticipantRepositoryCustom participantRepository, AuctionItemRepositoryCustom itemRepository){
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

        List<AuctionParticipant> participants = new ArrayList<>();
        participants.add(owner);

        auction.setOwner(owner);
        auction.setOwnerItems(ownerItems);
        auction.setMinimalOffer(auctionInput.minimalOffer);
        auction.setActive(true);
        auction.setParticipants(participants);
        Auction auctionResult = auctionRepository.save(auction);

        if(auctionResult != null){
            succes = true;
        }
        return succes;
    }


    public List<AuctionDTO> GetAllAuction(){
        List<Auction> auctions = auctionRepository.findAllByActive(true).orElse(null);

        for (int i = 0; i < auctions.size(); i++){
            List<AuctionItem> ownerItems = itemRepository.findAllByAuction_AuctionId(auctions.get(i).getAuctionId()).orElse(null);
            List<AuctionParticipant> participants = participantRepository.findAuctionParticipantByAuction_AuctionId(auctions.get(i).getAuctionId()).orElse(null);
            auctions.get(i).setOwnerItems(ownerItems);
            auctions.get(i).setParticipants(participants);
        }

        List<AuctionDTO> auctionDTOs = new ArrayList<>();

        if(auctions != null){
            for(int i = 0; i < auctions.size(); i++){
               AuctionDTO auctionDTO = ToAuctionDTO(auctions.get(i));
               auctionDTOs.add(auctionDTO);
            }
        }
        return auctionDTOs;
    }


    public AuctionDTO GetAuction(int auctionIdInput, boolean activeInput){
        Auction auction = auctionRepository.findAuctionByAuctionIdAndActive(auctionIdInput, activeInput).orElse(null);
        AuctionDTO auctionDTO = null;

        if(auction != null){
            auctionDTO = ToAuctionDTO(auction);
        }

        return auctionDTO;
    }

    public boolean AddParticipant(int auctionIdInput, int participantIdInput){
        // Bestaat de veiling?
        Auction auction = auctionRepository.findAuctionByAuctionIdAndActive(auctionIdInput, true).orElse(null);

        // Is de participant al deel van een veiling?
        AuctionParticipant participant = participantRepository.findById(participantIdInput).orElse(null);

        if(participant == null && auction != null){
            participant = new AuctionParticipant(participantIdInput, auction);
            participantRepository.save(participant);
            return true;
        }
        return false;
    }

    public boolean RemoveParticipant(int auctionIdInput, int participantIdInput){
        // Bestaat de veiling?
        Auction auctionActive = auctionRepository.findAuctionByAuctionIdAndActive(auctionIdInput, true).orElse(null);

        // Is de gegeven gebruiker deel van de veiling?
        AuctionParticipant participant = participantRepository.findById(participantIdInput).orElse(null);

        if(auctionActive != null && participant!= null){
            Auction emptyAuction = new Auction();
            participant.setAuction(emptyAuction);
            participantRepository.save(participant);
            return true;
        }
        return false;
    }

    public boolean UpdateOffer(int auctionIdInput, int participantIdInput, int currencyOfferInput){
        // Bestaat de veiling?
        Auction auctionActive = auctionRepository.findAuctionByAuctionIdAndActive(auctionIdInput, true).orElse(null);

        // Is de gegeven gebruiker deel van de veiling?
        AuctionParticipant participant = new AuctionParticipant(participantIdInput, auctionActive);
        Auction auction = auctionRepository.findAuctionByAuctionIdAndParticipantsContains(auctionIdInput, participant).orElse(null);

        if(auction != null){
            if(auction.getCurrentOffer() < currencyOfferInput){
                auction.setCurrentOffer(currencyOfferInput);
                auction.setCurrentOfferParticipant(participant);
                auctionRepository.save(auction);
                return true;
            }
        }
        return false;
    }

    public AuctionOfferDTO GetOffer(int auctionIdInput){
        AuctionOfferDTO auctionOfferDTO = null;
        Auction auction = auctionRepository.findAuctionByAuctionIdAndActive(auctionIdInput, true).orElse(null);
        if(auction != null){
             auctionOfferDTO = new AuctionOfferDTO(auctionIdInput,auction.getCurrentOfferParticipant().getParticipantId(),auction.getCurrentOffer());
        }

        return auctionOfferDTO;
    }


    public boolean StopAuction(int auctionIdInput, int ownerIdInput){
        Auction auction = auctionRepository.findAuctionByAuctionIdAndOwner_ParticipantIdAndActive(auctionIdInput,ownerIdInput, true).orElse(null);
        boolean succes = false;
        if(auction != null){
            auction.setActive(false);
            auctionRepository.save(auction);
            succes = true;
        }
        return succes;
    }



    private AuctionDTO ToAuctionDTO(Auction auctionInput){
        AuctionDTO auctionDTO = null;

        int[] ownerItems = new int [auctionInput.getOwnerItems().size()];
        for(int i = 0; i < auctionInput.getOwnerItems().size(); i++){
            ownerItems[i] = auctionInput.getOwnerItems().get(i).getItemId();
        }

        int[] participants = new int [auctionInput.getParticipants().size()];
        for(int i = 0; i < auctionInput.getParticipants().size(); i++){
            participants[i] = auctionInput.getParticipants().get(i).getParticipantId();
        }

        int currentOfferParticipant = 0;
        if(auctionInput.getCurrentOfferParticipant() != null){
            currentOfferParticipant = auctionInput.getCurrentOfferParticipant().getParticipantId();
        }
        auctionDTO = new AuctionDTO(auctionInput.getAuctionId(),auctionInput.getOwner().getParticipantId(),ownerItems,auctionInput.getMinimalOffer(),participants, currentOfferParticipant, auctionInput.getCurrentOffer());

        return auctionDTO;
    }
}
