package megamek.common;

import megamek.client.ui.swing.MMDiceInputDialog;
import org.apache.logging.log4j.LogManager;

import javax.swing.*;


public class DiceThrower {

  public static int Throw() {
    return 9;
  }

  public static int ThrowInitiative(int dice, ITurnOrdered whois) {
    int rollResult = 0;
    int playerNum;
    String playerName = null;

    if (whois instanceof Team) {
      //playerNum = ((Team) whois).getId();
     // playerName = Server.getServerInstance().getGame().getTeams().get(playerNum).toString();
      playerName = whois.toString();
      }
     else if (whois instanceof Player) {
      //playerNum = ((Player) whois).getId();
     // playerName = Server.getServerInstance().getGame().getPlayer(playerNum).getName();
      playerName = ((Player) whois).getName();
    }
     else if (whois instanceof Entity) {
      playerName = ((Entity) whois).getDisplayName();
    }

    JFrame frame = new JFrame();
      int diceThrow = MMDiceInputDialog.throwDice(frame,playerName+" Initiative Roll", dice + "D6 roll", dice);
//      LogManager.getLogger().info("Initiative dice: " + diceThrow);
      rollResult = diceThrow;

    return rollResult;
  }

  public static int ThrowD6(int dice, Entity entity, String rolltype) {
    int rollResult = 0;
    String entityName = entity.toString();
    String playerName = entity.getOwner().getName();

    JFrame frame = new JFrame();
    int diceThrow = MMDiceInputDialog.throwDice(frame,playerName+"'s "+rolltype, dice + "D6 roll", dice);
    LogManager.getLogger().info(playerName+" dice for "+rolltype+entityName + diceThrow);
    rollResult = diceThrow;

    return rollResult;
  }

  public static Roll mRollD6(int dice, Entity entity, String rolltype) {
    String entityName = entity.toString();
    String playerName = entity.getOwner().getName();

    JFrame frame = new JFrame();
    int diceThrow = MMDiceInputDialog.throwDice(frame,playerName+"'s "+rolltype, dice + "D6 roll", dice);
    LogManager.getLogger().info(playerName+" dice for "+rolltype+entityName + diceThrow);
    Roll rollResult = new MMRoll.manRoll(dice,0, diceThrow);
    return rollResult;
  }

}
