package me.rik02.prefix.objects;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractCommand extends Command {
    // Properties
    private final String name;
    private final String permission;
    private Player player = null;
    private List<String> subCommands;

    // Constructor
    public AbstractCommand(String name, String permission) {
        super(name);
        setPermission(permission);

        this.name = name;
        this.permission = permission;
        subCommands = new LinkedList<>();
    }

    // Methods
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) player = (Player) sender;
        if (!subCommands.isEmpty()) {
            if (!(getExecutedSubcommand(args).equalsIgnoreCase(""))) {
                if (hasPermission(player, permission + "." + getExecutedSubcommand(args))) {
                    try {
                        Method method = getClass().getMethod(getExecutedSubcommand(args), CommandSender.class, String.class, String[].class);
                        method.invoke(this, sender, commandLabel, getSubCommandArgs(args));
                        return true;
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {}
                }
            }
        }

        if (!hasPermission(player, permission)) return true;
        onCommand(sender, commandLabel, args);

        return true;
    }

    protected void onCommand(CommandSender sender, String commandLabel, String[] args) {}

    public final boolean hasPermission(Player player, String permission) {
        if (player == null) return true;
        if (!player.hasPermission(permission)) {
            player.sendMessage(ChatColor.RED + "You need the permission " + permission + " to execute this command.");
            return false;
        }

        return true;
    }

    private String getExecutedSubcommand(String[] args) {
        if (args.length == 0) return "";
        if (subCommands == null) return "";
        for (String subCommand : subCommands) {
            if (subCommand.equalsIgnoreCase(args[0])) return subCommand;
        }

        return "";
    }

    private String[] getSubCommandArgs(String[] args) {
        if (args.length == 0) return new String[0];
        if (subCommands == null) return new String[0];

        int count = 0;
        String[] subCommandArgs = new String[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            subCommandArgs[count] = args[i];
            count++;
        }

        return subCommandArgs;
    }

    public final void setSubCommands(List<String> subCommands) {
        this.subCommands = subCommands;
    }

    public final void addSubCommand(String subCommand) {
        subCommands.add(subCommand);
    }

    public final void clearSubCommands() {
        subCommands.clear();
    }

    public final String getName() {
        return name;
    }
}
