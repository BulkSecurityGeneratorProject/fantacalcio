package com.fantacalcio.web.rest;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.fantacalcio.domain.FantaFormazione;
import com.fantacalcio.domain.Giocatore;
import com.fantacalcio.repository.GiocatoreRepository;
import com.fantacalcio.repository.UserRepository;
import com.fantacalcio.service.GeneraFormazioneService;

@RestController
@RequestMapping("/api")
public class FantaFormazioneResource {
	
	private final Logger log = LoggerFactory.getLogger(FantaFormazioneResource.class);
	
    @Inject
    private GiocatoreRepository giocatoreRepository;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private GeneraFormazioneService generaFormazioneService;

	
    /**
     * GET  /fantaformazione/:id -> get the "id" fantaformazione.
     */
    @RequestMapping(value = "/fantaformazione/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FantaFormazione> getFantaFormazione(@PathVariable Long id) {
        log.debug("REST request to get FantaFormazione : {}", id);
        
        FantaFormazione fantaFormazione = null;
        List<Giocatore> players = giocatoreRepository.findByUserIsCurrentUser();
        
        log.debug("Found players : " + players);
        
        
        if ( players != null ) {
        	fantaFormazione = generaFormazioneService.genera(players, id);
        }
        
        return Optional.ofNullable(fantaFormazione)
            .map(ff -> new ResponseEntity<>(ff, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
