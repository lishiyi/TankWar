package shiyi;
import java.awt.*;
import java.util.Random;

public class Blood {
	
	int x, y;
	TankClient tc;
	boolean star;
	public boolean isStar() {
		return star;
	}

	public void setStar(boolean star) {
		this.star = star;
	}
	//int step = 0;
	private boolean live = true;
	
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
/*
	private int[][] pos = {
			{350, 500}, {350, 500},{350, 500}, {350, 500},{350, 500}, {350, 500},{350, 500}, {350, 500},{350, 500}, {350, 500},
			{500, 500}, {500, 500},{500, 500}, {500, 500},{500, 500}, {500, 500},{500, 500}, {500, 500}, {500, 500}, {500, 500}
	};
	*/
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image img = tk.getImage(Blood.class.getClassLoader().getResource("image/mintank.png"));
	private static Image imgS = tk.getImage(Blood.class.getClassLoader().getResource("image/star.png"));
	private static Random r = new Random();
	
	public Blood(){
		
		x = r.nextInt(700) + 50;
		y = r.nextInt(500) + 50;
		star = r.nextBoolean();
	}
	
	public void draw(Graphics g){
		
		if(!live){
			if(r.nextInt(500)> 498){
				live = true;
				x = r.nextInt(700) + 50;
				y = r.nextInt(500) + 50;
			}
			return;
		}
		//Color c = g.getColor();
		//g.setColor(Color.MAGENTA);
		//g.fillOval(x, y, w, h);
		//g.setColor(c);
		if(star){
			g.drawImage(imgS, x, y, null);
		}
		else{
			g.drawImage(img, x, y, null);
		}
		//move();
	}
	/*
	private void move(){
		step++;
		if(step == pos.length){
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}
	*/
	public Rectangle getRect(){
		return new Rectangle(x, y, 32, 28);
	}
	
	public boolean collidesWithWall(Wall w){
		
		if(this.live && this.getRect().intersects(w.getRect())){
			this.live = false;
		}
		return false;
	}
}
