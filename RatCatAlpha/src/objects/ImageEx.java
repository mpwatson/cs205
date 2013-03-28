package objects;

import java.awt.Image;

/**
 * Stores an Image alonside coordinates and rotation
 * @author Adam
 *
 */
public class ImageEx {
	public Image image;
	public int x;
	public int y;
	public float rotation;
	
	public ImageEx(Image iImage, int iX, int iY){
		image = iImage;
		x = iX;
		y = iY;
		rotation = 0;
	}
	
	public int width(){
		return image.getWidth(null);
	}
	
	public int height(){
		return image.getHeight(null);
	}
	
	public void move(int addX, int addY){
		x += addX;
		y += addY;
	}
	
	public void reloadImage(Image newImage){
		image = newImage;
	}
}
