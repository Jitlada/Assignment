package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Bomb> bomb = new ArrayList<Bomb>();	
	private SpaceShip v;	
	private SpaceShip v2;
	
	private Timer timer;
	
	private long score = 0;
	private long score2 = 0;
	private double difficulty = 0.1; // up Enemy
	private double difficultyBomb = 0.01; // up Bomb

	private int cnt = 0;

	private Alive alive = new Alive(3);
	private Alive alive2 = new Alive(3);
	
	public GameEngine(GamePanel gp, SpaceShip v, SpaceShip v2) {
		this.gp = gp;
		this.v = v;	
		this.v2 = v2;	
		
		gp.sprites.add(v);
		gp.sprites.add(v2);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				processEnemy();
				processBomb();

				processEnemy2();
				processBomb2();
				cnt++;
				if(cnt % 1000 == 0){
					difficulty += 0.01;
				}
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);// random location Enemy
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void generateBomb(){
		Bomb b = new Bomb((int)(Math.random()*390),30);
		gp.sprites.add(b);
		bomb.add(b);
	}

	
	private void processEnemy(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				this.score += 100;
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				if(alive.getHeart() < 0)
				{
					die();
				}
				else {
					alive.dec(); 
					//System.out.println(alive.getHeart());
				}
				return;
			}
		}
	}
	private void processEnemy2(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				this.score2 += 100;
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v2.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				if(alive2.getHeart() < 0)
				{
					die();
				}
				else {
					alive2.dec(); 
					//System.out.println(alive.getHeart());
				}
				return;
			}
		}
	}
	private void processBomb(){
		if(Math.random() < difficultyBomb){
			generateBomb();
		}
		
		Iterator<Bomb> b_iter = bomb.iterator();
		while(b_iter.hasNext()){
			Bomb b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double bvr = v.getRectangle();
		Rectangle2D.Double br;
		for(Bomb b : bomb){
			br = b.getRectangle();
			if(br.intersects(bvr)){
				die();
			}
		}
	}
	private void processBomb2(){
		if(Math.random() < difficultyBomb){
			generateBomb();
		}

		Iterator<Bomb> b_iter = bomb.iterator();
		while(b_iter.hasNext()){
			Bomb b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double bvr = v.getRectangle();
		Rectangle2D.Double br;
		for(Bomb b : bomb){
			br = b.getRectangle();
			if(br.intersects(bvr)){
				die();	
				return;
			}
		}
	}
	
	public void die(){
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v2.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v2.move(1);
			break;
		case KeyEvent.VK_C:
			difficulty += 0.1;
			break;
		
		case KeyEvent.VK_A:
			v.move(-1);
			break;
		case KeyEvent.VK_D:
			v.move(1);
			break;
		}
	}

	public long getScore(){
		return this.score;
	}
	public int getHeart(){
		if(alive.getHeart() < 0)
		{
			return 0;
		}
		else{
			return alive.getHeart();
		}	
	}
	public int getHeart2(){
		if(alive2.getHeart() < 0)
		{
			return 0;
		}
		else{
			return alive2.getHeart();
		}	
	}
	public long getScore2(){
		return this.score2;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
