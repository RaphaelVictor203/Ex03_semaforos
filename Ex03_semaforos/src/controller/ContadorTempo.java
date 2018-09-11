package controller;

import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;

public class ContadorTempo extends Thread{
	private double[] tempoVolta;
	private static Carro gridLargada[];
	private static int last;
	private int firstPosVet;
	private ThreadCarro carro;
	private Semaphore semaforo;
	
	public ContadorTempo(int qntdVoltas, ThreadCarro carro,int qntdCarros, Semaphore semaforo) {
		this.gridLargada = new Carro[qntdCarros];
		this.tempoVolta = new double[qntdVoltas];
		this.carro = carro;
		this.firstPosVet = 0;
		this.last = -1;
		this.semaforo = semaforo;
	}
	
	public void run() {
		iniciaContagem();
	}
	
	public void iniciaContagem() {
		double tempoInicial;
		double tempoFinal;
		double tempoTotal;
		for(int i=0; i<=tempoVolta.length; i++) {
			tempoInicial = System.nanoTime();
			while(carro.voltaFinalizada) {
				try {
					Thread.sleep(1000);
////					tempo++;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			tempoFinal = System.nanoTime();
			tempoTotal = tempoFinal - tempoInicial;
			tempoTotal /= Math.pow(10, 9);
			carro.voltaFinalizada = true;
			insertTempoVolta(tempoTotal);
		}
		consultaTempoVolta();
		
	}
	
	public void insertTempoVolta(double tempo) {
		if((firstPosVet < tempoVolta.length) && tempo > 0) {
			this.tempoVolta[firstPosVet] = tempo;
			firstPosVet++;
		}
	}
	
	public void consultaTempoVolta() {
		System.out.println();
		System.out.println("\n" + carro.carro.getNome() + " - " + carro.carro.getEscuderia() + ":--------------------------------- \n");
		for(int i=0; i<tempoVolta.length; i++) {
			System.out.println(" Tempo " + (i+1) + "° volta: " + tempoVolta[i]);
		}
		System.out.println("\n---------------------------------------------------");
		try {
			semaforo.acquire();
			tempoVolta = ordenaTemposVolta(tempoVolta);
			carro.carro.setMenorTempo(tempoVolta[0]);
			addCarroGrid(carro.carro);
			if(last == (gridLargada.length-1)){
				gridLargada = ordenaGridLargada(gridLargada);
				mostrarGrid();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			semaforo.release();
		}
	}
	
	public void addCarroGrid(Carro carro) {
		if(last < (gridLargada.length-1)) {
			gridLargada[last+1] = carro;
			last++;
		}
	}
	
	public Carro[] ordenaGridLargada(Carro[] gridLargada) {
		int in, out;
		for(out=(gridLargada.length-1); out>1; out--) {
			for(in=0; in<out; in++) {
				if(gridLargada[in].getMenorTempo() > gridLargada[in+1].getMenorTempo()) {
					Carro temp;
					temp = gridLargada[in];
					gridLargada[in] = gridLargada[in+1];
					gridLargada[in+1] = temp;
				}
			}
		}
		return gridLargada;
	}
	
	public void mostrarGrid() {
		System.out.println("---------- GRID DE LARGADA ----------");
		for(int i=0; i<gridLargada.length; i++) {
			System.out.println("Carro: " + gridLargada[i].getNome() + ", Escuderia: " + gridLargada[i].getEscuderia() + ", Tempo: " + gridLargada[i].getMenorTempo() + "s");
		}
		System.out.println("-------------------------------------");
	}
	
	public double[] ordenaTemposVolta(double[] tempoVolta) {
		int in, out;
		for(out=(tempoVolta.length-1); out>1; out--) {
			for(in=0; in<out; in++) {
				if(tempoVolta[in] > tempoVolta[in+1]) {
					double temp;
					temp = tempoVolta[in];
					tempoVolta[in] = tempoVolta[in+1];
					tempoVolta[in+1] = temp;
				}
			}
		}
		return tempoVolta;
	}
}
