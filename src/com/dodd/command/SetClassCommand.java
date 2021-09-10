package com.dodd.command;

import com.dodd.gameclass.GameClassManager;

public class SetClassCommand extends BaseCommand {
    GameClassManager manager;
    
    public SetClassCommand(GameClassManager manager) {
        this.manager = manager;

        forcePlayer = true;
        cmdName = "setclass";
        argLength = 1;
    }


    @Override
    public boolean run() {
        if (args[0].equalsIgnoreCase("rogue")) {
            manager.assignPlayerClass(player, GameClassManager.GameClasses.Rogue);
            return  true;
        } else if (args[0].equalsIgnoreCase("cleric")) {
            manager.assignPlayerClass(player, GameClassManager.GameClasses.Cleric);
            return true;
        }

        return false;
    }
}
