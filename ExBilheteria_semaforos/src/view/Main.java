package view;

import java.util.concurrent.Semaphore;

import controller.ThreadSistemaCompra;

public class Main {

	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(300);
		Semaphore semaforo2 = new Semaphore(1);
		for(int i=0; i<300; i++){
			ThreadSistemaCompra sts = new ThreadSistemaCompra(i, semaforo, semaforo2);
			sts.start();
		}
		
	}

}
