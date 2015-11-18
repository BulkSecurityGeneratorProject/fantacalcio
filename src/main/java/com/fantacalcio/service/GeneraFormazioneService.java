package com.fantacalcio.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fantacalcio.domain.FantaFormazione;
import com.fantacalcio.domain.Giocatore;
import com.fantacalcio.domain.Ruolo;

@Service
public class GeneraFormazioneService {

    private final Logger log = LoggerFactory.getLogger(GeneraFormazioneService.class);
    
    
    public FantaFormazione genera(List<Giocatore> lista, long codiceModulo) {
    	
    	FantaFormazione fantaFormazione = new FantaFormazione();
    	
    	if ( lista == null || lista.size() == 0) {
    		// Return empty lists
    		fantaFormazione.setTitolari(new ArrayList<Giocatore>());
    		fantaFormazione.setPanchina(new ArrayList<Giocatore>());
    		return fantaFormazione;
    	}
    	
		Hashtable<Ruolo, List<Giocatore>> listaPerRuolo = new Hashtable<Ruolo, List<Giocatore>>() {{
		    put(Ruolo.P, new ArrayList<Giocatore>());
		    put(Ruolo.D, new ArrayList<Giocatore>());
		    put(Ruolo.C, new ArrayList<Giocatore>());
		    put(Ruolo.A, new ArrayList<Giocatore>());
		    
		}};
		
		// Ordinamenti
		Comparator<Giocatore> byRuolo = (e1, e2) -> e1.getRuolo().compareTo(e2.getRuolo());
	    Comparator<Giocatore> byStato = (e1, e2) -> e2.getStato().compareTo(e1.getStato());
	    Comparator<Giocatore> byMedia = (e1, e2) -> e2.getMedia_punti().compareTo(e1.getMedia_punti());

    	 lista.stream()
			.sorted(byRuolo.thenComparing(byStato).thenComparing(byMedia))
			.forEach(t -> listaPerRuolo.get(Ruolo.valueOf(t.getRuolo())).add(t));
    	 
    	 
 		Ruolo[] ruoli = {Ruolo.P, Ruolo.D, Ruolo.C, Ruolo.A};
 		int[] riserve = {1,2,2,2};
 		
 		int[] modulo = new int[4];
 		modulo[0] = 1; //Portiere
 		
 		String moduloStr = "" + codiceModulo;
 		for ( int i=0; i<3; i++) {
 			modulo[i+1] = Integer.parseInt(moduloStr.substring(i,i+1));
 		}
 		
 		log.info("Use modulo: " + modulo[0] + "-" + modulo[1] + "-" + modulo[2] + "-" + modulo[3]);
/*
 		for ( int i=0; i < ruoli.length; i++) {
 			for ( Giocatore g : listaPerRuolo.get(ruoli[i]) ) {
 				log.info(ruoli[i] + ":" + g.getNome());
 			}
 		}
*/ 		
 		ArrayList<Giocatore> t = new ArrayList<Giocatore>();
 		for ( int i=0; i < ruoli.length; i++) {
 			for ( int j=0; j < modulo[i]; j++) {
 				t.add(listaPerRuolo.get(ruoli[i]).get(j));
 			}
 		}
 		
 		ArrayList<Giocatore> p = new ArrayList<Giocatore>();
 		for ( int i=0; i < ruoli.length; i++) {
 			for ( int j=0; j < riserve[i]; j++) {
 				p.add(listaPerRuolo.get(ruoli[i]).get(modulo[i] + j));
 			}
 		}
    	
 		fantaFormazione.setTitolari(t);
 		fantaFormazione.setPanchina(p);
 		
    	return fantaFormazione;
    	
    }

}
