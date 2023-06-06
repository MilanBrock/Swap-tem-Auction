package com.Swaptem.Auction.Controller;

import com.Swaptem.Auction.DTO.AuctionDTO;
import com.Swaptem.Auction.DTO.AuctionOfferDTO;
import com.Swaptem.Auction.DTO.AuctionStartDTO;
import com.Swaptem.Auction.DTO.MessageDTO;
import com.Swaptem.Auction.Service.AuctionService;
import com.Swaptem.Auction.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;
    private final SimpMessagingTemplate template;
    final JwtService jwtService;

    @Autowired
    public AuctionController(AuctionService auctionService, SimpMessagingTemplate template, JwtService jwtService){
        this.auctionService = auctionService;
        this.template = template;
        this.jwtService = jwtService;
    }



    @PostMapping()
    public ResponseEntity<String> StartAuction(@RequestBody AuctionStartDTO auctionDTOInput, @RequestHeader String authentication){
        int ownerId = jwtService.getUserIdFromJwtToken(authentication);
        auctionDTOInput.setOwnerId(ownerId);
        boolean succes = auctionService.StartAuction(auctionDTOInput);
        if(succes){
            return new ResponseEntity<>("Auction added", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Auction not added", HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping()
    public ResponseEntity<List<AuctionDTO>> GetAllActiveAuction(){
        List<AuctionDTO> auctionDTOs = auctionService.GetAllAuction();
        if (auctionDTOs.size() > 0 ){
            return ResponseEntity.ok(auctionDTOs);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
    }


    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionDTO> GetActiveAuction(@PathVariable int auctionId, @RequestHeader String authentication){
        int userId = jwtService.getUserIdFromJwtToken(authentication);

        AuctionDTO auctionDTO = auctionService.GetAuction(auctionId, true);
        if(auctionDTO != null){
            for(int participantId: auctionDTO.participants){
                if(participantId == userId){
                    return ResponseEntity.ok(auctionDTO);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
    }

    @GetMapping("/inactive/{auctionId}")
    public ResponseEntity<AuctionDTO> GetInactiveAuction(@PathVariable int auctionId){
        AuctionDTO auctionDTO = auctionService.GetAuction(auctionId, false);
        if(auctionDTO != null){
            return ResponseEntity.ok(auctionDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
    }

    @PutMapping("/join/{auctionId}")
    public ResponseEntity<String> JoinAuction(@PathVariable int auctionId, @RequestHeader String authentication){
        int userId = jwtService.getUserIdFromJwtToken(authentication);
        if(auctionService.AddParticipant(auctionId,userId)){
            return new ResponseEntity<>("Auction joined", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Unable to join auction", HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/offer")
    public ResponseEntity<String> UpdateOffer(@RequestBody AuctionOfferDTO auctionOfferInput, @RequestHeader String authentication){
        int userId = jwtService.getUserIdFromJwtToken(authentication);
        auctionOfferInput.setParticipantId(userId);

        if(auctionService.UpdateOffer(auctionOfferInput)){
            AuctionOfferDTO auctionOfferDTO = auctionService.GetOffer(auctionOfferInput.getAuctionId());
            Integer message = (Integer)auctionOfferDTO.offerAmount;

            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setMessage(message.toString());
            template.convertAndSend("/topic/message", messageDTO);
            
            return new ResponseEntity<>("Offer updated", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Invalid offer", HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/leave/{auctionId}")
    public ResponseEntity<String> LeaveAuction(@PathVariable int auctionId, @RequestHeader String authentication){
        int userId = jwtService.getUserIdFromJwtToken(authentication);

        if(auctionService.RemoveParticipant(auctionId,userId)){
            return new ResponseEntity<>("Left auction", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Could not leave auction", HttpStatus.NOT_ACCEPTABLE);
    }


    @PutMapping("/stop/{auctionId}")
    public ResponseEntity<String> StopAuction(@PathVariable int auctionId, @RequestHeader String authentication){
        int userId = jwtService.getUserIdFromJwtToken(authentication);

        if(auctionService.StopAuction(auctionId,userId)){
            return new ResponseEntity<>("Stopped auction", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Unable to stop auction", HttpStatus.NOT_ACCEPTABLE);
    }

}
