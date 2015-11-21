package shiyi;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TankClient extends Frame{
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Tank myTank = new Tank(50, 50, true, this);
	Tank enemyTank = new Tank(100, 100, false, this);
	ArrayList<Missile> missiles = new ArrayList();
	
	Image offScreenImage = null;
	
	
	@Override
	public void paint(Graphics g) {
		g.drawString("missiles count:" + missiles.size(), 10, 60);
		for(int i = 0; i < missiles.size(); i++){
			Missile m = missiles.get(i);
			//if(!m.isLive()) missiles.remove(m);
			m.draw(g);
		}
		myTank.draw(g);
		enemyTank.draw(g);
	}
	
	public void update(Graphics g){
		
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
			
		}
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.gray);
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	public void lauchFrame(){
		
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
