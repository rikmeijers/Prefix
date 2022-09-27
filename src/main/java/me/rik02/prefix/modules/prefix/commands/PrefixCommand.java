package me.rik02.prefix.modules.prefix.commands;

import me.rik02.prefix.modules.prefix.PrefixModule;
import me.rik02.prefix.modules.prefix.objects.PGroup;
import me.rik02.prefix.modules.prefix.objects.PPlayer;
import me.rik02.prefix.objects.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public final class PrefixCommand extends AbstractCommand {
    // Properties
    private final PrefixModule module;
    private final List<String> colors = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "k", "l", "m", "n", "o", "r");

    // Constructor
    public PrefixCommand(PrefixModule module) {
        super("prefix", "prefix.prefix");
        setSubCommands(Arrays.asList("help", "chat", "tab", "chatnamecolor", "tabnamecolor", "chatcolor", "weight", "group", "reload"));

        this.module = module;
    }

    // Methods
    // /prefix
    @Override
    protected void onCommand(CommandSender sender, String commandLabel, String[] args) {
        if (args.length > 0) {
            String arg = args[0];
            if (sender instanceof Player) {
                if (!hasPermission((Player) sender, "prefix.prefix.others")) return;
            }

            others(sender, arg, commandLabel, args);
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return;
        }

        Player player = (Player) sender;
        PPlayer pplayer = new PPlayer(player.getName(), module);
        if (pplayer.getGroupName().equals("") || pplayer.getPGroup() == null) {
            player.sendMessage(ChatColor.RED + "You are not assigned to a valid group.");
            return;
        }

        PGroup pgroup = pplayer.getPGroup();
        player.sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + "Prefix");
        player.sendMessage(ChatColor.WHITE + "Player: " + player.getName() + ".");
        player.sendMessage(ChatColor.WHITE + "Chatprefix: " + pgroup.getChatPrefix().replace('&', '§') + ChatColor.RESET + ChatColor.WHITE + ".");
        player.sendMessage(ChatColor.WHITE + "Tabprefix: " + pgroup.getTabPrefix().replace('&', '§') + ChatColor.RESET + ChatColor.WHITE + ".");
        player.sendMessage(ChatColor.WHITE + "Chatnamecolor: " + pgroup.getChatNameColor() + "COLOR" + ChatColor.RESET + ChatColor.WHITE + ".");
        player.sendMessage(ChatColor.WHITE + "Tab" +
                "namecolor: " + pgroup.getTabNameColor() + "COLOR" + ChatColor.RESET + ChatColor.WHITE + ".");
        player.sendMessage(ChatColor.WHITE + "Chatcolor: " + pgroup.getChatColor() + "COLOR" + ChatColor.RESET + ChatColor.WHITE + ".");
        player.sendMessage(ChatColor.WHITE + "Group: " + pgroup.getName().toUpperCase() + ".");
    }

    // /prefix [player]
    public void others(CommandSender sender, String target, String commandLabel, String[] args) {
        PPlayer pplayer = new PPlayer(target, module);
        if (pplayer.getGroupName().equals("") || pplayer.getPGroup() == null) {
            sender.sendMessage(ChatColor.RED + "This player is not assigned to a valid group.");
            return;
        }

        PGroup pgroup = pplayer.getPGroup();
        sender.sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + "Prefix");
        sender.sendMessage(ChatColor.WHITE + "Player: " + target + ".");
        sender.sendMessage(ChatColor.WHITE + "Chatprefix: " + pgroup.getChatPrefix().replace('&', '§') + ChatColor.RESET + ChatColor.WHITE + ".");
        sender.sendMessage(ChatColor.WHITE + "Tabprefix: " + pgroup.getTabPrefix().replace('&', '§') + ChatColor.RESET + ChatColor.WHITE + ".");
        sender.sendMessage(ChatColor.WHITE + "Chatnamecolor: " + pgroup.getChatNameColor() + "COLOR" + ChatColor.RESET + ChatColor.WHITE + ".");
        sender.sendMessage(ChatColor.WHITE + "Tabnamecolor: " + pgroup.getTabNameColor() + "COLOR" + ChatColor.RESET + ChatColor.WHITE + ".");
        sender.sendMessage(ChatColor.WHITE + "Chatcolor: " + pgroup.getChatColor() + "COLOR" + ChatColor.RESET + ChatColor.WHITE + ".");
        sender.sendMessage(ChatColor.WHITE + "Group: " + pgroup.getName().toUpperCase() + ".");
    }

    // /prefix help
    public void help(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + "Prefix");
        sender.sendMessage(ChatColor.RED + "/prefix (prefix.prefix) -- no console");
        sender.sendMessage(ChatColor.RED + "/prefix [player] (prefix.prefix.others)");
        sender.sendMessage(ChatColor.RED + "/prefix help (prefix.prefix.help)");
        sender.sendMessage(ChatColor.RED + "/prefix chat [group] [prefix] (prefix.prefix.chat)");
        sender.sendMessage(ChatColor.RED + "/prefix tab [group] [prefix] (prefix.prefix.tab)");
        sender.sendMessage(ChatColor.RED + "/prefix chatnamecolor [group] [color] (prefix.prefix.chatnamecolor)");
        sender.sendMessage(ChatColor.RED + "/prefix tabnamecolor [group] [color] (prefix.prefix.tabnamecolor)");
        sender.sendMessage(ChatColor.RED + "/prefix chatcolor [group] [color] (prefix.prefix.chatcolor)");
        sender.sendMessage(ChatColor.RED + "/prefix weight [group] [weight] (prefix.prefix.weight)");
        sender.sendMessage(ChatColor.RED + "/prefix group [player | group] [group | empty] (prefix.prefix.group)");
        sender.sendMessage(ChatColor.RED + "/prefix reload (prefix.prefix.reload)");
    }

    // /prefix chat [group] [prefix]
    public void chat(CommandSender sender, String commandLabel, String[] args) {
        if (!(args.length >= 2)) {
            sender.sendMessage(ChatColor.RED + "You did not use the correct format, please use /prefix chat [group] [prefix].");
            return;
        }

        StringBuilder prefix = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i == args.length-1) {
                prefix.append(args[i]);
                break;
            }
            prefix.append(args[i]).append(" ");
        }

        PGroup pgroup = new PGroup(args[0], module);
        pgroup.setChatPrefix(String.valueOf(prefix));
        sender.sendMessage(ChatColor.GREEN + "The chatprefix has successfully been changed.");
    }

    // /prefix tab [group] [prefix]
    public void tab(CommandSender sender, String commandLabel, String[] args) {
        if (!(args.length >= 2)) {
            sender.sendMessage(ChatColor.RED + "You did not use the correct format, please use /prefix tab [group] [prefix].");
            return;
        }

        StringBuilder prefix = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i == args.length-1) {
                prefix.append(args[i]);
                break;
            }
            prefix.append(args[i]).append(" ");
        }

        PGroup pgroup = new PGroup(args[0], module);
        pgroup.setTabPrefix(String.valueOf(prefix));
        sender.sendMessage(ChatColor.GREEN + "The tabprefix has successfully been changed.");

        for (Player player : Bukkit.getOnlinePlayers()) {
            module.tablist.update(player);
        }
    }

    // /prefix chatnamecolor [group] [color]
    public void chatnamecolor(CommandSender sender, String commandLabel, String[] args) {
        if (!(args.length >= 2)) {
            sender.sendMessage(ChatColor.RED + "You did not use the correct format, please use /prefix chatnamecolor [group] [color].");
            return;
        }

        String color = args[1].substring(1, 2);
        if (!colors.contains(color)) {
            sender.sendMessage(ChatColor.RED + "This color does not exist.");
            return;
        }

        PGroup pgroup = new PGroup(args[0], module);
        pgroup.setChatNameColor(args[1].substring(0, 2));
        sender.sendMessage(ChatColor.GREEN + "The chatnamecolor has successfully been changed.");
    }

    // /prefix tabnamecolor [group] [color]
    public void tabnamecolor(CommandSender sender, String commandLabel, String[] args) {
        if (!(args.length >= 2)) {
            sender.sendMessage(ChatColor.RED + "You did not use the correct format, please use /prefix tabnamecolor [group] [color].");
            return;
        }

        String color = args[1].substring(1, 2);
        if (!colors.contains(color)) {
            sender.sendMessage(ChatColor.RED + "This color does not exist.");
            return;
        }

        PGroup pgroup = new PGroup(args[0], module);
        pgroup.setTabNameColor(args[1].substring(0, 2));
        sender.sendMessage(ChatColor.GREEN + "The tabnamecolor has successfully been changed.");

        for (Player player : Bukkit.getOnlinePlayers()) {
            module.tablist.update(player);
        }
    }

    // /prefix chatcolor [group] [color]
    public void chatcolor(CommandSender sender, String commandLabel, String[] args) {
        if (!(args.length >= 2)) {
            sender.sendMessage(ChatColor.RED + "You did not use the correct format, please use /prefix chatcolor [group] [color].");
            return;
        }

        String color = args[1].substring(1, 2);
        if (!colors.contains(color)) {
            sender.sendMessage(ChatColor.RED + "This color does not exist.");
            return;
        }

        PGroup pgroup = new PGroup(args[0], module);
        pgroup.setChatColor(args[1].substring(0, 2));
        sender.sendMessage(ChatColor.GREEN + "The chatcolor has successfully been changed.");
    }

    // /prefix weight [group] [weight]
    public void weight(CommandSender sender, String commandLabel, String[] args) {
        if (!(args.length >= 2)) {
            sender.sendMessage(ChatColor.RED + "You did not use the correct format, please use /prefix weight [group] [weight].");
            return;
        }

        int weight = 0;
        try {
            weight = Integer.parseInt(args[1]);
        } catch (Exception exception) {
            sender.sendMessage(ChatColor.RED + "You did not enter a number format.");
            return;
        }

        PGroup pgroup = new PGroup(args[0], module);
        pgroup.setTabWeight(weight);
        sender.sendMessage(ChatColor.GREEN + "The tabweight has successfully been changed.");

        for (Player player : Bukkit.getOnlinePlayers()) {
            module.tablist.update(player);
        }
    }

    // /prefix group [player | group] [group | empty]
    public void group(CommandSender sender, String commandLabel, String[] args) {
        if (!(args.length >= 2)) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    PPlayer pplayer = new PPlayer(player.getName(), module);
                    pplayer.setPGroup(args[0]);
                    player.sendMessage(ChatColor.GREEN + "Your group has successfully been changed.");
                    return;
                }
            }

            sender.sendMessage(ChatColor.RED + "You did not use the correct format, please use /prefix group [player | group] [group | empty].");
            return;
        }

        PPlayer pplayer = new PPlayer(args[0], module);
        pplayer.setPGroup(args[1]);
        sender.sendMessage(ChatColor.GREEN + "You successfully changed the group of " + args[0] + ".");
    }

    // /prefix reload
    public void reload(CommandSender sender, String commandLabel, String[] args) {
        module.groups.reloadConfig();
        module.players.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "The plugin has been reloaded!");
    }
}
