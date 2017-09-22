package com.fantacalcio.parser;

import java.io.IOException;
import java.text.Normalizer;
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
public class SkyFormazioniParser {
	
	private static Logger log = LoggerFactory.getLogger(SkyFormazioniParser.class);
	
	ArrayList<String> titolari = new ArrayList<String>();
	ArrayList<String> panchina = new ArrayList<String>();
	ArrayList<String> infortunati = new ArrayList<String>();
	
	@Inject
    private GiocatoreRepository giocatoreRepository;
	
	//@Scheduled(initialDelay=120000, fixedDelay=3600000)
	@Scheduled(initialDelay=60000, fixedDelay=3600000)
	public void loadData() throws IOException {
		
		// clear lists
		titolari.clear();
		panchina.clear();
		infortunati.clear();
		
		// http://www.fantacalcioservice.it/it/seriea/37787/calciatore/j._murillo/statistiche/
		String url = "http://sport.sky.it/calcio/serie-a/probabili-formazioni/";
		
		log.info("Parsing: " + url);
		Document doc = Jsoup.connect(url).get();
		
		// DIV doc.getElementsByClass("stats_calciatore_popup")
		Elements players = doc.getElementsByClass("player");
		
		for (Element element : players) {
			titolari.add(normalizeName(element.getElementsByClass("name").text()));
		}
		
		/*
		<dl class="otherlist">
		<dt>Allenatore:</dt><dd>Donadoni</dd>
		<dt>Panchina:</dt><dd>Da Costa, Sarr, Oikonomou, Tabacchi, Mbaye, Di Francesco, Pulgar, Valencia, Rizzo, Petkovic, Sadiq</dd>
		<dt>Squalificati:</dt><dd>Viviani e Krafth</dd>
		<dt>Indisponibili:</dt><dd>Helander, Taider</dd>
		</dl>
		*/
		
		Elements otherList = doc.getElementsByClass("otherlist");
		
		
		for (Element element : otherList) {
			
			//log.info("OTHER: " + element.getElementsByTag("dd").text());			
			Elements otherElements = element.getElementsByTag("dd");			
			String[] panchinaList = otherElements.get(1).text().replaceAll(" e ",",").split(",");
			for (int i=0; i< panchinaList.length; i++) {
				panchina.add(normalizeName(panchinaList[i].trim()));
			}
			
			String[] infortunatiList = otherElements.get(2).text().replaceAll(" e ",",").split(",");
			for (int i=0; i< infortunatiList.length; i++) {
				infortunati.add(normalizeName(infortunatiList[i].trim()));
			}
			
			String[] indispList = otherElements.get(3).text().replaceAll(" e ",",").split(",");
			for (int i=0; i< indispList.length; i++) {
				infortunati.add(normalizeName(indispList[i].trim()));
			}
			
		}
		
		
	
		for (String titolare : titolari) {
			//log.info("TITOLARE: " + titolare );
		}
		for (String panchinaro : panchina) {
			//log.info("PANCHINA: " + panchinaro );
		}
		for (String infortunato : infortunati) {
			//log.info("INFORTUNATO: " + infortunato );
		}
	
		
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
	
	private String normalizeName(String name) {
		
		String s = Normalizer.normalize(name, Normalizer.Form.NFD);
		s = s.replaceAll("[^\\p{ASCII}]", "");
		//log.info("[ " + name + "->" + s + "]");
		
		return s;
	}
	
	
	public Stato getStatus(String name) {
		
		String nameNormalized = normalizeName(name);
		
	
		for (String titolare : titolari) {
			if ( titolare.equalsIgnoreCase(nameNormalized)  ) {
				return Stato.TITOLARE;
			}
		} 
		for (String inPanchina : panchina) {
			if ( inPanchina.equalsIgnoreCase(nameNormalized)  ) {
				return Stato.PANCHINA;
			}
		}
		
		for (String infortunato : infortunati) {
			if ( infortunato.equalsIgnoreCase(nameNormalized)  ) {
				return Stato.INDISPONIBILE;
			}
		}		
		
		log.warn("Stato per: [" + nameNormalized + "] non trovato");
		
		
		return Stato.INDISPONIBILE;
	}

}