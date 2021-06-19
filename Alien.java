import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Alien extends MovingThing
{
  private int speed;
  private Image image;
  public String direction = "RIGHT";

  boolean counted = false;

  public Alien()
  {
    this(0,0,30,30,0);
    try
    {
      URL url = getClass().getResource("alien.jpg");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
      System.out.println("Error!");
    }
  }

  public Alien(int x, int y)
  {
    this(x, y, 30, 30, 1);
    try
    {
      URL url = getClass().getResource("alien.jpg");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
      System.out.println("Error!");
    }
  }

  public Alien(int x, int y, int s)
  {
    this(x, y, 30, 30, s);
    try
    {
      URL url = getClass().getResource("alien.jpg");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
      System.out.println("Error!");
    }
  }

  public Alien(int x, int y, int w, int h, int s)
  {
    super(x, y, w, h);
    speed = s;
    try
    {
      URL url = getClass().getResource("alien.jpg");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
      System.out.println("Error!");
    }
  }

  public void setSpeed(int s)
  {
    speed = s;
  }

  public int getSpeed()
  {
    return speed;
  }

  public void move(String direction)
  {
        if(direction.equals("LEFT")){
            setX(getX()-speed);
        }
        if(direction.equals("RIGHT")){
            setX(getX()+speed);
        }
        if(direction.equals("UP")){
            setY(getY()-speed);
        }
        if(direction.equals("DOWN")){
            setY(getY()+speed);
        }
        
  }

  public void alreadyCounted() {
    counted = true;
  }

  public boolean getAlreadyCounted() {
    return counted;
  }

  public void reset() {
    counted = false;
  }

  public void draw( Graphics window )
  {
    window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
  }

  public String toString()
  {
    return super.toString() + " " + getSpeed();
  }
}
