package eu.fzajac.fzsnake;

import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * Class representing snake in the game
 * @author FZajac
 */
public class Snake extends Thread
{
    private final static int INITIAL_SPEED = 6; 
    private final static int INITIAL_INTERVAL = 100; 
    private final static int INITIAL_DIRECTION = 0;
    private final static int INITIAL_X = 320;
    private final static int INITIAL_Y = 240;
    
    private int x, y, direction, nextDirection; 
    private ArrayList<Position> position = new ArrayList<Position>();
    private boolean pause = false; 
    private boolean collision = false; 
    private int nextSpeed; 
    private int interval; 
    
    /**
     * Draws the snake consisting of 3 parts, with initial position, speed, interval and direction
     */
    public Snake()
    {
        position.add(new Position(INITIAL_X, INITIAL_Y));
        position.add(new Position(INITIAL_X-20, INITIAL_Y));
        position.add(new Position(INITIAL_X-40, INITIAL_Y));
        
        nextSpeed = INITIAL_SPEED;
        interval = INITIAL_INTERVAL;
        direction = INITIAL_DIRECTION; 
        nextDirection = INITIAL_DIRECTION;

        x = INITIAL_X; 
        y = INITIAL_Y; 
    }

    @Override
    public void run()
    {
        while(true)
        {
            if(!pause)
            {
                direction = nextDirection;

                // change the position to the other end if outside of the area
                if(direction == 0 && x == 620)
                    x = -20; 
                else if(direction == 2 && x == 0)
                    x = 640;
                else if(direction == 1 && y == 460)
                    y = -20; 
                else if(direction == 3 && y == 0)
                    y = 480;
                
                // advance the position of parts of the snake 
                for(int i = position.size() - 1; i > 0; i--)
                    position.set(i, position.get(i-1));

                if(direction == 0)
                {
                    x += 20; 
                    position.set(0, new Position(x, y));
                }
                else if(direction == 1)
                {
                    y += 20; 
                    position.set(0, new Position(x, y));
                }
                else if(direction == 2)
                {
                    x -= 20; 
                    position.set(0, new Position(x, y));
                }
                else if(direction == 3)
                {
                    y -= 20; 
                    position.set(0, new Position(x, y));
                }

                // check if head collides with snake
                for(int i = 1; i < position.size(); i++)
                {
                    if(position.get(0).getX() == position.get(i).getX() && position.get(0).getY() == position.get(i).getY())
                    {
                        collision = true; 
                    }
                }
            }

            try 
            {
                Thread.sleep(interval);
            }
            catch(InterruptedException ie)
            {
                System.err.println(ie);
            }
        }
    }

    /**
     * Sets direction of the snake
     * @param direction new direction
     */
    public void setDirection(int direction)
    {
        if(this.direction != direction + 2 && this.direction != direction -2)
            this.nextDirection = direction; 
    }

    /**
     * Gets position of the snake
     * @return position of the snake`s head
     */
    public int[] getPosition()
    {
        return position.get(0).getPosition(); 
    }

    /**
     * Checks if snake collides with itself
     * @return whether the snake collides with itself
     */
    public boolean collides()
    {
        return collision; 
    }

    /**
     * Restarts the snake (starting position, initial length, initial speed, initial direction)
     */
    public void restart()
    {
        interval = INITIAL_INTERVAL; 
        nextSpeed = INITIAL_SPEED; 
        collision = false; 
        direction = INITIAL_DIRECTION; 
        nextDirection = INITIAL_DIRECTION; 
        x = INITIAL_X; 
        y = INITIAL_Y; 

        position.removeAll(position);
        position.add(new Position(INITIAL_X, INITIAL_Y));
        position.add(new Position(INITIAL_X-20, INITIAL_Y));
        position.add(new Position(INITIAL_X-40, INITIAL_Y));
    }
    
    /**
     * Call when snake eats an apple
     */
    public void eat()
    {
        position.add(position.get(position.size()-1));

        if(interval > 30)
        {
            if(nextSpeed > 0)
                nextSpeed--; 
            else
            {
                interval -= 5; 
                nextSpeed = 6; 
            }
        }
    }

    /**
     * Draws the snake
     */
    public void draw()
    {
        glColor3f(0,1,0); // set colour to #00FF00 (green)
        for(int i = 0; i < position.size(); i++)
        {
            glBegin(GL_QUADS); 
                glVertex2f(position.get(i).getX(), position.get(i).getY());
                glVertex2f(position.get(i).getX() + 20, position.get(i).getY());
                glVertex2f(position.get(i).getX() + 20, position.get(i).getY() + 20);
                glVertex2f(position.get(i).getX(), position.get(i).getY() + 20);
            glEnd();
        }
    }

    /**
     * Pauses / unpauses the snake
     */
    public void pause() 
    {
        if(pause)
            pause = false;
        else if(!pause)
            pause = true;
    }
}
