package megamek.common;

import megamek.client.Client;
import megamek.common.net.enums.PacketCommand;
import megamek.common.net.packets.Packet;
import megamek.server.GameManager;
import megamek.server.IGameManager;
import megamek.server.Server;
import megamek.client.ui.swing.MMDiceInputDialog;
import org.apache.logging.log4j.LogManager;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;


public class DiceThrower {

  public static int Throw() {
    return 9;
  }

  public static int ThrowInitiative(int dice, ITurnOrdered whois, Server server) {
    int rollResult = dice;
    int playerID = 0;
    String playerName = null;

    if (whois instanceof Team) {
      //playerNum = ((Team) whois).getId();
     // playerName = Server.getServerInstance().getGame().getTeams().get(playerNum).toString();
      playerName = "Team "+whois.toString();
      List<Player> playersList = ((Team) whois).players();
      int playerIndex;
      for (playerIndex = 0; ((Team) whois).players().get(playerIndex).isBot(); playerIndex++) {
        playerID = ((Team) whois).players().get(playerIndex).getId();
        }
      } else if (whois instanceof Player) {
          playerID = ((Player) whois).getId();
          playerName = ((Player) whois).getName();
        } else if (whois instanceof Entity) {
          playerName = ((Entity) whois).getDisplayName();
          playerID = ((Entity) whois).getOwnerId();
    }

     String rollTitle = playerName+" Initiative Roll";
     String rollDescription = dice + "D6 roll";
     IGameManager gm = server.getGameManager();
     rollResult = gm.processManualIniCFR(playerID, rollTitle, rollDescription, dice);

    /*JFrame frame = new JFrame();
       int diceThrow = MMDiceInputDialog.throwDice(frame, playerName + " Initiative Roll", dice + "D6 roll", dice);
       //      LogManager.getLogger().info("Initiative dice: " + diceThrow);
       rollResult = diceThrow;
    */

    return rollResult;
  }

  public static int ThrowD6(int dice, Entity entity, String rollDescription, int targetRoll) {
    int playerId = entity.getOwnerId();
    String playerName = entity.getOwner().getName();
    String rollTitle = playerName+"'s "+dice+"D6 roll";

    IGameManager gm = Server.getServerInstance().getGameManager();

    int rollResult = gm.processManualIntD6CFR(playerId, rollTitle, rollDescription, dice, targetRoll);

    return rollResult;
        /*JFrame frame = new JFrame();
    rollResult = MMDiceInputDialog.throwDice(frame,playerName+"'s "+dice+"D6 roll", rolltype, dice);
    //LogManager.getLogger().info(playerName+" dice for "+rolltype+entityName + diceThrow);*/
  }

  public static Roll mRollD6(int dice, Entity entity, String rollDescription, int tartgetRoll) {
    int playerId = entity.getOwnerId();
    String playerName = entity.getOwner().getName();
    String rollTitle = playerName+"'s "+dice+"D6 roll";
    IGameManager gm = Server.getServerInstance().getGameManager();

    int roll = gm.processManualRollD6CFR(playerId, rollTitle, rollDescription, dice, tartgetRoll);
    Roll rollResult = new MMRoll.manRoll(dice,0, roll);

    return rollResult;
  }
  public static int customDice(int dice, int sides, Entity entity, String rollDescription) { //TODO - make better unified method for possible NdX throws
    int playerId = entity.getOwnerId();
    String playerName = entity.getOwner().getName();
    String rollTitle = playerName+"'s "+dice+"D"+sides+" roll";
    IGameManager gm = Server.getServerInstance().getGameManager();

    int rollResult = gm.processManualRollD6CFR(playerId, rollTitle, rollDescription, dice);

    return rollResult;
  }
}
