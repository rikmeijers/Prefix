package me.rik02.prefix.modules.prefix.events;

import me.rik02.prefix.modules.prefix.PrefixModule;
import me.rik02.prefix.modules.prefix.objects.PGroup;
import me.rik02.prefix.modules.prefix.objects.PPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {
    // Properties
    private final PrefixModule module;

    // Constructor
    public PlayerChat(PrefixModule module) {
        this.module = module;
    }

    // Methods
    @EventHandler
    public void asyncPlayerChatListener(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PPlayer pplayer = new PPlayer(player.getName(), module);
        String message = event.getMessage();
        String[] messageParts = message.split(" ");

        String format;
        String chatColor;
        if (pplayer.getGroupName().isEmpty() || pplayer.getPGroup() == null) {
            chatColor = "§7";
            format = ChatColor.GRAY + player.getName() + ": " + chatColor;
        } else {
            PGroup pgroup = pplayer.getPGroup();
            chatColor = pgroup.getChatColor();

            if (pgroup.getChatPrefix().isEmpty()) {
                format = pgroup.getChatNameColor().replace('&', '§') + player.getName() + ": " + chatColor;
            } else {
                format = pgroup.getChatPrefix().replace('&', '§') + " " + pgroup.getChatNameColor().replace('&', '§') + player.getName() + ": " + chatColor;
            }
        }

        for (Player recipient : event.getRecipients()) {
            StringBuilder messageFormat = new StringBuilder(format);
            boolean foundName = false;
            for (String part : messageParts) {
                boolean found = false;
                if (part.equalsIgnoreCase(recipient.getName())) {
                    foundName = true;
                    found = true;
                }

                if (found) messageFormat.append(ChatColor.BOLD).append(part).append(ChatColor.RESET).append(chatColor).append(" ");
                else messageFormat.append(part).append(" ");
            }

            if (foundName) {
                event.getRecipients().remove(recipient);
                recipient.sendMessage(String.valueOf(messageFormat));
            }
        }

        event.setFormat(format + message);
    }
}
