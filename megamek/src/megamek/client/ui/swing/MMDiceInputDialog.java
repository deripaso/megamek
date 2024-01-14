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
      throwResult = dice; // TODO - this is a workaround for "Window Close Action", make a better one (confirm dialog probably)
      setTitle(title);
      //private JFrame dialogFrame = owner;
      JPanel panButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
      add(panButtons, BorderLayout.PAGE_START);
      t = new JTextField(4);
      JTextArea tt = new JTextArea();
      tt.setEditable(false);
      tt.setLineWrap(true);
      tt.setWrapStyleWord(true);
      tt.setText(rolldescription);
      tt.setBounds(100,100,450,600);
      t.addActionListener(e -> parseThrow(numDice));
      JButton butOK = new DialogButton(Messages.getString("Send"));
      panButtons.add(butOK);
      butOK.addActionListener(e -> parseThrow(numDice));
      panButtons.add(t);
      panButtons.add(tt);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      owner.setSize(600,800);
      owner.setLocation((screenSize.width - getSize().width) / 4, (screenSize.height - getSize().height) / 4);

      addWindowListener( new WindowAdapter() {
        public void windowOpened( WindowEvent e ){ //TODO - more research on swing behavior
          t.requestFocusInWindow();
        }
      } );
      setVisible(true);

      addKeyListener(k);
      butOK.addKeyListener(k);
    }


    private void parseThrow(int numDice) {
      int result = Integer.parseInt(t.getText());
      if(result >= (numDice) && result <= (6*numDice)) {
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