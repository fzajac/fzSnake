package eu.fzajac.fzsnake;

import java.util.Random;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * Class representing the apple object in the game
 * @author Filip Zajac (fzajac.eu)
 */
public class Apple extends Thread
{
    private Position position = new Position(0, 0); 
    private Random random = new Random(); 
    private boolean draw = true; 

    /**
     * Draws an apple at random position. 
     */
    public Apple()
    {
        randomizePosition(); 
    }

    /**
     * Thread implemented to make the apple drawn, and not drawn next time in a loop. 
     */
    @Override
    public void run()
    {
        while(true)
        {
            if(draw)
                draw = false; 
            else
                draw = true;
            try 
            {
                Thread.sleep(200);
            }
            catch(InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
    }

    /**
     * Changes apple's position to a random position. 
     */
    public void randomizePosition()
    {
        position.setPosition(random.nextInt(31) * 20, random.nextInt(23) * 20);
    }

    /**
     * Gets apple's position
     * @return apple's position
     */
    public int[] getPosition()
    {
        return position.getPosition();
    }
    
    /**
     * Draws the apple
     */
    public void draw()
    {
        if(draw)
        {
            glColor3f(1,0,0); // set colour to #FF0000 (red)
            glBegin(GL_QUADS); 
                glVertex2f(position.getX(), position.getY());
                glVertex2f(position.getX() + 20, position.getY());
                glVertex2f(position.getX() + 20, position.getY() + 20);
                glVertex2f(position.getX(), position.getY() + 20);
            glEnd();
        }
    }
}
