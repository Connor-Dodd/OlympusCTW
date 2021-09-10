package com.dodd.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseCommand {

    public BaseCommand() {
    }

    //Initalize in constructor
    public String cmdName;
    public int argLength = 0;
    public boolean forcePlayer = true;

    //Get from execution
    public CommandSender commandSender;
    public String[] args;
    public Player player;

    public void process(CommandSender commandSender, String[] arg) {
        this.commandSender = commandSender;
        args = arg;

        if (forcePlayer) {
            if (!(commandSender instanceof Player))  {
                commandSender.sendMessage("Must be player");
                return;
            } else {
                player = (Player) commandSender;
            }
        }

        if (argLength > arg.length)
            commandSender.sendMessage(ChatColor.DARK_RED + "Wrong usage: ");
        else {
            run();
        }
    }

    public abstract boolean run();
}
