package shiyi;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class Tank {
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	public static final int LEVEL = 100 - Integer.parseInt(PropertyMgr.getProperty("level"));
	
	TankClient tc;
	private BloodBar bb = new BloodBar();
	
	private boolean good;
	
	protected boolean live = true;
	
	private int life = 100;
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	protected int x;
	protected int y;
	protected int oldX;
	protected int oldY;
	
	protected static Random r = new Random();
	
	private boolean bL = false, bU = false, bR = false, bD = false;

	protected Direction dir = Direction.STOP; 
	protected Direction ptDir= Direction.D;
	
	protected int step = r.nextInt(12) + 3;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImages = {
			tk.getImage(Tank.class.getClassLoader().getResource("image/tankL.png")),
			tk.getImage(Tank.class.getClassLoader().getResource("image/tankLU.png")),
			tk.getImage(Tank.class.getClassLoader().getResource("image/tankU.png")),
			tk.getImage(Tank.class.getClassLoader().getResource("image/tankRU.png")),
			tk.getImage(Tank.class.getClassLoader().getResource("image/tankR.png")),
			tk.getImage(Tank.class.getClassLoader().getResource("image/tankRD.png")),
			tk.getImage(Tank.class.getClassLoader().getResource("image/tankD.png")),
			tk.getImage(Tank.class.getClassLoader().getResource("image/tankLD.png")),
	};
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		imgs.put("L", tankImages[0]);
		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[6]);
		imgs.put("LD", tankImages[7]);
	}
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.setGood(good);
	}
	
	public Tank(int x, int y, boolean good, Direction dir, TankClient tc){
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		
		if(!live) {
			if(!isGood()) {
				tc.tanks.remove(this);
			}
			return;
		}
		/*
		Color c = g.getColor();
		if(isGood()) g.setColor(Color.red);
		else g.setColor(Color.blue);
		
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		*/
		g.drawImage(imgs.get("L"), x, y, null);
		if(good) bb.draw(g);
		
		switch(ptDir){
		case L:
			//g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT / 2);
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			//g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			//g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y);
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			//g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y);
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			//g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT / 2);
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			//g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT);
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			//g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y + Tank.HEIGHT);
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			//g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT);
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		case STOP:
			//g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT);
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		}
		
		move();
	}
	
	void move(){
		
		this.oldX = x;
		this.oldY = y;
		
		switch(dir){
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += XSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		
		if(this.dir != Direction.STOP){
			this.ptDir = this.dir;
		}
		
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
		
		if(!isGood()){
			Direction[] dirs = Direction.values();
			if(step == 0){
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;
			
			if(r.nextInt(100) > LEVEL)
				this.fire();
		}
	}
	
	public void keyPressed(KeyEvent e){
		
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_F2:
			if(!this.live){
				this.live = true;
				this.life = 100;
			}
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}
	
	void locateDirection(){
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}

	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
		}
		locateDirection();
	}
	
	public Missile fire(){
		if(!live) return null;
		
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, isGood(), ptDir, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir){
		if(!live) return null;
		
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, isGood(), dir, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public void stay(){
		x = oldX;
		y = oldY;
	}
	
	public boolean collidesWithWall(Wall w){
		
		if(this.live && this.getRect().intersects(w.getRect())){
			this.stay();
			return true;
		}
		return false;
	}
	
	public boolean collidesWithTanks(ArrayList<Tank> tanks){
		for(int i = 0; i < tanks.size(); i++){
			Tank t = tanks.get(i);
			if(this != t){
				if(this.live && t.isLive() && this.getRect().intersects(t.getRect())){
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}
	//TODO
	private void superFire(){
		Direction[] dirs = Direction.values();
		for(int i = 0; i < 8; i++){
			fire(dirs[i]);
		}
	}
	
	private class BloodBar{
		public void draw(Graphics g){
			Color c = g.getColor();
			if(life > 60) g.setColor(Color.green);
			else if(life > 20) g.setColor(Color.yellow);
			else g.setColor(Color.red);
			g.drawRect(x, y - 5, WIDTH, 5);
			int w = WIDTH * life / 100;
			
			g.fillRect(x, y - 5, w, 5);
			g.setColor(c);
		}
	}
	
	public boolean eat(Blood b){
		if(this.live && b.isLive() && this.getRect().intersects(b.getRect())){
			this.life = 100;
			b.setLive(false);
			return true;
		}
		return false;
	}
}
