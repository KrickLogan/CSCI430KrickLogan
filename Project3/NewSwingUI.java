import java.awt.*;
public class NewSwingUI implements UIContext {
  private Graphics graphics;
  private static NewSwingUI swingUI;
  private NewSwingUI() {
  }
  public static NewSwingUI getInstance() {
    if (swingUI == null) {
      swingUI = new NewSwingUI();
    }
    return swingUI;
  }
  public  void setGraphics(Graphics graphics) {
    this.graphics = graphics;
  }
  public void drawLabel(Point point, String text) {
    if (point != null) {
      if (text != null) {
        graphics.drawString(text, (int) point.getX(), (int) point.getY());
      }
    }
    int length = graphics.getFontMetrics().stringWidth(text);
    graphics.drawString("_", (int) point.getX() + length, (int) point.getY());
  }
  public void drawLine(Point point1,  Point point2) {
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    if (point1 != null) {
      i1 = Math.round((float) (point1.getX()));
      i2 = Math.round((float) (point1.getY()));
      if (point2 != null) {
        i3 = Math.round((float) (point2.getX()));
        i4 = Math.round((float) (point2.getY()));
      } else {
        i3 = i1;
        i4 = i2;
      }
      graphics.drawLine(i1, i2, i3, i4);
    }
  }
  public void drawTriangle(Point point1, Point point2, Point point3) {
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    if (point1 != null) {
      i1 = Math.round((float) (point1.getX()));
      i2 = Math.round((float) (point1.getY()));
      if (point2 != null) {
        i3 = Math.round((float) (point2.getX()));
        i4 = Math.round((float) (point2.getY()));
        if (point3 != null) {
          i5 = Math.round((float) (point3.getX()));
          i6 = Math.round((float) (point3.getY()));
        } else {
          i5 = i3;
          i6 = i4;
        }
      } else {
        i3 = i1;
        i4 = i2;
        i5 = i1;
        i6 = i2;
      } 
      graphics.drawLine(i1, i2, i3, i4);
      graphics.drawLine(i3, i4, i5, i6);
      graphics.drawLine(i5, i6, i1, i2);
    }
  }
  public void draw(Item item) {
    System.out.println( "Cant draw unknown Item \n");
  }
}