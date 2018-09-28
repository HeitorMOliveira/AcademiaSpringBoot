package br.com.academia.poo.model;

import javax.persistence.Entity;

@Entity
public class Cliente extends AbstractEntity{
	private String plano;

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}
	
	
	
}
