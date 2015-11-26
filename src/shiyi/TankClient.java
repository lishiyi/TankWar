package shiyi;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class TankClient extends Frame{
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	public int score = 0;
	
	Tank myTank = new Tank(300, 150, true, Direction.STOP, this);
	Wall[] walls = {
		new Wall(100, 200, 20, 150, this),
		new Wall(300, 100, 300, 20, this),
		new Wall(300, 400, 150, 20, this)
	};
	River river = new River(550, 200, 40, 200, this);
	
	ArrayList<Explode> explodes = new ArrayList<Explode>();
	ArrayList<Missile> missiles = new ArrayList<Missile>();
	ArrayList<Tank> tanks = new ArrayList<Tank>();
	Image offScreenImage = null;
	
	Blood b = new Blood();
	
	@Override
	public void paint(Graphics g) {

		//g.drawString("Missiles count:" + missiles.size(), 10, 50);
		//g.drawString("Explodes count:" + explodes.size(), 10, 70);
		//g.drawString("Tanks    count:" + tanks.size(), 10, 90);
		//g.drawString("Tank      life:" + myTank.getLife(), 10, 110);
		//g.drawString("Level:" + (100 - Tank.LEVEL), 10, 130);
		
		int reProduceTankCount = Integer.parseInt(PropertyMgr.getProperty("reProduceTankCount")); 
		if(tanks.size() <= 0){
			for(int i = 0; i < reProduceTankCount; i++){	
				tanks.add(new Tank(50 + 60 * (i + 1), 50, false, Direction.D, this));
				tanks.add(new Tank(50 + 740 - 60 * (i + 1), 50, false, Direction.D, this));
				tanks.add(new Tank(50 + 60 * (i + 1), 500, false, Direction.D, this));
				tanks.add(new Tank(50 + 740 - 60 * (i + 1), 500, false, Direction.D, this));
			}
		}
		//tanks.add(new BossTank(150, 150, false, Direction.D, this));
		
		river.draw(g);
		for(int i=0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			for(Wall w : walls){
				t.collidesWithWall(w);
			}
			//River
			t.collidesWithWall(river);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}

		for(int i = 0; i < explodes.size(); i++){
			Explode e = explodes.get(i);
			e.draw(g);
		}

		
		myTank.collidesWithTanks(tanks);
		for(Wall w : walls){
			myTank.collidesWithWall(w);
		}
		//River
		myTank.collidesWithWall(river);
		myTank.draw(g);
		myTank.eat(b);
		for(Wall w : walls){
			w.draw(g);
		}
		
		b.draw(g);
		
		for(int i = 0; i < missiles.size(); i++){
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			for(Wall w : walls){
				m.hitWall(w);
			}
			m.draw(g);
		}
		
		g.drawString("Score: " + this.score, 10, 50);
		g.drawString("Life:  " +  myTank.getLife(), 10, 70);
		g.drawString("Enemy Tanks: " + tanks.size(), 10, 90);
	}
	
	public void update(Graphics g){
		
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
			
		}
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.white);
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	/**
	 * 本方法显示坦克主窗口
	 * 
	 */
	public void lauchFrame(){
		
		int initTankCount = Integer.parseInt(PropertyMgr.getProperty("initTankCount")); 
		for(int i = 0; i < initTankCount; i++){
			
			tanks.add(new Tank(50 + 60 * (i + 1), 50, false, Direction.D, this));
			tanks.add(new Tank(50 + 740 - 60 * (i + 1), 50, false, Direction.D, this));
			tanks.add(new Tank(50 + 60 * (i + 1), 500, false, Direction.D, this));
			tanks.add(new Tank(50 + 740 - 60 * (i + 1), 500, false, Direction.D, this));
		}
		//Boss
		//tanks.add(new BossTank(600, 400, false, Direction.D, this));
		
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("Tank War");
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.gray);
		
		this.addKeyListener(new KeyMonitor());
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	public static void main(String[] args) {
		
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}
	
	private class PaintThread implements Runnable{
		
		public void run(){
			while(true){
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			
			myTank.keyPressed(e);
		}
		@Override
		public void keyReleased(KeyEvent e){
			myTank.keyReleased(e);
		}
	}
	
}
