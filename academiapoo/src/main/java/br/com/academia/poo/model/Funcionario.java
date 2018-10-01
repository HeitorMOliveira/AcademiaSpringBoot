package br.com.academia.poo.model;

import javax.persistence.Entity;

@Entity
public class Funcionario extends AbstractEntity{
	
	private double salario;
	private String cargaHoraria;
	
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public String getCargaHoraria() {
		return cargaHoraria;
	}
	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
	
}
