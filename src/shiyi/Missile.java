package shiyi;
import java.awt.*;
import java.util.ArrayList;

public class Missile {
	
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	int x, y;
	Direction dir;
	private boolean good;
	
	private boolean live = true;
	private TankClient tc;
	
	public boolean isLive() {
		return live;
	}
	
    private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image img = tk.getImage(Blood.class.getClassLoader().getResource("image/bullet.png"));


	public Missile(int x, int y, Direction dir) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good, Direction dir, TankClient tc){
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		
		if(!live){
			tc.missiles.remove(this);
			return;
		}
		
		if(good) {
			Color c = g.getColor();
			g.setColor(Color.gray);
			g.fillOval(x, y, WIDTH, HEIGHT);
			g.setColor(c);
		}
		else g.drawImage(img, x, y, 10, 10, null);
		
		
		move();
	}

	private void move() {
		
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
		if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT){
			tc.missiles.remove(this);
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			if(t.isGood()){
				t.setLife(t.getLife() - 20);
				if(t.getLife() <= 0) t.setLive(false);
			}
			else{
				t.setLive(false);
				tc.score += 100;
			}
			this.live = false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(ArrayList<Tank> tanks){
		
		for(int i = 0; i < tanks.size(); i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w){
		if(this.live && this.getRect().intersects(w.getRect())){
			this.live = false;
			return true;
		}
		return false;
	}
}

