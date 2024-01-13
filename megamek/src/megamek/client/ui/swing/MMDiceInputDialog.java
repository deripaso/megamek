package megamek.client.ui.swing;

import megamek.client.ui.Messages;
import megamek.client.ui.swing.dialog.DialogButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MMDiceInputDialog  {


  // default constructor
  public static int throwDice(JFrame owner, String title, String rolldescription, int numDice) {
    DiceInputDialog dialog = new DiceInputDialog(owner, title, rolldescription, numDice);
    return dialog.throwResult;
    }

  private static class DiceInputDialog extends ClientDialog {

    private int throwResult = 1;
    private int numDice;
    private JTextField t;

    public DiceInputDialog(JFrame owner, String title, String rolldescription, int dice) {
      super(owner, title, true, true);
      numDice = dice;
      throwResult = dice; // TODO - this is a workaround for "Window Close Action", make a better one
      setTitle(title);
      //private JFrame dialogFrame = owner;
      JPanel panButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
      add(panButtons, BorderLayout.PAGE_END);
      t = new JTextField(4);
      JTextField tt = new JTextField(12);
      tt.setEditable(false);
      tt.setText(rolldescription);
      t.addActionListener(e -> parseThrow(numDice));
      JButton butOK = new DialogButton(Messages.getString("Send"));
      panButtons.add(butOK);
      butOK.addActionListener(e -> parseThrow(numDice));
      panButtons.add(t);
      panButtons.add(tt);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      owner.setLocation((screenSize.width - getSize().width) / 2, (screenSize.height - getSize().height) / 2);
      owner.setSize(600,200);
      t.requestFocusInWindow(); //TODO - test
      setVisible(true);
      t.requestFocus();

      //getRootPane().setDefaultButton(butOK);//TODO - test
      addKeyListener(k);
      butOK.addKeyListener(k);
    }


    private void parseThrow(int numDice) {
      int result = Integer.parseInt(t.getText());
      if(result >= (numDice) && result <= (6*numDice)) { //TODO - test
        throwResult = result;
        setVisible(false);
      }
    }
    KeyListener k = new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          parseThrow(numDice);
        }
      }
    };
  }

}