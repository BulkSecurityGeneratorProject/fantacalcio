package com.fantacalcio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fantacalcio.domain.Giocatore;
import com.fantacalcio.domain.User;
import com.fantacalcio.repository.GiocatoreRepository;
import com.fantacalcio.repository.UserRepository;
import com.fantacalcio.security.SecurityUtils;
import com.fantacalcio.web.rest.util.HeaderUtil;
import com.fantacalcio.web.rest.util.PaginationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Giocatore.
 */
@RestController
@RequestMapping("/api")
public class GiocatoreResource {

    private final Logger log = LoggerFactory.getLogger(GiocatoreResource.class);

    @Inject
    private GiocatoreRepository giocatoreRepository;
    
    @Inject
    private UserRepository userRepository;

    /**
     * POST  /giocatores -> Create a new giocatore.
     */
    @RequestMapping(value = "/giocatores",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Giocatore> createGiocatore(@Valid @RequestBody Giocatore giocatore) throws URISyntaxException {
        log.debug("REST request to save Giocatore : {}", giocatore);
        if (giocatore.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new giocatore cannot already have an ID").body(null);
        }
        
        // Add userID
        User currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin()).get();
        if ( currentUser == null ) {
        	return ResponseEntity.badRequest().header("Failure", "User not logged").body(null);
        }
        giocatore.setUser(currentUser);
        
        Giocatore result = giocatoreRepository.save(giocatore);
        return ResponseEntity.created(new URI("/api/giocatores/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("giocatore", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /giocatores -> Updates an existing giocatore.
     */
    @RequestMapping(value = "/giocatores",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Giocatore> updateGiocatore(@Valid @RequestBody Giocatore giocatore) throws URISyntaxException {
        log.debug("REST request to update Giocatore : {}", giocatore);
        if (giocatore.getId() == null) {
            return createGiocatore(giocatore);
        }
        Giocatore result = giocatoreRepository.save(giocatore);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("giocatore", giocatore.getId().toString()))
                .body(result);
    }

    /**
     * GET  /giocatores -> get all the giocatores.
     */
    @RequestMapping(value = "/giocatores",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Giocatore>> getAllGiocatores(Pageable pageable)
        throws URISyntaxException {
        //Page<Giocatore> page = giocatoreRepository.findAll(pageable);
    	Page<Giocatore> page = giocatoreRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/giocatores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /giocatores/:id -> get the "id" giocatore.
     */
    @RequestMapping(value = "/giocatores/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Giocatore> getGiocatore(@PathVariable Long id) {
        log.debug("REST request to get Giocatore : {}", id);
        return Optional.ofNullable(giocatoreRepository.findOne(id))
            .map(giocatore -> new ResponseEntity<>(
                giocatore,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    /**
     * GET  /giocatores?name=val -> get all the giocatores with name like val
     */
    @RequestMapping(value = "/giocatores", params = "name",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Giocatore>> getAllGiocatores(@RequestParam("name") String name, Pageable pageable)
        throws URISyntaxException {

    	log.debug("REST search Giocatores with name: {}", name);
    	
    	Page<Giocatore> page = giocatoreRepository.searchByNameAndUserIsCurrentUser(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/giocatores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    
    /**
     * DELETE  /giocatores/:id -> delete the "id" giocatore.
     */
    @RequestMapping(value = "/giocatores/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGiocatore(@PathVariable Long id) {
        log.debug("REST request to delete Giocatore : {}", id);
        giocatoreRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("giocatore", id.toString())).build();
    }
}
