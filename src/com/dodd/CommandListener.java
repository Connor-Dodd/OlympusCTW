package com.dodd;

import com.dodd.command.BaseCommand;
import net.minecraft.server.commands.CommandExecute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class CommandListener extends CommandExecute implements Listener, CommandExecutor {
    public static CommandListener current;

    private MCTWPlugin plugin;
    private HashMap<String, BaseCommand> commands;

    public CommandListener(MCTWPlugin plugin) {
        this.plugin = plugin;
        current = this;
        commands = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {
        BaseCommand command = commands.get(cmd.getName());
        if (command != null) {
            System.out.println("COMMAND PROCESSING: " + command.cmdName);
            command.process(commandSender, args);
        }
        return true;
    }

    public boolean registerCommand(BaseCommand command) {
        if (!commands.containsKey(command.cmdName)) {
            commands.put(command.cmdName, command);
            plugin.getCommand(command.cmdName).setExecutor(this);
            return true;
        }

        return false;
    }
}
