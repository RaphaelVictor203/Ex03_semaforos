package controller;

import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;

public class ContadorTempo extends Thread{
	private int[] tempoVolta;
	private static Carro gridLargada[];
	private static int last;
	private ThreadCarro carro;
	private static Semaphore sem;
	
	public ContadorTempo(int qntdVoltas, ThreadCarro carro,int qntdCarros) {
		this.gridLargada = new Carro[qntdCarros];
		this.tempoVolta = new int[qntdVoltas];
		this.carro = carro;
		this.last = -1;
		this.sem = new Semaphore(1);
	}
	
	public void run() {
		iniciaContagem();
	}
	
	public void iniciaContagem() {
		int tempo;
		int last = -1;
		for(int i=0; i<=tempoVolta.length; i++) {
			tempo = 0;
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
			last = insertTempoVolta(tempo, last);
		}		
		//consultaTempoVolta();
		tempoVolta = ordenaTemposVolta(tempoVolta);
		carro.carro.setMenorTempo(tempoVolta[0]);
		addCarroGrid(carro.carro);
		System.out.println("last = " + this.last + ", tamanho grid = " + (gridLargada.length-1));
		mostrarGrid();
	}
	
	public int insertTempoVolta(int tempo, int last) {
		if((last < (tempoVolta.length-1)) && tempo > 0) {
			this.tempoVolta[last+1] = tempo;
			last++;
		}
		return last;
	}
	
	public void consultaTempoVolta() {
		System.out.println();
		System.out.println("\n" + carro.carro.getNome() + " - " + carro.carro.getEscuderia() + ":--------------------------------- \n");
		for(int i=0; i<tempoVolta.length; i++) {
			System.out.println(" Tempo " + (i+1) + "° volta: " + tempoVolta[i]);
		}
		System.out.println("\n---------------------------------------------------");
	}
	
	public void addCarroGrid(Carro carro) {
		try {
			sem.acquire();
			if(last < (gridLargada.length-1)) {
				gridLargada[last+1] = carro;
				last++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			sem.release();
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
		if(last == (gridLargada.length-1)){
			gridLargada = ordenaGridLargada(gridLargada);
			System.out.println("---------- GRID DE LARGADA ----------");
			for(int i=0; i<gridLargada.length; i++) {
				System.out.println((i+1) + "° Carro: " + gridLargada[i].getNome() + ", Escuderia: " + gridLargada[i].getEscuderia() + ", Tempo: " + gridLargada[i].getMenorTempo() + "s");
			}
			System.out.println("-------------------------------------");
		}
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
