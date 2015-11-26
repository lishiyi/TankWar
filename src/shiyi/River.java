package shiyi;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class River extends Wall{
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image img = tk.getImage(Blood.class.getClassLoader().getResource("image/river.png"));
	
	public River(int x, int y, int w, int h, TankClient tc) {
		super(x, y, w, h, tc);
	}
	
	@Override
	public void draw(Graphics g){
		for(int i = 0; i < 5; i++){
			g.drawImage(img, x, y + 40 * i, 40, 40, null);
		}
	}
}
