package com.dodd.command;

import com.dodd.BlockLogger;

public class LogBlockClicksCommand extends BaseCommand {
    BlockLogger blockLogger;

    public LogBlockClicksCommand(BlockLogger blockLogger) {
        this.blockLogger = blockLogger;
        forcePlayer = false;
        cmdName = "logblocks";
        argLength = 1;
    }

    @Override
    public boolean run() {
        if (args[0].equalsIgnoreCase("enabled")) {
            blockLogger.isLogging = true;
        } else if (args[0].equalsIgnoreCase("disabled")) {
            blockLogger.isLogging = false;
        }

        return true;
    }
}
