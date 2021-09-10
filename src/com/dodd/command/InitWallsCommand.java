package com.dodd.command;

import com.dodd.InvisWallsManager;

public class InitWallsCommand extends BaseCommand {
    InvisWallsManager wallsManager;

    public InitWallsCommand(InvisWallsManager wallsManager) {
        this.wallsManager = wallsManager;

        forcePlayer = false;
        cmdName = "initwalls";
        argLength = 0;
    }

    @Override
    public boolean run() {
        wallsManager.findAllWallBlocks();

        return true;
    }
}
