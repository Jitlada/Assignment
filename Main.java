package f2.spw;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.Color;

public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 650);
		frame.getContentPane().setLayout(new BorderLayout());
		
		SpaceShip player2 = new SpaceShip(280, 550, 20, 20, Color.green);
		SpaceShip player1 = new SpaceShip(70, 550, 20, 20, Color.orange);
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, player1, player2);
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		
		engine.start();
	}
}
