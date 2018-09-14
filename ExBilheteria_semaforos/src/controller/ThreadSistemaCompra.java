package controller;

import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

public class ThreadSistemaCompra extends Thread{
	private int comprador;
	private static int numTotalIngresso;
	private Semaphore semaforo;
	private Semaphore semaforo2;
	
	public ThreadSistemaCompra(int comprador, Semaphore semaforo, Semaphore semaforo2) {
		this.comprador = comprador;
		this.numTotalIngresso = 100;
		this.semaforo = semaforo;
		this.semaforo2 = semaforo2;
	}
	
	@Override
	public void run() {
		try {
			this.semaforo.acquire();
			logar();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Excecao e) {
			// TODO Auto-generated catch block
			e.getMsg();
		}finally{
			this.semaforo.release();
		}
	}
	
	public void logar() throws Excecao{
		int segsLogin = (int) ((Math.random()*(2000-49))+50);
		if(segsLogin > 1000){
			throw new Excecao("Comprador: " + comprador + " Timeout no login");
		}else{
			try {
				sleep(segsLogin);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Comprador: " + comprador + "Login feito :)");
			
			int numIngressoClt = (int)((Math.random()*4)+1);
			prcCompra(numIngressoClt);
		}
	}
	
//	Processo de compra: Processo que pode demorar de 1 s a 3 s, sendo que, se o tempo passar de
//	2,5s, ao final do tempo de espera da compra, o comprador recebe uma mensagem de final de tempo
//	de sessão e, por estourar o tempo de sessão, não poderá fazer a compra.
	public void prcCompra(int numIngressoClt) throws Excecao{
		int segsCompra = (int) ((Math.random()*(2001))+1000);
		if(segsCompra > 2500){
			throw new Excecao("Comprador: " + comprador + "Timeout na compra, não podera ser realizada");
		}else{
			try {
				sleep(segsCompra);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.semaforo2.acquire();
				checaIngresso(numIngressoClt);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Excecao e) {
				e.getMsg();
			}finally{
				this.semaforo2.release();
			}
			
		}
	}
	
	public void checaIngresso(int numIngressoClt) throws Excecao{
		int numIngressoRest = numTotalIngresso - numIngressoClt;
		if(numIngressoRest >= 0){
			numTotalIngresso -= numIngressoClt;
			System.out.println("--------------------------");
			System.out.println("Comprador " + this.comprador + " comprou " + numIngressoClt + " ingressos. Sobraram " + this.numTotalIngresso + " ingressos.");
			System.out.println("--------------------------");
		}else{
			throw new Excecao("Comprador: " + comprador + " Não foi possivel realizar a compra, não a ingressos suficientes.");
		}
	}
}
