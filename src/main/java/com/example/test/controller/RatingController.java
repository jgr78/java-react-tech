package com.example.test.controller;

import com.example.test.model.Authority;
import com.example.test.model.AuthorityRating;
import com.example.test.model.AuthorityRatingItem;
import com.example.test.model.FSAAuthority;
import com.example.test.model.FSAAuthorityList;
import com.example.test.model.FSAEstablishment;
import com.example.test.model.FSAEstablishmentList;
import com.example.test.model.FileIOControl;
import com.example.test.model.RatingParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.HashMap;

@Configuration
@PropertySource(value = "file:config.properties", ignoreResourceNotFound=false)

@RestController
@RequestMapping(value = "/api")
public class RatingController  {

    private static final Logger logger = LogManager.getLogger(RatingController.class);
    private final RestTemplate restTemplate;
    private HashMap<Integer, AuthorityRating> ratingList;
    private List<Authority>  authoritiesList;

    // We get the parameters from the config file
    @Value("${food.gov.authorities:http://api.ratings.food.gov.uk/Authorities}")
    private String authoritiesURL;

    @Value("${food.gov.establishment:http://api.ratings.food.gov.uk/Establishments?localAuthorityId}")
    private String establishmentsURL;
    @Value("${food.gov.FHRS:FHRS}")
    private String typeFHRS;
    private boolean cacheEnabled;
    private String fileEstablishments;
    private String fileAuthorities;

    @Autowired
    public RatingController(RestTemplate restTemplate, @Value("${system.file.establishment:/build/tmp/establishments.tmp}")  String fileEstablishments, @Value("${system.file.authorities:/build/tmp/authorities.tmp}") String fileAuthorities, @Value("${system.cache.enabled:true}") boolean cacheEnabled)
    {
        this.restTemplate = restTemplate;
        this.ratingList = new HashMap<Integer, AuthorityRating>();
        this.fileEstablishments = fileEstablishments;
        this.fileAuthorities = fileAuthorities;
        this.cacheEnabled = cacheEnabled;
        
        if (!cacheEnabled) {
            this.ratingList = new HashMap<Integer,AuthorityRating>();
            this.authoritiesList = new ArrayList<>();
            return;
        }


        FileIOControl io = new FileIOControl();
        Object establishments = io.ReadObjectFromFile(this.fileEstablishments);

        if (establishments != null) {
            this.ratingList = (HashMap<Integer,AuthorityRating>) establishments;
        } else {
            this.ratingList = new HashMap<Integer,AuthorityRating>();
        }
        Object authorities = io.ReadObjectFromFile(this.fileAuthorities);
        if (authorities != null) {
            this.authoritiesList = (List<Authority>) authorities;
        } else {
            this.authoritiesList = new ArrayList<>();
        }
    }

    /**
     * Produces a list of authorities, for the select dropdown
     *
     * @return list of authorities
     */
    @RequestMapping(value = "", produces = "application/json")
    public List<Authority> getList() {
        
        List<Authority> authorityList = new ArrayList<>();
        if(cacheEnabled && !authoritiesList.isEmpty()) {
            authorityList = authoritiesList;
        } else {
            authorityList = this.getListFromFoodAgency();
        }
        return authorityList;
    }
    /**
     * Produces a list of authorities, From the FSA using a HTTP request
     *
     * @return list of authorities
     */
    private List<Authority> getListFromFoodAgency() {
        List<Authority> authorityList = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-version", "2");
            HttpEntity<FSAAuthorityList> entity = new HttpEntity<>(headers);

            ResponseEntity<FSAAuthorityList> restRes = restTemplate.exchange(this.authoritiesURL, HttpMethod.GET, entity, FSAAuthorityList.class);

            FSAAuthorityList res = restRes.getBody();
            for (FSAAuthority fsaAuthority : res.getFsaAuthorityList()) {
                authorityList.add(new Authority(fsaAuthority.getId(), fsaAuthority.getName()));
            }

            if (cacheEnabled) {
                FileIOControl io = new FileIOControl();
                io.WriteObjectToFile(authorityList, fileAuthorities);
            }
        } catch(Exception e) {
            // Exception
            logger.warn("The Food Agency is not responding correctly. " + e.getMessage());
        }
        return authorityList;
    }

    /**
     * Produces the ratings for a specific authority for the table
     *
     * @param authorityId the authority to calculate ratings for
     * @return the ratings to display
     */
    @RequestMapping(value = "/{authorityId}", produces = "application/json")
    public List<AuthorityRatingItem> getAuthority(@PathVariable("authorityId") int authorityId) {

        AuthorityRating authrating = new AuthorityRating();
        if(cacheEnabled && ratingList.containsKey(authorityId)) {
            authrating = ratingList.get(authorityId);
        } else {
            authrating = getRatingFromFoodAgency(authorityId);
        }

        RatingParser raParser = new RatingParser(authrating, typeFHRS);
        return raParser.getValues();
    }

    /**
     * Produces an Object with all the rating and %
     *
     * @return AuthorityRating
     */
    private AuthorityRating getRatingFromFoodAgency(int authorityId) {
        AuthorityRating authrating = new AuthorityRating();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-version", "2");
            HttpEntity<FSAEstablishmentList> entity = new HttpEntity<>(headers);

            ResponseEntity<FSAEstablishmentList> restRes = restTemplate.exchange(this.establishmentsURL + "=" + authorityId, HttpMethod.GET, entity, FSAEstablishmentList.class);
            FSAEstablishmentList res = restRes.getBody();

            String schemetype = res.getFSAEstablishmentList().get(0).getSchemeType();
            ListIterator<FSAEstablishment> li = res.getFSAEstablishmentList().listIterator();
            while( li.hasNext() ){
                FSAEstablishment fs = li.next();
                authrating.addNewValue(fs.getValue());
            }
            authrating.createRating();
            authrating.setSchemeType(schemetype);
            ratingList.put(authorityId, authrating);

            if (cacheEnabled) {
                FileIOControl io = new FileIOControl();
                io.WriteObjectToFile(ratingList, this.fileEstablishments);
            }
        } catch(Exception e) {
            // Exception
            logger.warn("The Food Agency is not responding correctly. " + e.getMessage());
        }
        return authrating;
    }

}