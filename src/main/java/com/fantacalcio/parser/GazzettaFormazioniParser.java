package com.fantacalcio.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fantacalcio.domain.Giocatore;
import com.fantacalcio.domain.Stato;
import com.fantacalcio.repository.GiocatoreRepository;

@Service
@Transactional
public class GazzettaFormazioniParser {
	
	private static Logger log = LoggerFactory.getLogger(GazzettaFormazioniParser.class);
	
	ArrayList<String> titolari = new ArrayList<String>();
	ArrayList<String> panchina = new ArrayList<String>();
	ArrayList<String> infortunati = new ArrayList<String>();
	
	@Inject
    private GiocatoreRepository giocatoreRepository;
	
	@Scheduled(initialDelay=1800000, fixedDelay=3600000)
	public void loadData() throws IOException {
		
		// http://www.fantacalcioservice.it/it/seriea/37787/calciatore/j._murillo/statistiche/
		String url = "http://www.gazzetta.it/Calcio/prob_form";
		
		log.info("Parsing: " + url);
		Document doc = Jsoup.connect(url).get();
		
		// DIV doc.getElementsByClass("stats_calciatore_popup")
		Elements players = doc.getElementsByClass("team-player");
		
		for (Element element : players) {
			titolari.add(element.text());
		}
		
		Elements homeDetails = doc.getElementsByClass("homeDetails");
		
		for (Element element : homeDetails) {
			Elements p = element.getElementsByTag("p");
			if ( p.size() != 0 && p.html().contains("Panchina") ) {
				String row = p.get(0).html();
				log.info("Panchina squadra di casa: " + row);
				String[] list = row.split(";");
				for(int i=1; i< list.length; i++) {
					String[] nameFull = list[i].split(",");
					// Rimuovo eventuali numeri
					String name = nameFull[0].replaceAll("[0-9]","");
					// Rimuovo spazi iniziali e finali
					panchina.add(name.trim());
				}
			}
		}
		
		//TODO: Unificare parsing panchina
		Elements awayDetails = doc.getElementsByClass("awayDetails");
		
		for (Element element : awayDetails) {
			Elements p = element.getElementsByTag("p");
			if ( p.size() != 0 && p.html().contains("Panchina") ) {
				String row = p.get(0).html();
				log.info("Panchina squadra in trasferta: " + row);
				String[] list = row.split(";");
				for(int i=1; i< list.length; i++) {
					String[] nameFull = list[i].split(",");
					// Rimuovo eventuali numeri
					String name = nameFull[0].replaceAll("[0-9]","");
					// Rimuovo spazi iniziali e finali
					panchina.add(name.trim());
				}
			}
		}		
		
		//TODO: Infortunati
		//TODO: Balottaggio
		
		// Update
		List<Giocatore> list = giocatoreRepository.findAll();
		
		for (Giocatore giocatore : list) {
			
			Stato newStatus = getStatus(giocatore.getNome_gazzetta());
			
			giocatore.setStato(newStatus.name());
			giocatore.setUltima_modifica(new DateTime());
			
			giocatoreRepository.saveAndFlush(giocatore);
			log.info("Giocatore " + giocatore.getCodice() + " updated)");
			
			
		}
		
	}
	
	public Stato getStatus(String name) {
		
	
		for (String titolare : titolari) {
			if ( titolare.equalsIgnoreCase(name)  ) {
				return Stato.TITOLARE;
			}
		} 
		for (String inPanchina : panchina) {
			if ( inPanchina.equalsIgnoreCase(name)  ) {
				return Stato.PANCHINA;
			}
		} 
		return Stato.INDISPONIBILE;
	}

}