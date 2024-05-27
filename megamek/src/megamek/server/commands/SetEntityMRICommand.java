package megamek.server.commands;

import megamek.common.Entity;
import megamek.common.Player;
import megamek.server.GameManager;
import megamek.server.Server;

/**
 * This command enables or disables manual roll input for selected entity.
 */
public class SetEntityMRICommand extends ServerCommand { //TODO - test
  private final GameManager gameManager;

  public SetEntityMRICommand(Server server, GameManager gameManager) {
    super(server, "setmri", "Enables or disables Manual Roll Input for selected entity. Usage: setmri [entity id] [on/off]");
    this.gameManager = gameManager;
  }

  @Override
  public void run(int connId, String... args) {
    // Allow change option if requested by GM or entity owner.
    Player player = server.getPlayer(connId);
    Entity entity;
    boolean isOwner = false;
    if (args.length == 3) {
      entity = gameManager.getGame().getEntity(Integer.parseInt(args[1]));
      if (entity != null) {
        if (player == entity.getOwner())
        {
          isOwner = true;
        } else {
          isOwner = false;
        }
      }

      if (!player.isGameMaster() || !isOwner) {
        server.sendServerChat(connId, "Only GM or owner of the entity can run this command");
        return;
      }

      try {
        int id = Integer.parseInt(args[1]);
        String parameter = args[2];
        boolean argument;
        if ("on".equalsIgnoreCase(parameter)) {
          argument = true;
        } else if ("off".equalsIgnoreCase(parameter)) {
          argument = false;
        } else {
          server.sendServerChat(connId, "Error parsing the command.");
          return;
        }
        entity.setMRIOption(argument);
        server.sendServerChat(connId, "Entity #" + id + "MRI mode set to - " + args[2]);
      } catch (Exception ignore) {
        server.sendServerChat(connId, "Error parsing the command.");
      }

    } else {
      server.sendServerChat(connId, "Error parsing the command. Invalid number of arguments.");
    }
  }
}

