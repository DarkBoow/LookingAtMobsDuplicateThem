package fr.darkbow_.lookingatmobsduplicatethem;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command implements CommandExecutor {
    private Main main;
    public Command(Main main){this.main = main;}

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String msg, String[] args) {
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("show")){
                sender.sendMessage("§6§lActual Delay: §b" + main.getDelay() + " ticks");
            } else if(args[0].equalsIgnoreCase("set")){
                try {
                    main.setDelay(Integer.parseInt(args[1]));
                    main.getConfig().set("delay", args[1]);
                    sender.sendMessage("§aDelay set to §9§l" + main.getDelay() + " ticks§a.");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    sender.sendMessage("§cPlease provide a Valid Integer.");
                }
            }
        } else {
            sender.sendMessage("§6§lLooking Duplicate Mobs\n§7/ldm show §a> §bShows the current plugin delay.\n§7/ldm set <number> §a> §bChange the plugin delay.");
        }

        return false;
    }
}