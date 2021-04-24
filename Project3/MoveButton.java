import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class MoveButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private MoveCommand moveCommand;
  private UndoManager undoManager;
  public MoveButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Move");
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    this.undoManager = undoManager;
    mouseHandler = new MouseHandler();
  }
  public void actionPerformed(ActionEvent event) {
    moveCommand = new MoveCommand();
    drawingPanel.addMouseListener(mouseHandler);
    undoManager.beginCommand(moveCommand);
  }
  private class MouseHandler extends MouseAdapter {
    private Boolean itemSelected = false;
    private Point point1;
    public void mouseClicked(MouseEvent event) {
      if (itemSelected == false) {
        point1 = View.mapPoint(event.getPoint());
        moveCommand.setPoint(point1);
        if (moveCommand.getSelectedMoveItem() != null) {
          itemSelected = true;
          drawingPanel.addMouseMotionListener(mouseHandler);
          System.out.println("Move Item Selected"); // debugging
        }
      } else if (itemSelected == true) {
        moveCommand.setMovePoint(View.mapPoint(event.getPoint()));
        moveCommand.moveItem();
        view.refresh();
        drawingPanel.removeMouseListener(this);
        undoManager.endCommand(moveCommand);
        itemSelected = false;
      }
    }
    public void mouseMoved(MouseEvent event) {
      if (itemSelected == true) {
        moveCommand.setMovePoint(View.mapPoint(event.getPoint()));
        moveCommand.moveItem();
        view.refresh();
      } else {
        drawingPanel.removeMouseMotionListener(this);
      }
    }
  }
}
