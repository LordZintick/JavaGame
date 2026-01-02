package com.lordzintick.game.entity;

import java.util.HashMap;
import java.util.Random;

public final class EntityHelper {
    public static final HashMap<String, HostileEntityFactory<? extends HostileEntity>> ENTITIES = new HashMap<>();
    public static final HashMap<String, Integer> WEIGHTS = new HashMap<>();

    public static final HostileEntityFactory<Zombie> ZOMBIE = register("zombie", 100, Zombie::new);
    public static final HostileEntityFactory<EvilWizard> EVIL_WIZARD = register("evil_wizard", 10, EvilWizard::new);
    public static final HostileEntityFactory<Skeleton> SKELETON = register("skeleton", 50, Skeleton::new);
    public static final HostileEntityFactory<IrradiatedZombie> IRRADIATED_ZOMBIE = register("irradiated_zombie", 5, IrradiatedZombie::new);
    public static final HostileEntityFactory<FrostWalker> FROST_WALKER = register("frost_walker", 35, FrostWalker::new);
    public static final HostileEntityFactory<Barbarian> BARBARIAN = register("barbarian", 20, Barbarian::new);

    public static int getTotalWeight() {
        int totalWeight = 0;
        for (int weight : WEIGHTS.values()) {
            totalWeight += weight;
        }
        return totalWeight;
    }

    public static <T extends HostileEntity> HostileEntityFactory<T> getWeightedRandomEntity(Random rand) {
        int totalWeight = getTotalWeight();
        int val = rand.nextInt(totalWeight);
        String entityID = "";

        for (String id : WEIGHTS.keySet()) {
            int weight = WEIGHTS.get(id);
            val -= weight;
            if (val <= 0) {
                entityID = id;
                break;
            }
        }

        return (HostileEntityFactory<T>) ENTITIES.get(entityID);
    }

    private static <T extends HostileEntity> HostileEntityFactory<T> register(String id, int weight, HostileEntityFactory<T> factory) {
        if (ENTITIES.containsKey(id))
            throw new IllegalArgumentException("Entity ID already exists!");
        if (WEIGHTS.containsKey(id))
            throw new IllegalArgumentException("Weight ID already exists!");

        ENTITIES.put(id, factory);
        WEIGHTS.put(id, weight);
        return factory;
    }
}
