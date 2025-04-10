package dev.akarah.actions;

import org.bukkit.entity.Entity;

public class Environment {
    Entity defaultEntity;

    public static Environment empty() {
        return new Environment();
    }

    public Entity getDefaultEntity() {
        return this.defaultEntity;
    }

    public Environment setDefaultEntity(Entity defaultEntity) {
        this.defaultEntity = defaultEntity;
        return this;
    }
}
