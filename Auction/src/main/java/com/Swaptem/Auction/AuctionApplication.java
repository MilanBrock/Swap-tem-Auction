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

			// Auction 1, active
			Auction auction1 = new Auction();
			auction1 = auctionRepository.save(auction1); // Assign auction ID

			AuctionParticipant owner1 = new AuctionParticipant(1, true, auction1);
			participantRepository.save(owner1);

			List<AuctionItem> ownerItems1 = new ArrayList<>();

			AuctionItem item1 = new AuctionItem(4,auction1);
			itemRepository.save(item1);
			ownerItems1.add(item1);


			List<AuctionParticipant> participants1 = new ArrayList<>();
			AuctionParticipant participant1 = new AuctionParticipant(5,auction1);
			participants1.add(participant1);
			participants1.add(owner1);

			auction1.setOwner(owner1);
			auction1.setOwnerItems(ownerItems1);
			auction1.setMinimalOffer(300);
			auction1.setActive(true);
			auction1.setParticipants(participants1);
			auctionRepository.save(auction1);


			// Auction 2, inactive
			Auction auction2 = new Auction();
			auction2 = auctionRepository.save(auction2); // Assign auction ID

			AuctionParticipant owner2 = new AuctionParticipant(10, true, auction2);
			participantRepository.save(owner2);

			List<AuctionItem> ownerItems2 = new ArrayList<>();

			AuctionItem item2 = new AuctionItem(5,auction2);
			itemRepository.save(item2);
			ownerItems2.add(item2);


			List<AuctionParticipant> participants2 = new ArrayList<>();
			AuctionParticipant participant2 = new AuctionParticipant(11, auction2);
			participants2.add(owner2);
			participants2.add(participant2);

			auction2.setOwner(owner2);
			auction2.setOwnerItems(ownerItems2);
			auction2.setMinimalOffer(300);
			auction2.setActive(false);
			auction2.setParticipants(participants2);
			auction2.setCurrentOffer(500);
			auction2.setCurrentOfferParticipant(participant2);
			auctionRepository.save(auction2);




			// Auction 3, active
			Auction auction3 = new Auction();
			auction3 = auctionRepository.save(auction3); // Assign auction ID

			AuctionParticipant owner3 = new AuctionParticipant(4, true, auction3);
			participantRepository.save(owner3);

			List<AuctionItem> ownerItems3 = new ArrayList<>();

			AuctionItem item3 = new AuctionItem(4,auction3);
			itemRepository.save(item3);
			ownerItems3.add(item3);


			List<AuctionParticipant> participants3 = new ArrayList<>();
			AuctionParticipant participant3 = new AuctionParticipant(2, auction3);
			participants3.add(participant3);
			participants3.add(owner3);

			auction3.setOwner(owner3);
			auction3.setOwnerItems(ownerItems3);
			auction3.setMinimalOffer(300);
			auction3.setActive(true);
			auction3.setParticipants(participants3);
			auctionRepository.save(auction3);
		};
	};
}
