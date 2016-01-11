package com.fantacalcio.parser;

import java.math.BigDecimal;
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
import com.fantacalcio.repository.GiocatoreRepository;

@Service
@Transactional
public class FantaserviceParser {

	private static Logger log = LoggerFactory.getLogger(FantaserviceParser.class);
	
	private static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	
	@Inject
    private GiocatoreRepository giocatoreRepository;
	
	
	//@Scheduled(initialDelay=60000, fixedDelay=86400000)
	@Scheduled(initialDelay=60000, fixedDelay=86400000)
	public void loadData() {
		
		List<Giocatore> players = giocatoreRepository.findAll();
		
		for (Giocatore giocatore : players) {
			
		
			// http://www.fantacalcioservice.it/it/seriea/37787/calciatore/j._murillo/statistiche/
			String url = "http://www.fantacalcioservice.it/it/seriea/" + giocatore.getCodice() +
					"/calciatore/" + giocatore.getNome() + "/statistiche/";
			
			log.info("Parsing: " + url);
			
			
			// DIV doc.getElementsByClass("stats_calciatore_popup")
			int maxRetry = 10;
			int i = 0;
			Document doc = null;
			
			while (i < maxRetry) {
				try {
					doc = Jsoup.connect(url).userAgent(USER_AGENT).timeout(45 * 1000).get();
					if (doc.getElementsByClass("tab_stats").size() != 0) {
						break;
					} else {
						i++;
					}
				} catch (Exception e) {
					e.printStackTrace();
					i++;
					log.error("Retry ("+ i + ") " + e.getMessage());
				}
				
				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

				
			log.info("Parsing data...");
	        
			Element table = doc.getElementsByClass("tab_stats").get(0);
			Elements tds = table.getElementsByTag("td");
			
			int idx = 0;
			String[] statistics = new String[26];
			for (Element element : tds) {
				//System.out.println(idx + " td: " + element.text());
				statistics[idx++] = element.text();
			}
			
			log.info("Got statistics...");
			
			giocatore.setRuolo(statistics[15]);
			if ( !statistics[16].equals("") ) {
				giocatore.setPresenze(Integer.parseInt(statistics[16]));
			}
			if ( !statistics[17].equals("") ) {
				giocatore.setCambio_in(Integer.parseInt(statistics[17]));
			}
			if ( !statistics[18].equals("") ) {
				giocatore.setCambio_out(Integer.parseInt(statistics[18]));
			}
			if ( !statistics[19].equals("") ) {
				giocatore.setGol(Integer.parseInt(statistics[19]));
			}
			if ( !statistics[21].equals("") ) {
				giocatore.setAmmonizioni(Integer.parseInt(statistics[21]));
			}
			if ( !statistics[22].equals("") ) {
				giocatore.setEspulsioni(Integer.parseInt(statistics[22]));
			}
			if ( !statistics[24].equals("") ) {
				giocatore.setMedia_punti(new BigDecimal(Double.parseDouble(statistics[24].replace(",","."))));
			}

			giocatore.setUltima_modifica(new DateTime());
			
			giocatoreRepository.saveAndFlush(giocatore);
			log.info("Giocatore " + giocatore.getCodice() + " updated)");
				
		}
		
	}
	
	public void close() {
		log.info("Shutdown");
	}


}