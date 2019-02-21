package com.example.test.model;

import com.example.test.model.AuthorityRating;
import java.util.Arrays;
import java.util.List;


public class RatingParser  {
    private AuthorityRating authrating;    
    private String typeFHRS;

    public RatingParser() {

    }

    public RatingParser(AuthorityRating authrating, String typeFHRS ) {
        this.authrating = authrating;
        this.typeFHRS = typeFHRS;
    }

    public List<AuthorityRatingItem> getValues() {

        if (authrating.getSchemeType().equals(this.typeFHRS)) {
            return Arrays.asList(
                    new AuthorityRatingItem("5-star", Double.parseDouble(authrating.getValueFormatted("5"))),
                    new AuthorityRatingItem("4-star", Double.parseDouble(authrating.getValueFormatted("4"))),
                    new AuthorityRatingItem("3-star", Double.parseDouble(authrating.getValueFormatted("3"))),
                    new AuthorityRatingItem("2-star", Double.parseDouble(authrating.getValueFormatted("2"))),
                    new AuthorityRatingItem("1-star", Double.parseDouble(authrating.getValueFormatted("1"))),
                    new AuthorityRatingItem("0-star", Double.parseDouble(authrating.getValueFormatted("0"))),
                    new AuthorityRatingItem("Exempt", Double.parseDouble(authrating.getValueFormatted("Exempt"))),
                    new AuthorityRatingItem("Awaiting Inspection", Double.parseDouble(authrating.getValueFormatted("AwaitingInspection")))
                    
            );
        }
        // Scotland Template
        return Arrays.asList(
            new AuthorityRatingItem("Pass", Double.parseDouble(authrating.getValueFormatted("Pass"))),
            new AuthorityRatingItem("Pass and Eat Safe", Double.parseDouble(authrating.getValueFormatted("Pass and Eat Safe"))),
            new AuthorityRatingItem("Exempt", Double.parseDouble(authrating.getValueFormatted("Exempt"))),
            new AuthorityRatingItem("Improvement Required", Double.parseDouble(authrating.getValueFormatted("Improvement Required"))),
            new AuthorityRatingItem("Awaiting Inspection", Double.parseDouble(authrating.getValueFormatted("Awaiting Inspection")))
        );
        
    }
}
