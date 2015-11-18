package com.fantacalcio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fantacalcio.domain.Formazione;
import com.fantacalcio.repository.FormazioneRepository;
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
 * REST controller for managing Formazione.
 */
@RestController
@RequestMapping("/api")
public class FormazioneResource {

    private final Logger log = LoggerFactory.getLogger(FormazioneResource.class);

    @Inject
    private FormazioneRepository formazioneRepository;

    /**
     * POST  /formaziones -> Create a new formazione.
     */
    @RequestMapping(value = "/formaziones",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Formazione> createFormazione(@Valid @RequestBody Formazione formazione) throws URISyntaxException {
        log.debug("REST request to save Formazione : {}", formazione);
        if (formazione.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new formazione cannot already have an ID").body(null);
        }
        Formazione result = formazioneRepository.save(formazione);
        return ResponseEntity.created(new URI("/api/formaziones/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("formazione", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /formaziones -> Updates an existing formazione.
     */
    @RequestMapping(value = "/formaziones",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Formazione> updateFormazione(@Valid @RequestBody Formazione formazione) throws URISyntaxException {
        log.debug("REST request to update Formazione : {}", formazione);
        if (formazione.getId() == null) {
            return createFormazione(formazione);
        }
        Formazione result = formazioneRepository.save(formazione);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("formazione", formazione.getId().toString()))
                .body(result);
    }

    /**
     * GET  /formaziones -> get all the formaziones.
     */
    @RequestMapping(value = "/formaziones",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Formazione>> getAllFormaziones(Pageable pageable)
        throws URISyntaxException {
        Page<Formazione> page = formazioneRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/formaziones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /formaziones/:id -> get the "id" formazione.
     */
    @RequestMapping(value = "/formaziones/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Formazione> getFormazione(@PathVariable Long id) {
        log.debug("REST request to get Formazione : {}", id);
        return Optional.ofNullable(formazioneRepository.findOne(id))
            .map(formazione -> new ResponseEntity<>(
                formazione,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /formaziones/:id -> delete the "id" formazione.
     */
    @RequestMapping(value = "/formaziones/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFormazione(@PathVariable Long id) {
        log.debug("REST request to delete Formazione : {}", id);
        formazioneRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("formazione", id.toString())).build();
    }
}
