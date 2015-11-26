package shiyi;
import java.awt.*;

public class Blood {
	
	int x, y;
	TankClient tc;
	
	int step = 0;
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private int[][] pos = {
			{350, 500}, {350, 500},{350, 500}, {350, 500},{350, 500}, {350, 500},{350, 500}, {350, 500},{350, 500}, {350, 500},
			{500, 500}, {500, 500},{500, 500}, {500, 500},{500, 500}, {500, 500},{500, 500}, {500, 500}, {500, 500}, {500, 500}
	};
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image img = tk.getImage(Blood.class.getClassLoader().getResource("image/mintank.png"));

	
	public Blood(){
		x = 200;
		y  = 200;
		//w = h = 20;
	}
	
	public void draw(Graphics g){
		
		if(!live) return;
		//Color c = g.getColor();
		//g.setColor(Color.MAGENTA);
		//g.fillOval(x, y, w, h);
		//g.setColor(c);
		g.drawImage(img, x, y, null);
		//move();
	}
	
	private void move(){
		step++;
		if(step == pos.length){
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, 32, 28);
	}
}
