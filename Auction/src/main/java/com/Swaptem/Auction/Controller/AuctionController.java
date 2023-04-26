package com.Swaptem.Auction.Controller;

import com.Swaptem.Auction.DTO.AuctionDTO;
import com.Swaptem.Auction.DTO.AuctionOfferDTO;
import com.Swaptem.Auction.DTO.AuctionStartDTO;
import com.Swaptem.Auction.Entity.Auction;
import com.Swaptem.Auction.Service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService){
        this.auctionService = auctionService;
    }



    @PostMapping()
    public ResponseEntity<String> StartAuction(@RequestBody AuctionStartDTO auctionDTOInput){
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
    public ResponseEntity<AuctionDTO> GetActiveAuction(@PathVariable int auctionId){
        AuctionDTO auctionDTO = auctionService.GetAuction(auctionId, true);
        if(auctionDTO != null){
            return ResponseEntity.ok(auctionDTO);
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

    @PutMapping("/join/{auctionId}/{userId}")
    public ResponseEntity<String> JoinAuction(@PathVariable int auctionId, @PathVariable int userId){
        System.out.println("HELPMEEEEEEEEEEEEE");
        if(auctionService.AddParticipant(auctionId,userId)){
            return new ResponseEntity<>("Auction joined", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Unable to join auction", HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/offer")
    public ResponseEntity<String> UpdateOffer(@RequestBody AuctionOfferDTO auctionOffer){
        if(auctionService.UpdateOffer(auctionOffer.getAuctionId(),auctionOffer.participantId,auctionOffer.getOfferAmount())){
            return new ResponseEntity<>("Offer updated", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Offer not updated", HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/leave/{auctionId}/{userId}")
    public ResponseEntity<String> LeaveAuction(@PathVariable int auctionId, @PathVariable int userId){
        if(auctionService.RemoveParticipant(auctionId,userId)){
            return new ResponseEntity<>("Left auction", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Could not leave auction", HttpStatus.NOT_ACCEPTABLE);
    }


    @PutMapping("/stop/{auctionId}/{userId}")
    public ResponseEntity<String> StopAuction(@PathVariable int auctionId, @PathVariable int userId){
        if(auctionService.StopAuction(auctionId,userId)){
            return new ResponseEntity<>("Stopped auction", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Unable to stop auction", HttpStatus.NOT_ACCEPTABLE);
    }
}
