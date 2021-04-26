import java.awt.*;
public class TriangleCommand extends Command {
  private Triangle triangle;
  private int pointCount;
  public TriangleCommand() {
    this(null, null, null);
    pointCount = 0;
  }
  public TriangleCommand(Point point) {
    this(point, null, null);
    pointCount = 1;
  }
  public TriangleCommand(Point point1, Point point2) {
    triangle = new Triangle(point1, point2);
    pointCount = 2;
  }
  public TriangleCommand(Point point1, Point point2, Point point3) {
    triangle = new Triangle(point1, point2, point3);
    pointCount = 3;
  }
  public void setStartPoint(Point point) {
      triangle.setPoint1(point);
  }
  public void setMidPoint(Point point) {
      triangle.setPoint2(point);
  }
  public void setEndPoint(Point point) {
      triangle.setPoint3(point);
  }
  public void execute() {
    model.addItem(triangle);
  }
  public boolean undo() {
    model.removeItem(triangle);
    return true;
  }
  public boolean redo() {
    execute();
    return true;
  }
  public boolean end() {
    if (triangle.getPoint1() == null) {
      undo();
      return false;
    }
    if (triangle.getPoint2() == null) {
       undo();
       return false;
    }
    if (triangle.getPoint3() == null) {
       undo();
       return false;
    }
    return true;
  }
}
