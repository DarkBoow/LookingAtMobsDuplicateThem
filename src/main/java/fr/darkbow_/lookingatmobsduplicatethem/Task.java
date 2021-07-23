package fr.darkbow_.lookingatmobsduplicatethem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task extends BukkitRunnable {
    public static boolean isRunning = false;
    public static int timer = 0;

    private Main main;

    public Task(Main main){this.main = main;}

    @Override
    public void run() {
        if(!isRunning){
            isRunning = true;
        }

        for(Player pls : Bukkit.getOnlinePlayers()){
            if(main.getConfig().getBoolean("gamemodes." + pls.getGameMode().toString().toLowerCase())){
                Location eye = pls.getEyeLocation();
                for(Entity e : pls.getNearbyEntities(30, 30, 30)){
                    if(e instanceof LivingEntity && !(e instanceof EnderDragon) && !(e instanceof Player)){
                        if(!main.getEtimers().containsKey(pls)){
                            Map<LivingEntity, Integer> emap = new HashMap<>();
                            main.getEtimers().put(pls, emap);
                        }

                        LivingEntity entity = (LivingEntity) e;

                        boolean copy = false;

                        if(main.getEtimers().get(pls).containsKey(entity)){
                            if(main.getEtimers().get(pls).get(entity) > 0){
                                main.getEtimers().get(pls).put(entity, main.getEtimers().get(pls).get(entity) - 1);
                            } else {
                                main.getEtimers().get(pls).put(entity, main.getDelay());
                                copy = true;
                            }
                        } else {
                            copy = true;
                            main.getEtimers().get(pls).put(entity, main.getDelay());
                        }

                        if(copy){
                            Vector toEntity = entity.getEyeLocation().toVector().subtract(eye.toVector());
                            double dot = toEntity.normalize().dot(eye.getDirection());

                            List<Block> blocks = main.getLineOfSight(main.getHashSet(), 30, 1, pls);
                            Block targetblock = blocks.get(0);

                            if(dot > 0.99 && pls.getEyeLocation().distance(entity.getLocation()) <= pls.getEyeLocation().distance(targetblock.getLocation())){
                                Entity clone = main.cloneEntity(entity);
                                if(clone instanceof LivingEntity){
                                    LivingEntity livingClone = (LivingEntity) clone;
                                    main.getEtimers().get(pls).put(livingClone, main.getDelay());
                                }
                            }
                        }
                    }
                }
            }
        }

        if(timer >= 1000){
            timer = 0;
        }

        timer++;
    }
}