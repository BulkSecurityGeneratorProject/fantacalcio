package com.fantacalcio.repository;

import com.fantacalcio.domain.Giocatore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Giocatore entity.
 */
public interface GiocatoreRepository extends JpaRepository<Giocatore,Long> {

    @Query("select giocatore from Giocatore giocatore where giocatore.user.login = ?#{principal.username}")
    List<Giocatore> findByUserIsCurrentUser();

    @Query("select giocatore from Giocatore giocatore where giocatore.user.login = ?#{principal.username} order by giocatore.ruolo desc, giocatore.stato desc, giocatore.media_punti desc")
    Page<Giocatore> findByUserIsCurrentUser(Pageable pageable);
    
    @Query("select giocatore from Giocatore giocatore where giocatore.user.login = ?#{principal.username} and giocatore.nome_gazzetta like %?1% order by giocatore.ruolo desc, giocatore.stato desc, giocatore.media_punti desc")
    Page<Giocatore> searchByNameAndUserIsCurrentUser(String name, Pageable pageable);

}
