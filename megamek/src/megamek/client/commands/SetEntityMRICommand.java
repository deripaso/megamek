package megamek.client.commands;

import megamek.client.Client;
import megamek.common.Entity;
import megamek.common.Player;
import megamek.common.options.OptionsConstants;


/**
 * This command enables or disables manual roll input for selected entity.
 */
public class SetEntityMRICommand extends ClientCommand { //TODO - test

  public SetEntityMRICommand(Client client) {
    super(client, "setmri", "Enables or disables Manual Roll Input for selected entity. Usage: setmri [entity id] [on/off]");
  }

  @Override
  public String run(String[] args) {

    Player player = getClient().getLocalPlayer();
    Entity entity;
    boolean isOwner = false;
    // Allow change option if requested by GM or entity owner.
    if (args.length == 3) {
      entity = getClient().getEntity(Integer.parseInt(args[1]));
      if (entity != null) {
        if (player == entity.getOwner()) {
          isOwner = true;
        } else {
          isOwner = false;
        }
      }

      if (!player.isGameMaster() || !(player.getId() == entity.getOwner().getId())) {
        return "You should be GM or Owner of this entity to change this option.";
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
          return "Error parsing the command.";
        }

        client.getEntity(id).setMRIOption(argument);
        return "Entity #" + id + "MRI mode set to - " + args[2];

      } catch (Exception ignore) {
        return "Error parsing the command. Invalid arguments";
      }
    }
    return "Error parsing the command";
  }
}

