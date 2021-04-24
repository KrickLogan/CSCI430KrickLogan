import java.awt.*;
import java.util.*;
public class MoveCommand extends Command {
  private Item item;
  private Item selectedItem = null;
  private Point startPoint;
  private Point movePoint;
  public MoveCommand() {
  }
  public void setPoint(Point point) {
    Enumeration enumeration = model.getItems();
    while (enumeration.hasMoreElements()) {
      item = (Item)(enumeration.nextElement());
      if (item.includes(point)) {
        selectedItem = item;
        startPoint = point;
        break;
      }
    }
  }
  public void setMovePoint(Point point) {
    movePoint = point;
  }
  public void moveItem() {
    selectedItem.moveTo(movePoint);
  }
  public void moveItemBack() {
    selectedItem.moveTo(startPoint);
  }
  public Point getMovePoint() {
    return movePoint;
  }
  public Item getSelectedMoveItem() {
    return selectedItem;
  }
  public boolean undo() {
    moveItemBack();
    Model.getView().refresh();
    return true;
  }
  public boolean  redo() {
    execute();
    return true;
  }
  public void execute() {
    moveItem(); // move item
    Model.getView().refresh();
  }
  public boolean end() {
    return true;
  }
}
