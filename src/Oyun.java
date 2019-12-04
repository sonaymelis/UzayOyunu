import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates {

	private int x;
	private int y;

	public Ates(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}

public class Oyun extends JPanel implements KeyListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Timer timer = new Timer(5, this);

	private int gecenSure = 0;
	private int harcananAtes = 0;

	private BufferedImage image;

	private ArrayList<Ates> atesler = new ArrayList<Ates>();

	private int atesdirY = 1;
	private int topX = 0;
	private int topdirX = 2;
	private int uzaygemisiX = 0;
	private int uzaydirX = 20;

	
	public boolean kontrolEt() {
		
		for (Ates ates : atesler) {
			
			if (new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new Rectangle(topX,0,20,20))) {
				return true;
			}
			
		}
		return false;
	}
	
	
	public Oyun() {
		try {
			image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setBackground(Color.BLACK);

		timer.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		gecenSure += 5;

		g.setColor(Color.red);
		g.fillOval(topX, 0, 20, 20);

		g.drawImage(image, uzaygemisiX, 490, image.getWidth() / 10, image.getHeight() / 10, this);
		
		for (Ates ates : atesler) {
			if (ates.getY() < 0) {
				atesler.remove(ates);
			}
		}

		g.setColor(Color.BLUE);
		
		for (Ates ates : atesler) {
			g.fillRect(ates.getX(), ates.getY(), 10, 20);
		}
		
		if (kontrolEt()) {
			timer.stop();
			String message = "Kazandýnýz...\n" +
							 "Harcanan Ateþ : " + harcananAtes +
							 "\nGeçen Süre : " + gecenSure / 1000.0 + " saniye";
			JOptionPane.showMessageDialog(this, message);
			System.exit(0);
		}
		
	}

	@Override
	public void repaint() {
		super.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for (Ates ates : atesler) {
			
			ates.setY(ates.getY() - atesdirY);
			
		}

		topX += topdirX;

		if (topX >= 750) {
			topdirX = -topdirX;
		}
		if (topX <= 0) {
			topdirX = -topdirX;
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int c = e.getKeyCode();

		if (c == KeyEvent.VK_LEFT) {

			if (uzaygemisiX <= 0) {

				uzaygemisiX = 0;

			} else {

				uzaygemisiX -= uzaydirX;

			}

		} else if (c == KeyEvent.VK_RIGHT) {

			if (uzaygemisiX >= 740) {

				uzaygemisiX = 740;

			} else {

				uzaygemisiX += uzaydirX;

			}
		}else if (c == KeyEvent.VK_CONTROL) {

			atesler.add(new Ates(uzaygemisiX+13,475));
			harcananAtes++;
			
			
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
