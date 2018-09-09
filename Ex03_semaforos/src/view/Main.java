package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controller.ThreadCarro;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class Main extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 684, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCarro1 = new JLabel("carro");
		lblCarro1.setBounds(198, 133, 56, 16);
		contentPane.add(lblCarro1);
		
		JLabel lblCarro2 = new JLabel("carro");
		lblCarro2.setBounds(198, 156, 56, 16);
		contentPane.add(lblCarro2);
		
		JLabel lblCarro3 = new JLabel("carro");
		lblCarro3.setBounds(198, 172, 56, 16);
		contentPane.add(lblCarro3);
		
		JLabel lblCarro4 = new JLabel("carro");
		lblCarro4.setBounds(198, 190, 56, 16);
		contentPane.add(lblCarro4);
		
		JLabel lblCarro5 = new JLabel("carro1");
		lblCarro5.setBounds(198, 207, 56, 16);
		contentPane.add(lblCarro5);
		
		JLabel lblCarro6 = new JLabel("carro");
		lblCarro6.setBounds(198, 119, 56, 16);
		contentPane.add(lblCarro6);
		
		JLabel lblCarro7 = new JLabel("carro");
		lblCarro7.setBounds(198, 104, 56, 16);
		contentPane.add(lblCarro7);
		
		int vlMax = 30;//Velocidade maxima de todos os carros
		int numVoltas = 3;//Numero de voltas que os carros irão dar na pista
		int qntdCarros = 14;
		Semaphore semaforoContTemp = new Semaphore(1);
		
		//Posições das curvas da pista e a posiçao inicial do carro
		Rectangle[][] posCurvasVet = new Rectangle[][] {
				{new Rectangle(12, 299, 56, 16), new Rectangle(608, 299, 56, 16), new Rectangle(608, 13, 56, 16), new Rectangle(12, 13, 56, 16), new Rectangle(198, 299, 56, 16)},
				{new Rectangle(40, 279, 56, 16), new Rectangle(549, 279, 56, 16), new Rectangle(549, 32, 56, 16), new Rectangle(40, 32, 56, 16), new Rectangle(198, 279, 56, 16)},
				{new Rectangle(67, 263, 56, 16), new Rectangle(492, 263, 56, 16), new Rectangle(492, 49, 56, 16), new Rectangle(67, 49, 56, 16), new Rectangle(198, 263, 56, 16)},
				{new Rectangle(95, 245, 56, 16), new Rectangle(437, 245, 56, 16), new Rectangle(437, 65, 56, 16), new Rectangle(95, 65, 56, 16), new Rectangle(198, 245, 56, 16)},
				{new Rectangle(122, 228, 56, 16), new Rectangle(381, 228, 56, 16), new Rectangle(381, 83, 56, 16), new Rectangle(122, 83, 56, 16), new Rectangle(198, 228, 56, 16)},
				{new Rectangle(12, 321, 56, 16), new Rectangle(608, 321, 56, 16), new Rectangle(608, 13, 56, 16), new Rectangle(12, 13, 56, 16), new Rectangle(198, 321, 56, 16)},
				{new Rectangle(0, 338, 56, 16), new Rectangle(618, 338, 56, 16), new Rectangle(618, 0, 56, 16), new Rectangle(0, 0, 56, 16), new Rectangle(198, 338, 56, 16)}
		};
		
		//Posições de espera no centro da pista
		Rectangle[] posEspera = new Rectangle[] {
				new Rectangle(198, 133, 56, 16),
				new Rectangle(198, 156, 56, 16),
				new Rectangle(198, 172, 56, 16),
				new Rectangle(198, 190, 56, 16),
				new Rectangle(198, 207, 56, 16),
				new Rectangle(198, 119, 56, 16),
				new Rectangle(198, 104, 56, 16),
		};
		
		//Vetor de labels(Que representam os carros na pista)
		JLabel[] lblCarros = new JLabel[] {lblCarro1, lblCarro2, lblCarro3, lblCarro4, lblCarro5, lblCarro6, lblCarro7};
		
		//Escuderias
		String[] escuderias = new String[] {"Azul", "Vermelho", "Amarelo", "Rosa", "Preto", "Verde","Roxo"};
		
		//Vetor de cores, baseada nas escuderias acima
		Color[] cores = new Color[] {new Color(0,0,255), new Color(255,0,0), new Color(255,255,0), new Color(255,20,147), new Color(0,0,0), new Color(0,100,0), new Color(128,0,128), new Color(255,255,255)};
		
		//Semaforo que controla o uso da pista entre os carros
		Semaphore pista = new Semaphore(5);
		
		//For responsavel por criar os carros
		for(int i=0; i<escuderias.length; i++){
			Semaphore semaforoEquipe = new Semaphore(1);//Semaforo responsavel por controlar qual, dos dois carros da mesma equipe, ira entrar na pista 
			for(int j=1; j<=2; j++) {// Este for é responsavel por criar os dois carros da mesma equipe
				Thread carro = new ThreadCarro(escuderias[i], "Carro " + j, pista, numVoltas, lblCarros[i], posCurvasVet[i], cores[i], semaforoEquipe, posEspera[i], qntdCarros, semaforoContTemp);
				carro.start();
			}
		}
		
		JSeparator separator = new JSeparator();
		separator.setBounds(198, 104, 163, 16);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(194, 104, 14, 126);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(367, 104, 14, 126);
		contentPane.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(198, 228, 163, 16);
		contentPane.add(separator_3);
		
	}
}
