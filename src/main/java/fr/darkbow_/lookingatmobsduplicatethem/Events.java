package fr.darkbow_.lookingatmobsduplicatethem;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class Events implements Listener {
    private Main main;

    public Events(Main main){this.main = main;}

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Map<LivingEntity, Integer> emap = new HashMap<>();

        if(!main.getEtimers().containsKey(player)){
            main.getEtimers().put(player, emap);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        main.getEtimers().remove(player);
    }
}