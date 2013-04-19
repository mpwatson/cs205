package misc;

/**
 * This represents a 2 dimensional vector
 * @author Adam
 *
 */
public class Vector2D {
    
	public float x;
	
	public float y;
	
	public Vector2D(){
		x = 0;
		y = 0;
	}
	
	public Vector2D(float iX, float iY){
		x = iX;
		y = iY;
	}
	
	/**
	 * Calculate the magnitude of the vector
	 * @return
	 */
	public float getMagnitude(){
		return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	/**
	 * Constrains the vector to specified bounds
	 * This helps adjust the position of visual components to keep them on screen
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 */
	public void setToBounds(float minX, float maxX, float minY, float maxY){
		if(x < minX) x = minX;
		if(x > maxX) x = maxX;
		if(y < minY) y = minY;
		if(y > maxY) y = maxY;
	}
	
	/**
	 * Move the vector
	 * @param addX
	 * @param addY
	 */
	public void move(float addX, float addY){
		x += addX;
		y += addY;
	}
	
	public String toString(){
		return "x:" + x + ", y:" + y;
	}
}
