import java.awt.*;
public class Triangle extends Item {
  private Point point1;
  private Point point2;
  private Point point3;
  public Triangle(Point point1, Point point2, Point point3) {
    this.point1 = point1;
    this.point2 = point2;
    this.point3 = point3;
  }
  public Triangle(Point point1, Point point2) {
    this.point1 = point1;
    this.point2 = point2;
    point3 = null;
  }
  public Triangle(Point point1) {
    this.point1 = point1;
	  point2 = null;
    point3 = null;
  }
  public Triangle() {
	  point1 = null;
	  point2 = null;
    point3 = null;
  }
  public boolean includes(Point point) {
    return ((distance(point, point1 ) < 10.0) || (distance(point, point2) < 10.0) || (distance(point, point3) < 10.0));
  }
  public void render() {
    uiContext.drawTriangle(point1, point2, point3);
  }
  public void setPoint1(Point point) {
    point1 = point;
  }
  public void setPoint2(Point point) {
    point2 = point;
  }
  public void setPoint3(Point point) {
    point3 = point;
  }
  public Point getPoint1() {
    return point1;
  }
  public Point getPoint2() {
    return point2;
  }
  public Point getPoint3() {
    return point3;
  }
  public String toString() {
    return "Triangle from " + point1 + " to " + point2 + " to " + point3;
  }
  @Override
  public boolean moveTo(Point point) {
    if(point != null) {
      int x = point.x;
      int y = point.y;
      int dx = x - point1.x;
      int dy = y - point1.y;
      point1 = point;
      point2.x += dx;
      point2.y += dy;
      point3.x += dx;
      point3.y += dy;
      return true;
    }
    return false;
  }
  @Override
  public Point getStartingPoint() {
    return point1;
  }
}

