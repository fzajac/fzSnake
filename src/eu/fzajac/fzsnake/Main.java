package eu.fzajac.fzsnake;

import javax.swing.JOptionPane;
import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Class running the main game loop
 * @author FZajac
 */
public class Main 
{
    private int score = 0; 
    private int lives = 3;

    private boolean showGrid = true; 

    /**
     * Constructor setting up display / OpenGL and containing the main loop
     */
    public Main()
    {	
        Apple apple = new Apple(); 
        apple.start();
        Snake snake = new Snake(); 
        snake.start();

        try 
        {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("fzSnake");
            Display.create();
        }
        catch(LWJGLException e)
        {
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);


        while(!Display.isCloseRequested()) 
        {
            Display.setTitle("fzSnake | Score: " + score + " | Lives: " + lives);

            glClear(GL_COLOR_BUFFER_BIT);

            while (Keyboard.next()) 	
            {
                if (Keyboard.getEventKeyState())
                {
                    if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
                    {
                        snake.pause();
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_P)
                    {
                        snake.pause();
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_G)
                    {
                        if(showGrid)
                            showGrid = false; 
                        else
                            showGrid = true;
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_Q)
                    {
                        Display.destroy();
                        System.exit(0);
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_R)
                    {
                        lives = 3;
                        score = 0; 
                        snake.restart();
                    }
                }
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
                snake.setDirection(0);
            if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                snake.setDirection(1);
            if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
                snake.setDirection(2);
            if(Keyboard.isKeyDown(Keyboard.KEY_UP))
                snake.setDirection(3);

            if(snake.collides())
            {
                if(lives > 0)
                {
                    snake.restart();
                    lives--;
                }
                else
                {
                    int choice = JOptionPane.showOptionDialog(null, "Your final score: " + score + " points. \nDo you want to try again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                    if(choice == 0)
                    {
                        lives = 3;
                        score = 0; 
                        snake.restart();
                    }
                    else
                    {
                        Display.destroy();
                        System.exit(0);
                    }
                }
            }

            if(apple.getPosition()[0] == snake.getPosition()[0] && apple.getPosition()[1] == snake.getPosition()[1])
            {
                apple.randomizePosition();
                snake.eat();
                score += 5;
            }

            // draw snake and apple
            snake.draw();
            apple.draw();

            // draw the grid
            if(showGrid)
            {
                glColor3d(0.05,0.05,0.05);
                // vertical lines
                for(int i = 1; i < 32; i++)
                {
                    glBegin(GL_LINES);
                        glVertex2i(i*20, 0);
                        glVertex2i(i*20, 480);
                    glEnd();
                }

                // horizontal lines
                for(int i = 1; i < 24; i++)
                {
                glBegin(GL_LINES);
                    glVertex2i(0, i*20);
                    glVertex2i(640, i*20);
                glEnd();
                }
            }

            Display.update();
            Display.sync(60);
        }

        Display.destroy();
        System.exit(0);
    }

    public static void main(String[] args) 
    {
        new Main();
    }

}
