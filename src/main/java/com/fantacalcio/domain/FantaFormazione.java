package com.fantacalcio.domain;

import java.util.List;

public class FantaFormazione {
	
	private List<Giocatore> titolari;
	
	private List<Giocatore> panchina;
	
	private List<Giocatore> portieri;
	
	private List<Giocatore> difensori;
	
	private List<Giocatore> centrocampisti;
	
	private List<Giocatore> attaccanti;

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

	public List<Giocatore> getPortieri() {
		return portieri;
	}

	public void setPortieri(List<Giocatore> portieri) {
		this.portieri = portieri;
	}

	public List<Giocatore> getDifensori() {
		return difensori;
	}

	public void setDifensori(List<Giocatore> difensori) {
		this.difensori = difensori;
	}

	public List<Giocatore> getCentrocampisti() {
		return centrocampisti;
	}

	public void setCentrocampisti(List<Giocatore> centrocampisti) {
		this.centrocampisti = centrocampisti;
	}

	public List<Giocatore> getAttaccanti() {
		return attaccanti;
	}

	public void setAttaccanti(List<Giocatore> attaccanti) {
		this.attaccanti = attaccanti;
	}
		
	
	

}
