package com.Swaptem.Auction.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    private int messageId;
    @ManyToOne
    private Auction auction;
    private int senderId;
    private String message;

    public Message(Auction auctionInput, int senderIdInput, String messageInput){
        this.auction = auctionInput;
        this.senderId = senderId;
        this.message = messageInput;
    }
}
