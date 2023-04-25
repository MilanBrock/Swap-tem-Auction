package com.Swaptem.Auction.Controller;

import com.Swaptem.Auction.DTO.AuctionDTO;
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
    public ResponseEntity<List<AuctionStartDTO>> GetAllActiveAuction(@PathVariable int auctionId){
        List<AuctionStartDTO> auctionDTOs = new ArrayList<>();

        return ResponseEntity.ok(auctionDTOs);
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
        AuctionDTO auctionDTO = auctionService.GetAuction(auctionId, true);
        if(auctionDTO != null){
            return ResponseEntity.ok(auctionDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
    }

    @PutMapping("/join/{auctionId}/{userId}")
    public ResponseEntity<String> JoinAuction(@PathVariable int auctionId, @PathVariable int userId){
        auctionService.AddParticipant(auctionId,userId);
        return new ResponseEntity<>("Auction joined", HttpStatus.CREATED);
    }

    @PutMapping("/{auctionId}/{offerAmount}")
    public ResponseEntity<String> UpdateOffer(@PathVariable int auctionId, @PathVariable int offerAmount){
        return new ResponseEntity<>("Offer updated", HttpStatus.CREATED);
    }

    @PutMapping("/leave/{auctionId}/{userId}")
    public ResponseEntity<String> LeaveAuction(@PathVariable int auctionId, @PathVariable int userId){
        auctionService.RemoveParticipant(auctionId,userId);
        return new ResponseEntity<>("Left auction", HttpStatus.CREATED);
    }


    @PutMapping("/stop/{auctionId}/{userId}")
    public ResponseEntity<String> StopAuction(@PathVariable int auctionId, @PathVariable int userId){

        return new ResponseEntity<>("Left auction", HttpStatus.CREATED);
    }





}
