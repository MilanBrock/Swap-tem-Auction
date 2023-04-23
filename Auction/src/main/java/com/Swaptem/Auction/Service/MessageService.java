package com.Swaptem.Auction.Service;

import com.Swaptem.Auction.Entity.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    public List<Message> GetAllMessagesByAuction(){
        List<Message> messages = new ArrayList<>();
        return messages;
    }

    public boolean AddMessage(Message messageInput){
        return false;
    }

}
