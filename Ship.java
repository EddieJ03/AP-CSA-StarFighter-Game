import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Ship extends MovingThing
{
  private int speed;
  private Image image;
  private boolean lost = false;

  public Ship()
  {
    this(10,10,10,10,10);
  }

  public Ship(int x, int y)
  {
    super(x, y);
    speed = 2;
    try
    {
      URL url = getClass().getResource("ship.jpg");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
      System.out.println("Error!");
    }
  }

  public Ship(int x, int y, int s)
  {
    super(x, y);
    speed = s;
    try
    {
      URL url = getClass().getResource("ship.jpg");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
      System.out.println("Error!");
    }
  }

  public Ship(int x, int y, int w, int h, int s)
  {
    super(x, y, w, h);
    speed=s;
    try
    {
      URL url = getClass().getResource("ship.jpg");
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
    if(direction.equals("UP")) {
      if(getY() + getSpeed() > 0) {
        setY(getY() - getSpeed());
      }
    } else if(direction.equals("DOWN")) {
      if(getY() + getHeight() + getSpeed() < 600) {
        setY(getY() + getSpeed());
      }
    } else if(direction.equals("LEFT")) {
      if(getX() + getSpeed() > 0) {
        setX(getX() - getSpeed());
      }
    } else {
      if(getX() + getSpeed() + getWidth() < 800) {
        setX(getX() + getSpeed());
      }
    }
  }

  public void draw( Graphics window )
  {
    window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
  }

  public void setLost(boolean b) {
      lost = b;
  }
  public boolean getLost() {
      return lost;
  }

  public String toString()
  {
    return super.toString() + " " + getSpeed();
  }
}
