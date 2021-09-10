package com.dodd.command;

import org.bukkit.entity.Player;

public class HelloWorldCommand extends BaseCommand {

    public HelloWorldCommand() {
        forcePlayer = true;
        cmdName = "sayhello";
        argLength = 0;
    }

    @Override
    public boolean run() {
        Player player = (Player) commandSender;
        player.sendMessage("Hello world");
        return false;
    }
}
