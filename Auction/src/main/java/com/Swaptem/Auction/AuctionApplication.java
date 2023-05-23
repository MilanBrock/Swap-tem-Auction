package com.Swaptem.Auction;

import com.Swaptem.Auction.DAL.*;
import com.Swaptem.Auction.Entity.Auction;
import com.Swaptem.Auction.Entity.AuctionItem;
import com.Swaptem.Auction.Entity.AuctionParticipant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AuctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}


	@Bean
	CommandLineRunner run(AuctionRepositoryCustom auctionRepository, AuctionParticipantRepositoryCustom participantRepository, AuctionItemRepositoryCustom itemRepository) {
		return args -> {

			// Active auction, active offer
			Auction auction1 = new Auction();
			auction1.setActive(true);
			auctionRepository.save(auction1);
			AuctionParticipant ownerAuction1 = new AuctionParticipant(1, true, auction1);
			participantRepository.save(ownerAuction1);
			AuctionItem ownerItem1Auction1 = new AuctionItem(1, auction1);
			AuctionItem ownerItem2Auction1 = new AuctionItem(5, auction1);
			itemRepository.save(ownerItem1Auction1);
			itemRepository.save(ownerItem2Auction1);
			AuctionParticipant participant1Auction1 = new AuctionParticipant(5, false,auction1);
			AuctionParticipant participant2Auction1 = new AuctionParticipant(30,false,auction1);
			AuctionParticipant participant3Auction1 = new AuctionParticipant(72,false,auction1);
			participantRepository.save(participant1Auction1);
			participantRepository.save(participant2Auction1);
			participantRepository.save(participant3Auction1);
			List<AuctionParticipant> participants = new ArrayList<>();
			participants.add(participant1Auction1);
			participants.add(participant2Auction1);
			participants.add(participant3Auction1);
			auction1.setParticipants(participants);
			auction1.setOwner(ownerAuction1);
			auction1.setMinimalOffer(500);
			auction1.setCurrentOffer(650);
			auction1.setCurrentOfferParticipant(participant2Auction1);
			auctionRepository.save(auction1);


			// Auction that is finished
			Auction auction2 = new Auction();
			auction2.setActive(false);
			auctionRepository.save(auction2);
			AuctionParticipant ownerAuction2 = new AuctionParticipant(63, true, auction2);
			participantRepository.save(ownerAuction2);
			AuctionItem ownerItem1Auction2 = new AuctionItem(10, auction2);
			AuctionItem ownerItem2Auction2 = new AuctionItem(13, auction2);
			AuctionItem ownerItem3Auction2 = new AuctionItem(5, auction2);
			itemRepository.save(ownerItem1Auction2);
			itemRepository.save(ownerItem2Auction2);
			itemRepository.save(ownerItem3Auction2);
			AuctionParticipant participant1Auction2 = new AuctionParticipant(1, false, auction2);
			AuctionParticipant participant2Auction2 = new AuctionParticipant(55, false, auction2);
			participantRepository.save(participant1Auction2);
			participantRepository.save(participant2Auction2);
			auction2.setOwner(ownerAuction2);
			auction2.setMinimalOffer(200);
			auction2.setCurrentOffer(250);
			auction2.setCurrentOfferParticipant(participant1Auction2);
			auctionRepository.save(auction2);


			// Active auction, no offer yet
			Auction auction3 = new Auction();
			auction3.setActive(true);
			auctionRepository.save(auction3);
			AuctionParticipant ownerAuction3 = new AuctionParticipant(2, true, auction3);
			participantRepository.save(ownerAuction3);
			AuctionItem ownerItem1Auction3 = new AuctionItem(17, auction3);
			itemRepository.save(ownerItem1Auction3);
			AuctionParticipant participant1Auction3 = new AuctionParticipant(5, false,auction3);
			participantRepository.save(participant1Auction3);
			auction3.setOwner(ownerAuction3);
			auction3.setMinimalOffer(1000);
			auctionRepository.save(auction3);

		};
	};
}
