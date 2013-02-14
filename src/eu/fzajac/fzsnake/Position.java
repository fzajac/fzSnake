package eu.fzajac.fzsnake;

/**
 * Used for setting and getting position of drawn objects. 
 * @author Filip Zajac (fzajac.eu)
 */
public class Position 
{
	private int x, y; 
	
        /**
         * Constructor setting the initial position
         * @param x X coordinate
         * @param y Y coordinate
         */
	public Position(int x, int y)
	{
		this.x = x; 
		this.y = y; 
	}
	
        /**
         * Sets new position
         * @param x X coordinate
         * @param y Y coordinate
         */
	public void setPosition(int x, int y)
	{
		this.x = x; 
		this.y = y; 
	}
	
        /**
         * Gets X coordinate
         * @return X coordinate
         */
	public int getX()
	{
		return x; 
	}
	
        /**
         * Gets Y coordinate
         * @return Y coordinate
         */
	public int getY()
	{
		return y; 
	}

        /**
         * Gets current position
         * @return  2-element array representing the position (x and y)
         */
	public int[] getPosition() {
		int[] position = {x, y};
		return position; 
	}
}
