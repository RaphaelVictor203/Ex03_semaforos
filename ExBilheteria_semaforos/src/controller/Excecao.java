package controller;

public class Excecao extends Exception{
	
	private String msg;
	
	public Excecao(String msg) {
		super();
		this.msg = msg;
	}
	
	public void getMsg(){
		System.err.println(msg);
	}
}
