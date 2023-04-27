import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class CharacterObject {
	private double x,y,w,h;
	private int xm,ym;
	private ImageIcon characterImage;
	
	public CharacterObject() {
		x = 0;
		y = 0;
		w = 0;
		h = 0;
		xm = 0;
		ym = 0;
		characterImage = new ImageIcon("");
	}
	
	//Player Ship Constructor
	public CharacterObject (int xv, int yv, int wv, int hv, ImageIcon i) {
		x = xv;
		y = yv;
		characterImage = i;
		xm = 0;
		ym = 0;
		w= wv;
		h = hv;
	}
	
	//Alien Ship Constructor
	public CharacterObject (int xv, int yv, int width, int height, ImageIcon i, int xmv, int ymv) {
		x = xv;
		y = yv;
		w= width;
		h = height;
		characterImage = i;
		xm = xmv;
		ym = ymv;
	}
	
	//Getters and Setters
	
	//Getters
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getW() {
		return w;
	}
	public double getH() {
		return h;
	}
	public int getXm() {
		return xm;
	}
	public int getYm() {
		return ym;
	}
	public ImageIcon getImg() {
		return characterImage;
	}
	
	//Setters
	public void setX(double xv) {
		x = xv;
	}
	public void setY(double yv) {
		y = yv;
	}
	public void setW(double wv) {
		w = wv;
	}
	public void setH(double hv) {
		h = hv;
	}
	public void setXm(int xmv) {
		xm = xmv;
	}
	public void increaseXm(int xmv) {
		if(xm<0) {
			xm -= xmv;
		} else {
			xm += xmv;
		}
	}
	public void setYm(int ymv) {
		ym = ymv;
	}
	public void setImg(ImageIcon newimage) {
		characterImage = newimage;
	}
	public void move(int xmv, int ymv) {
		y += ymv;
		x += xmv;
	}
	public boolean collision(CharacterObject s) {
		Rectangle sp = new Rectangle((int)s.getX(),(int) s.getY(), (int)s.getW(), (int)s.getH());
		Rectangle p = new Rectangle((int)getX(), (int)getY(), (int)getW(), (int)getH());
		
		if(p.intersects(sp)) {
			return true;
		}
		return false;
	}
}
