import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.*;

public class OuterSpace extends Canvas implements KeyListener, Runnable
{
  private Ship ship;
  private int hordeSize;
  private AlienHorde horde;
  private Bullets shots;
  private Bullets alienShots;

  boolean spaceReleased;

  private int score, lives;

  private boolean[] keys;
  private BufferedImage back;

  private Timer t;

  public OuterSpace()
  {
    spaceReleased = true;

    setBackground(Color.black);

    keys = new boolean[5];

    score = 0;
    lives = 3;

    hordeSize = 16;

    //instantiate other instance variables
    //Ship, Alien
    ship = new Ship(400, 450);

    //alienOne = new Alien(400, 100);
    //alienTwo = new Alien(500, 100);
    horde = new AlienHorde(16);

    shots = new Bullets();

    alienShots = new Bullets();

    t = new Timer();

    t.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        alienShots.add(new Ammo(horde.getList().get((int)(Math.random() * horde.getList().size())).getX() + horde.getList().get((int)(Math.random() * horde.getList().size())).getWidth() / 2, horde.getList().get((int)(Math.random() * horde.getList().size())).getY() ) );
      }
    }, 0, (int)((Math.random() * 3) + 1 * 1000));


    this.addKeyListener(this);
    new Thread(this).start();

    setVisible(true);
  }

  public void update(Graphics window)
  {
        if(ship.getLost() == false) {
          paint(window);
        }
  }

  public void paint( Graphics window )
  {
    //set up the double buffering to make the game animation nice and smooth
    Graphics2D twoDGraph = (Graphics2D)window;

    //take a snap shop of the current screen and same it as an image
    //that is the exact same width and height as the current screen
    if(back==null)
      back = (BufferedImage)( createImage(getWidth(), getHeight()) );

    //create a graphics reference to the back ground image
    //we will draw all changes on the background image
    Graphics graphToBack = back.createGraphics();

    graphToBack.setColor(Color.BLACK);
    graphToBack.fillRect(0,0,800,600);
    graphToBack.setColor(Color.BLUE);
    graphToBack.drawString("StarFighter Score: " + score, 30, 50 );
    graphToBack.setColor(Color.RED);
    graphToBack.drawString("StarFighter Lives: " + lives, 25, 500 );
    
    ship.draw(graphToBack);
    //alienOne.draw(graphToBack);
    //alienTwo.draw(graphToBack);

    //add code to move Ship, Alien, etc.
    if(keys[0] == true)
    {
      ship.move("LEFT");
    }
    if(keys[1] == true) 
    {
      ship.move("RIGHT");
    }
    if(keys[2] == true) 
    {
      ship.move("UP");
    }
    if(keys[3] == true) 
    {
      ship.move("DOWN");
    }
    if(keys[4] == true) {

      if(spaceReleased) {
        shots.add(new Ammo(ship.getX()+ship.getWidth()/2, ship.getY()));
      }

      spaceReleased = false;
      keys[4] = false;

    }

    horde.drawEmAll(graphToBack);
    horde.moveEmAll();
        
    shots.drawEmAll(graphToBack);
    shots.moveEmAll();

    alienShots.drawEmAll(graphToBack);
    alienShots.moveEmAll("DOWN");


    //add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship
    score = horde.removeDeadOnes(shots.getList(), score);

    for(Alien al : horde.getList()){
        if( (al.getX() > ship.getX() && al.getX() < ship.getX() + ship.getWidth()) && (al.didCollideBottom(ship)) && !al.getAlreadyCounted()) {
            System.out.println("Collision Detected: An Alien Hit Your Ship!");
            lives = lives - 1;
            al.alreadyCounted();
                if(lives <= 0) {
                  graphToBack.setColor(Color.BLACK);
                  graphToBack.fillRect(0, 0, 800, 600);
                  graphToBack.setColor(Color.RED);
                  graphToBack.drawString("GAME OVER: YOU LOST ", 325, 275);
                  graphToBack.drawString("Score: " + score, 325, 300);
                  ship.setLost(true);
                  t.cancel();
                  t.purge();
                }
        }
    }

    List<Ammo> removeBul = new ArrayList<Ammo>();
    for(Ammo a : alienShots.getList()){
            if( (a.getX() > ship.getX() && a.getX() < ship.getX() + ship.getWidth()) && (ship.didCollideTop(a))){
              lives -= 1;
              System.out.println("Collision Detected: An Alien Ammo Hit Your Ship!");
                if(lives <= 0) {
                  graphToBack.setColor(Color.BLACK);
                  graphToBack.fillRect(0, 0, 800, 600);
                  graphToBack.setColor(Color.RED);
                  graphToBack.drawString("GAME OVER: YOU LOST ", 325, 275);
                  graphToBack.drawString("Score: " + score, 325, 300);
                  ship.setLost(true);
                  t.cancel();
                  t.purge();
              }
              removeBul.add(a);
            }
    }
    alienShots.getList().removeAll(removeBul);
    
    if(horde.getList().size() == 0){
        graphToBack.setColor(Color.BLACK);
            graphToBack.fillRect(0, 0, 800, 600);
            horde = new AlienHorde(hordeSize++);
            lives += 1;
    }

    twoDGraph.drawImage(back, null, 0, 0);
  }



  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      keys[4] = true;
    }
    repaint();
  }

  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      keys[1] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP)
    {
      keys[2] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN)
    {
      keys[3] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      spaceReleased = true;
      keys[4] = false;
    }
    repaint();
  }


  public void keyTyped(KeyEvent e)
  {
    //no code needed here
  }

  public void run()
  {
    try
    {
      while(true)
      {
        Thread.currentThread().sleep(5);
        repaint();
      }
    }catch(Exception e)
    {
    }
  }
}

