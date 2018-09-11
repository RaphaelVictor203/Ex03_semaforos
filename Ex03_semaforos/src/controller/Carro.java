package controller;

public class Carro {
	private String nome, escuderia;
	private double menorTempo;
	
	public Carro(String nome, String escuderia) {
		this.nome = nome;
		this.escuderia = escuderia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEscuderia() {
		return escuderia;
	}

	public void setEscuderia(String escuderia) {
		this.escuderia = escuderia;
	}

	public double getMenorTempo() {
		return menorTempo;
	}

	public void setMenorTempo(double menorTempo) {
		this.menorTempo = menorTempo;
	}
	
	
}
