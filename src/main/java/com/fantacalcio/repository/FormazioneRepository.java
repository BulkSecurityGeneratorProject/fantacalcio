package com.fantacalcio.repository;

import com.fantacalcio.domain.Formazione;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Formazione entity.
 */
public interface FormazioneRepository extends JpaRepository<Formazione,Long> {

}
