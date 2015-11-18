package com.fantacalcio.domain;

import java.util.List;

public class FantaFormazione {
	
	private List<Giocatore> titolari;
	
	private List<Giocatore> panchina;

	public List<Giocatore> getTitolari() {
		return titolari;
	}

	public void setTitolari(List<Giocatore> titolari) {
		this.titolari = titolari;
	}

	public List<Giocatore> getPanchina() {
		return panchina;
	}

	public void setPanchina(List<Giocatore> panchina) {
		this.panchina = panchina;
	}
	
	
	

}
