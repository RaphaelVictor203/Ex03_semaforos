package controller;

import java.util.concurrent.Semaphore;

public class ContadorTempo extends Thread{
	private int[] tempoVolta;
	private static Carro gridLargada[];
	private static int last;
	private int firstPosVet;
	private int tempo;
	private ThreadCarro carro;
	private Semaphore semaforo;
	
	public ContadorTempo(int qntdVoltas, ThreadCarro carro,int qntdCarros, Semaphore semaforo) {
		this.gridLargada = new Carro[qntdCarros];
		this.tempoVolta = new int[qntdVoltas];
		this.carro = carro;
		this.firstPosVet = 0;
		this.tempo = 0;
		this.last = -1;
		this.semaforo = semaforo;
	}
	
	public void run() {
		iniciaContagem();
	}
	
	public void iniciaContagem() {
		for(int i=0; i<=tempoVolta.length; i++) {
			while(carro.voltaFinalizada) {
				try {
					Thread.sleep(1000);
					tempo++;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			carro.voltaFinalizada = true;
			insertTempoVolta();
		}
		consultaTempoVolta();
		
	}
	
	public void insertTempoVolta() {
		if((firstPosVet < tempoVolta.length) && tempo > 0) {
			this.tempoVolta[firstPosVet] = tempo;
			tempo = 0;
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
		if(last == (gridLargada.length-1)){
			gridLargada = ordenaGridLargada(gridLargada);
			mostrarGrid();
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
	
	public int[] ordenaTemposVolta(int[] tempoVolta) {
		int in, out;
		for(out=(tempoVolta.length-1); out>1; out--) {
			for(in=0; in<out; in++) {
				if(tempoVolta[in] > tempoVolta[in+1]) {
					int temp;
					temp = tempoVolta[in];
					tempoVolta[in] = tempoVolta[in+1];
					tempoVolta[in+1] = temp;
				}
			}
		}
		return tempoVolta;
	}
}
