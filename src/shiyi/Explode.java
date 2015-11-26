package shiyi;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/*
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
*/
public class Explode {
	
	int x, y;
	private boolean live = true;
	
	private TankClient tc;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] imgs = {
			tk.getImage(Explode.class.getClassLoader().getResource("image/ex1.png")),
			tk.getImage(Explode.class.getClassLoader().getResource("image/ex2.png")),
			tk.getImage(Explode.class.getClassLoader().getResource("image/ex3.png"))
	};
	int step = 0;
	
	private static boolean init = false;
	
	public Explode(int x, int y, TankClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		
		if(!init){
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}
		
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step >= imgs.length){
			live = false;
			step = 0;
			return;
		}
		
		
		//Color c = g.getColor();
		//g.setColor(Color.orange);
		//g.fillOval(x, y, diameter[step], diameter[step]);
		//g.setColor(c);
		g.drawImage(imgs[step], x, y, null);
		
		step++;
	}
}
