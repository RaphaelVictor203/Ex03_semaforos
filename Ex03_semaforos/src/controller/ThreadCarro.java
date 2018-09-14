package controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.concurrent.Semaphore;
import javax.swing.JLabel;

public class ThreadCarro extends Thread{
	private JLabel lblCarro;
	public Carro carro;
	private Semaphore pista;
	private Semaphore equipe;
	private int numVoltas;
	private Thread contTempo;
	public boolean voltaFinalizada;
	private Rectangle[] posCurvasVet;
	private Rectangle posEspera;
	private Semaphore semaforoTempos;
	
	public ThreadCarro(String escuderia, String nomeCarro, Semaphore pista, int numVoltas, JLabel lblCarro, Rectangle[] posCurvasVet, Color cor, Semaphore equipe, Rectangle posEspera, int qntdCarros, Semaphore semaforoTempos) {
		this.carro = new Carro(nomeCarro, escuderia);
		this.pista = pista;
		this.numVoltas = numVoltas;
		this.lblCarro = lblCarro;
		this.posCurvasVet = posCurvasVet;
		this.equipe = equipe;
		this.posEspera = posEspera;
		lblCarro.setForeground(cor);
		this.semaforoTempos = semaforoTempos;
		contTempo = new ContadorTempo(numVoltas, this, qntdCarros, semaforoTempos);
	}
	
	public void run() {
		try {
			equipe.acquire();
			pista.acquire();
			lblCarro.setBounds(posCurvasVet[posCurvasVet.length-1]);
			lblCarro.setText(carro.getNome());
			correr();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pista.release();
			equipe.release();
			lblCarro.setBounds(posEspera);
		}
		
	}
	
	public void correr(){
		contTempo.start();
		int cont = 0;
		while(cont < numVoltas) {
			cont += checaPos();
		}		
	}
	
	public void mover(int x, int y) {
		lblCarro.setLocation(lblCarro.getBounds().x + x, lblCarro.getBounds().y + y);
		try {
			sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int checaPos() {
		//int vlAtual = (int)((Math.random()*vlMaxima));
		//int vlAtual = (int)((Math.random()*5)*2);
		int vlAtual = 3;
		if((lblCarro.getBounds().y >= posCurvasVet[4].y) && (lblCarro.getBounds().x < posCurvasVet[4].x)) {
			mover(vlAtual, 0);
			if((lblCarro.getBounds().x >= posCurvasVet[4].x)) {
				voltaFinalizada = false;
				return 1;
			}
		}else
		if((lblCarro.getBounds().x < posCurvasVet[1].x) && (lblCarro.getBounds().y >= posCurvasVet[1].y)) {
			mover(vlAtual, 0);
		}else
		if((lblCarro.getBounds().y > posCurvasVet[2].y) && (lblCarro.getBounds().x >= posCurvasVet[2].x)) {
			mover(0, vlAtual*(-1));
		}else
		if((lblCarro.getBounds().x > posCurvasVet[3].x) && (lblCarro.getBounds().y <= posCurvasVet[3].y)) {
			mover(vlAtual*(-1), 0);
		}else
		if((lblCarro.getBounds().y < posCurvasVet[0].y) && (lblCarro.getBounds().x <= posCurvasVet[0].x)) {
			mover(0, vlAtual);
		}
		return 0;
	}
}
