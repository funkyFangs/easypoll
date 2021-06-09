package util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static constant.EasyPollPermission.POLL;
import static org.bukkit.ChatColor.*;
import static plugin.EasyPollPlugin.PREFIX;

/**
 * @author funkyFangs
 */
public class CommandUtil
{
    public static void messageError(@NotNull String message, @NotNull CommandSender sender)
    {
        message(message, DARK_RED, sender);
    }

    public static void message(@NotNull String message, @NotNull ChatColor defaultColor, @NotNull CommandSender sender)
    {
        sender.sendMessage(formatMessage(message, defaultColor));
    }

    public static void message(@NotNull String message, @NotNull CommandSender sender)
    {
        message(message, WHITE, sender);
    }

    public static void message(@NotNull String message, @NotNull ChatColor defaultColor)
    {
        Bukkit.getServer().broadcast(formatMessage(message, defaultColor), POLL);
    }

    public static void message(@NotNull String message)
    {
        message(message, WHITE);
    }

    private static String formatMessage(String message, ChatColor defaultColor)
    {
        return String.format("%s%s%s", PREFIX, defaultColor,
                             message.replace(RESET.toString(), RESET + defaultColor.toString()));
    }

    public static String[] removeElement(@NotNull String[] array, int index)
    {
        int length = array.length;
        String[] result = new String[length - 1];
        System.arraycopy(array, 0, result, 0, index);
        System.arraycopy(array, index + 1, result, index, length - index - 1);
        return result;
    }

    public static UUID getUUID(@NotNull CommandSender sender)
    {
        return sender instanceof Player
                       ? ((Player) sender).getUniqueId()
                       : null;
    }
}