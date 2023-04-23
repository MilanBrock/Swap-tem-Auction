package com.Swaptem.Auction.Controller;

import com.Swaptem.Auction.DTO.AuctionDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auctions")
public class AuctionController {

    @PostMapping()
    public ResponseEntity<String> StartAuction(@RequestBody AuctionDTO auctionDTOInput){
        return new ResponseEntity<>("Auction added", HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<AuctionDTO>> GetAllAuction(@PathVariable int auctionId){
        List<AuctionDTO> auctionDTOs = new ArrayList<>();
        return ResponseEntity.ok(auctionDTOs);
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionDTO> GetAuction(@PathVariable int auctionId){
        AuctionDTO auctionDTO = new AuctionDTO();
        return ResponseEntity.ok(auctionDTO);
    }

    @PutMapping("/join/{auctionId}/{userId}")
    public ResponseEntity<String> JoinAuction(@PathVariable int auctionId, @PathVariable int userId){
        return new ResponseEntity<>("Auction joined", HttpStatus.CREATED);
    }

    @PutMapping("/{auctionId}/{offerAmount}")
    public ResponseEntity<String> UpdateOffer(@PathVariable int auctionId, @PathVariable int offerAmount){
        return new ResponseEntity<>("Offer updated", HttpStatus.CREATED);
    }

    @PutMapping("/leave/{auctionId}/{userId}")
    public ResponseEntity<String> LeaveAuction(@PathVariable int auctionId, @PathVariable int userId){
        return new ResponseEntity<>("Left auction", HttpStatus.CREATED);
    }





}
