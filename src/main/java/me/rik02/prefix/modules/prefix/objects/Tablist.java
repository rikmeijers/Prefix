package me.rik02.prefix.modules.prefix.objects;

import me.rik02.prefix.modules.prefix.PrefixModule;
import me.rik02.prefix.objects.Log;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.logging.Level;

public final class Tablist {
    // Properties
    private final PrefixModule module;

    // Constructor
    public Tablist(PrefixModule module) {
        this.module = module;
    }

    // Methods
    private String getWeight(int weight) {
        String str = String.valueOf(weight);
        str = str.replace("9", "a");
        str = str.replace("8", "b");
        str = str.replace("7", "c");
        str = str.replace("6", "d");
        str = str.replace("5", "e");
        str = str.replace("4", "f");
        str = str.replace("3", "g");
        str = str.replace("2", "h");
        str = str.replace("1", "i");
        str = str.replace("0", "j");

        if (str.length() == 1) str = "j" + str;
        if (str.length() == 2) str = "j" + str;
        return str;
    }

    private Team getTeam(Scoreboard scoreboard, int weight, String prefix, ChatColor nameColor, Player player) {
        if (scoreboard == null) {
            new Log(Level.WARNING, "Scoreboard is null!");
            return null;
        }

        String teamName = getWeight(weight) + player.getName();
        if (teamName.length() > 15) teamName = teamName.substring(0, 15);

        prefix = prefix.replace('&', '§');
        if (!prefix.endsWith(" ") && !prefix.equals("")) prefix += " ";
        if (prefix.length() > 15) prefix = prefix.substring(0, 15);

        Team team = scoreboard.getTeam(teamName);
        if (team == null) team = scoreboard.registerNewTeam(teamName);
        if (!team.getPrefix().equals(prefix)) team.setPrefix(prefix);
        if (!team.getDisplayName().equals(teamName)) team.setDisplayName(teamName);
        if (!team.getColor().equals(nameColor)) team.setColor(nameColor);

        return team;
    }

    public void update(Player player) {
        PPlayer pplayer = new PPlayer(player.getName(), module);

        if (pplayer.getGroupName().equals("") || pplayer.getPGroup() == null) {
            Team team = getTeam(player.getScoreboard(), 0, "", ChatColor.GRAY, player);
            assert team != null;
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

            if (!team.hasEntry(player.getName())) team.addEntry(player.getName());
            return;
        }

        PGroup pgroup = pplayer.getPGroup();
        String tempTabNameColor = pgroup.getTabNameColor().substring(1, 2);
        ChatColor tabNameColor = ChatColor.GRAY;
        for (ChatColor color : ChatColor.values()) {
            if (color.equals(ChatColor.getByChar(tempTabNameColor))) tabNameColor = color;
        }

        Team team = getTeam(player.getScoreboard(), pgroup.getTabWeight(), pgroup.getTabPrefix(), tabNameColor, player);
        assert team != null;
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

        if (!team.hasEntry(player.getName())) team.addEntry(player.getName());
    }
}
