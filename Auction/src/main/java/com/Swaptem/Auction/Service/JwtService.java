package com.Swaptem.Auction.Service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "y833hfefc3v8f3mvfhw89fmhahfa89fhvah88a93hvfa8h9hw8hfaw8vf83awhv98fawn3nrn3nf3iwf3fw3nfnf90j4g40jg93jg9j3g34fj4g39gh934g"; // Replace with your own secret key

    public int getUserIdFromJwtToken(String token) {
        // Workaround for REST API testing
        int userId = 0;
        if(token.equals("RestAPIUserId1")){
            userId = 1;
            return userId;
        } else if(token.equals("RestAPIUserId2")) {
            userId = 2;
            return userId;
        }else if(token.equals("RestAPIUserId3")) {
            userId = 3;
            return userId;
        } else if(token.equals("RestAPIUserId4")) {
            userId = 4;
            return userId;
        } else if(token.equals("RestAPIUserId5")) {
            userId = 5;
            return userId;
        }else if(token.equals("RestAPIUserId6")) {
            userId = 6;
            return userId;
        }


        userId = Integer.parseInt(Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
        return userId;
    }


}
