package fr.darkbow_.lookingatmobsduplicatethem;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockIterator;

import java.util.*;

public class Main extends JavaPlugin {

    private Main instance;

    public Main getInstance() {
        return instance;
    }

    public static BukkitTask task;

    private Set<Material> HashSet;
    private Map<Player, Map<LivingEntity, Integer>> etimers;
    private int delay = 5;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        getCommand("lookduplimobs").setExecutor(new Command(this));

        this.HashSet = new HashSet<Material>();
        this.etimers = new HashMap<>();

        delay = getConfig().getInt("delay");

        for(Material mat : Material.values()){
            if(!mat.isSolid()){
                HashSet.add(mat);
            }
        }

        System.out.println("[LookingDuplicateMobs] Plugin ON!");
        task = new Task(this).runTaskTimer(this, 2L, 2L);
        Task.isRunning = true;
    }

    @Override
    public void onDisable() {
        System.out.println("[LookingDuplicateMobs] Plugin OFF!");
    }

    public List<Block> getLineOfSight(Set<Material> transparent, int maxDistance, int maxLength, Player player) {
        if (transparent == null) {
            transparent = Sets.newHashSet(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);
        }

        if (maxDistance > 120) {
            maxDistance = 120;
        }

        ArrayList<Block> blocks = new ArrayList<Block>();
        Iterator<Block> itr = new BlockIterator(player, maxDistance);
        while (itr.hasNext()) {
            Block block = itr.next();
            blocks.add(block);
            if (maxLength != 0 && blocks.size() > maxLength) {
                blocks.remove(0);
            }
            Material material = block.getType();
            if (!transparent.contains(material)) {
                break;
            }
        }
        return blocks;
    }

    public Set<Material> getHashSet(){
        return HashSet;
    }

    public Entity cloneEntity(Entity entity) {
        Entity clone = entity.getWorld().spawnEntity(entity.getLocation(), entity.getType());

        clone.setVelocity(entity.getVelocity());
        clone.setCustomName(entity.getCustomName());
        clone.setCustomNameVisible(entity.isCustomNameVisible());
        clone.setInvulnerable(entity.isInvulnerable());
        clone.setFallDistance(entity.getFallDistance());
        clone.setFireTicks(entity.getFireTicks());
        clone.setGlowing(entity.isGlowing());
        clone.setGravity(entity.hasGravity());
        clone.setLastDamageCause(entity.getLastDamageCause());
        clone.setPersistent(entity.isPersistent());
        clone.setPortalCooldown(entity.getPortalCooldown());
        clone.setSilent(entity.isSilent());
        clone.setTicksLived(entity.getTicksLived());
        clone.setOp(entity.isOp());
        if(entity.isInsideVehicle()){
            Objects.requireNonNull(entity.getVehicle()).addPassenger(clone);
        }

        if(entity instanceof LivingEntity){
            LivingEntity lentity = (LivingEntity) entity;
            LivingEntity newliventity = (LivingEntity) clone;

            newliventity.setAI(lentity.hasAI());
            newliventity.setCollidable(lentity.isCollidable());
            newliventity.setArrowCooldown(lentity.getArrowCooldown());
            newliventity.setArrowsInBody(lentity.getArrowsInBody());
            newliventity.setLastDamage(lentity.getLastDamage());
            if(lentity.isLeashed()){newliventity.setLeashHolder(lentity.getLeashHolder());}
            newliventity.setInvisible(lentity.isInvisible());
            newliventity.setCanPickupItems(lentity.getCanPickupItems());
            newliventity.setGliding(lentity.isGliding());
            newliventity.setMaximumAir(lentity.getMaximumAir());
            newliventity.setMaximumNoDamageTicks(lentity.getMaximumNoDamageTicks());
            newliventity.setSwimming(lentity.isSwimming());
            newliventity.setRemainingAir(lentity.getRemainingAir());
            newliventity.setRemoveWhenFarAway(lentity.getRemoveWhenFarAway());
            newliventity.setAbsorptionAmount(lentity.getAbsorptionAmount());
            newliventity.setHealth(lentity.getHealth());
            newliventity.setOp(lentity.isOp());
            newliventity.setMaxHealth(lentity.getMaxHealth());
            newliventity.getScoreboardTags().addAll(lentity.getScoreboardTags());
            newliventity.addPotionEffects(lentity.getActivePotionEffects());

            if(newliventity instanceof Monster){
                Monster oldmonster = (Monster) entity;
                Monster newmonster = (Monster) newliventity;

                newmonster.setAware(oldmonster.isAware());
                newmonster.setLootTable(oldmonster.getLootTable());
                newmonster.setTarget(oldmonster.getTarget());
            }

            if(newliventity instanceof Sheep){
                Sheep oldsheep = (Sheep) lentity;
                Sheep newsheep = (Sheep) newliventity;

                newsheep.setSheared(oldsheep.isSheared());
                newsheep.setAge(oldsheep.getAge());
                newsheep.setAgeLock(oldsheep.getAgeLock());
                newsheep.setBreed(oldsheep.canBreed());
                newsheep.setBreedCause(oldsheep.getBreedCause());
                newsheep.setColor(oldsheep.getColor());
            }

            if(newliventity instanceof Ravager){
                Ravager oldravager = (Ravager) lentity;
                Ravager newravager = (Ravager) newliventity;

                newravager.setCanJoinRaid(oldravager.isCanJoinRaid());
            }

            if(newliventity instanceof Animals){
                Animals oldanimal = (Animals) lentity;
                Animals newanimal = (Animals) newliventity;

                newanimal.setBreedCause(oldanimal.getBreedCause());
                newanimal.setLoveModeTicks(oldanimal.getLoveModeTicks());
            }

            if(newliventity instanceof Slime){
                Slime oldslime = (Slime) entity;
                Slime newslime = (Slime) newliventity;

                newslime.setTarget(oldslime.getTarget());
                newslime.setSize(oldslime.getSize());
                newslime.setAware(oldslime.isAware());
            }

            if(newliventity instanceof Zombie){
                Zombie oldzombie = (Zombie) lentity;
                Zombie newzombie = (Zombie) newliventity;
                newzombie.setAge(oldzombie.getAge());
                if(oldzombie.isAdult()){newzombie.setAdult();} else {newzombie.setBaby();}
            }

            if(newliventity instanceof Wolf){
                Wolf oldwolf = (Wolf) lentity;
                Wolf newwolf = (Wolf) newliventity;

                newwolf.setOwner(oldwolf.getOwner());
                newwolf.setCollarColor(oldwolf.getCollarColor());
                newwolf.setAngry(oldwolf.isAngry());
                newwolf.setTarget(oldwolf.getTarget());
            }
        }
        return clone;
    }

    public Map<Player, Map<LivingEntity, Integer>> getEtimers() {
        return etimers;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}