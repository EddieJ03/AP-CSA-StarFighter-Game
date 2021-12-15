import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class AlienAmmo extends Ammo {
  public AlienAmmo() {
      super(0, 0, 0);
  }

  public AlienAmmo(int x, int y) {
      super(x,y,3);
  }

  public AlienAmmo(int x, int y, int s) {
      super(x,y);
  }

  @Override
  public void draw(Graphics window) {
      window.setColor(Color.RED);
      window.fillRect(getX(), getY(), getWidth(), getHeight());
  }

}
