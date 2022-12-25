import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

//Inheriting Panel class from JPanel class and adding action listener
public class Panel extends JPanel implements ActionListener{
    //dimensions of the panel
    static int width = 1200;
    static int height = 600;
    //size of each unit(grid)
    static int unit = 50;

    //for checking the state of the game at regular intervals(fps)
    Timer timer;
    static int delay = 160;

    //For food spawns
    Random random;
    int fx, fy;

    int body = 3;    //setting the initial body size
    char dir = 'R';
    int score = 0;
    boolean flag = false;

    static int size = (width*height)/(unit*unit);
    //x and y blocks of the snake
    int xsnake[] = new int[size];
    int ysnake[] = new int[size];

    Panel()
    {
        //setting the dimensions of the panel to width*height
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);          //makes the game focusable and keyboard input goes to the game directly
        random = new Random();
        this.addKeyListener(new Key());   //adding key listener to the panel

        game_start();
    }


    public void game_start()
    {
        spawnfood();  //Spawning the food
        flag = true;  //setting the game running flag to true
        timer = new Timer(delay, this);  //starting the timer with delay
        timer.start();
    }

    public void spawnfood()
    {
        //setting random coordinates for the food in 50 multiples
        fx = random.nextInt((int)(width/unit)) * unit;   //random.nextInt() - stores a random integer between 2 points
        fy = random.nextInt((int)(height/unit)) * unit;
    }

    public void checkHit()
    {
        //checking the snake's head collision with its own body or the walls

        //checking with body parts
        for(int i = body; i > 0; i--)
        {
            if((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i]))
            {
                flag = false;
            }
        }

        //check hit with walls
        if(xsnake[0] < 0)
        {
            flag = false;
        }
        if(xsnake[0] > width)
        {
            flag = false;
        }
        if(ysnake[0] < 0)
        {
            flag = false;
        }
        if(ysnake[0] > height)
        {
            flag = false;
        }


        if(flag == false)
        {
            timer.stop();     //stops the timer and start over again
        }
    }


    //Intermediate function to call the draw function
    public void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);
        draw(graphic);
    }
    public void draw(Graphics graphic)
    {
        if(flag)
        //setting parameters for the food block
        {
            graphic.setColor(Color.RED);
            graphic.fillOval(fx, fy, unit, unit);

            //setting params for the snake
            for(int i=0; i< body; i++)
            {
                if(i==0)
                {
                    graphic.setColor(Color.GREEN);
                    graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
                }
                else
                {
                    graphic.setColor(Color.ORANGE);
                    graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
                }
            }

            //drawing the score board
            graphic.setColor(Color.BLUE);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
            FontMetrics f = getFontMetrics(graphic.getFont());
            //drawstring takes the string to draw, starting position in x and the starting position in y
            graphic.drawString("SCORE " +score, (width - f.stringWidth("Score " +score))/2, graphic.getFont().getSize());

        }
        else
        {
            gameOver(graphic);
        }
    }

    public void gameOver(Graphics graphic)
    {
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics f = getFontMetrics(graphic.getFont());
        //drawstring takes the string to draw, starting position in x and the starting position in y
        graphic.drawString("SCORE " +score, (width - f.stringWidth("Score " +score))/2, graphic.getFont().getSize());

        //graphic for the game over text
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
        FontMetrics f2 = getFontMetrics(graphic.getFont());
        //drawstring takes the string to draw, starting position in x and the starting position in y
        graphic.drawString("GAME OVER!", (width - f2.stringWidth("GAME OVER!"))/2, height/2);

        //graphic for the replay prompt
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics f3 = getFontMetrics(graphic.getFont());
        //drawstring takes the string to draw, starting position in x and the starting position in y
        graphic.drawString("Press R to replay", (width - f3.stringWidth("Press R to replay" +score))/2, height/2-180);
    }

    public void move()
    {
        //loops for updating the body parts except the head
        for(int i = body; i>0; i--)
        {
            xsnake[i] = xsnake[i-1];
            ysnake[i] = ysnake[i-1];
        }

        //for updating the head coordinates
        switch(dir)
        {
            case 'U' :
                ysnake[0] = ysnake[0] - unit;
                break;
            case 'D' :
                ysnake[0] = ysnake[0] + unit;
                break;
            case 'L' :
                xsnake[0] = xsnake[0] - unit;
                break;
            case 'R' :
                xsnake[0] = xsnake[0] + unit;
                break;
        }
    }

    public void CheckScore()
    {
        if((fx == xsnake[0]) && (fy == ysnake[0]))
        {
            body++;
            score++;
            spawnfood();
        }
    }
    public class Key extends KeyAdapter {          //KeyAdapter allows the class to act to the keyboard
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    if(dir != 'R')
                    {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir != 'L')
                    {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir != 'D')
                    {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir != 'U')
                    {
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag)
                    {
                        //changing everything to initial values and starting the game
                        score = 0;
                        body = 3;
                        dir = 'R';
                        Arrays.fill(xsnake, 0);
                        Arrays.fill(ysnake, 0);
                        game_start();
                    }
                    break;
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        if(flag)
        {
            move();
            CheckScore();
            checkHit();

        }
        repaint();

    }

}
