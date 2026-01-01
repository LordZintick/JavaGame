package com.lordzintick.game.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.entity.effect.BleedEffect;
import com.lordzintick.game.entity.effect.BurnEffect;
import com.lordzintick.game.entity.effect.PoisonEffect;
import com.lordzintick.game.entity.effect.SlowEffect;
import com.lordzintick.game.proj.Projectile;

import java.util.Random;
import java.util.function.BiConsumer;

public final class SkillConfig {
    public final int projWidth, projHeight, defaultDamage, pierce;
    public final float speed, frameTime, lifeTime;
    public final BiConsumer<Projectile, Float> tick;
    public final BiConsumer<Projectile, Entity> hit;

    public SkillConfig(int projWidth, int projHeight, int defaultDamage, float speed, int pierce, float frameTime, float lifeTime, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, Entity> hit) {
        this.projWidth = projWidth;
        this.projHeight = projHeight;
        this.defaultDamage = defaultDamage;
        this.speed = speed;
        this.pierce = pierce;
        this.frameTime = frameTime;
        this.lifeTime = lifeTime;
        this.tick = tick;
        this.hit = hit;
    }

    public SkillConfig(int projWidth, int projHeight, int defaultDamage, float speed, int pierce, float lifeTime, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, Entity> hit) {
        this.projWidth = projWidth;
        this.projHeight = projHeight;
        this.defaultDamage = defaultDamage;
        this.speed = speed;
        this.pierce = pierce;
        this.frameTime = (float) Math.pow(0.8, speed / 80);
        this.lifeTime = lifeTime;
        this.tick = tick;
        this.hit = hit;
    }

    // MAGE
    public static final SkillConfig FIREBALL = new SkillConfig(
        8, 8, 1, 800, 0, 0, (projectile, deltaTime) -> {
        Random random = projectile.screen.game.random;
        projectile.screen.addParticle(
            new TextureRegion[]{projectile.screen.game.particlesAtlas[0][0], projectile.screen.game.particlesAtlas[0][1]},
            projectile.x + random.nextInt(projectile.width * 8),
            projectile.y + random.nextInt(projectile.height * 8),
            new Vector4(random.nextFloat(-100, 100), random.nextFloat(-100, 100), -4f, 10f),
            5f,
            0.1f,
            5f
        );
    }, (projectile, enemy) -> {
        if (projectile.screen.game.random.nextInt(3) == 0) {
            enemy.applyEffect(new BurnEffect(projectile.screen.game, 5, 1));
        }
    });
    public static final SkillConfig ICEBALL = new SkillConfig(
        8, 8, 3, 400, 1, 0, (projectile, deltaTime) -> {
        Random random = projectile.screen.game.random;
        projectile.screen.addParticle(
            new TextureRegion[]{projectile.screen.game.particlesAtlas[0][2], projectile.screen.game.particlesAtlas[0][3]},
            projectile.x + random.nextInt(projectile.width * 8),
            projectile.y + random.nextInt(projectile.height * 8),
            new Vector4(random.nextFloat(-100, 100), random.nextFloat(-100, 100), -2.5f, 5f),
            5f,
            0.3f,
            6f
        );
    }, (projectile, enemy) -> {
        enemy.applyEffect(new SlowEffect(projectile.screen.game, 5, 2));
    });
    public static final SkillConfig POISON_NOVA = new SkillConfig(
        32, 32, 8, 0.25f, 2, 999, (projectile, deltaTime) -> {
    }, (projectile, enemy) -> {
        enemy.applyEffect(new PoisonEffect(projectile.screen.game, 10, 1));
    });
    public static final SkillConfig LIGHTNING_BOLT = new SkillConfig(
        8, 8, 2, 1400, 3, 0, (projectile, deltaTime) -> {
        Random random = projectile.screen.game.random;
        projectile.screen.addParticle(
            new TextureRegion[]{projectile.screen.game.particlesAtlas[1][0], projectile.screen.game.particlesAtlas[1][1]},
            projectile.x + random.nextInt(projectile.width * 8),
            projectile.y + random.nextInt(projectile.height * 8),
            new Vector4(random.nextFloat(-100, 100), random.nextFloat(-100, 100), -5f, 10f),
            5f,
            0.1f,
            5f
        );
    }, (projectile, enemy) -> {
    });
    public static final SkillConfig PLASMA_BOLT = new SkillConfig(
        8, 8, 1, 600, 999, 0,
        (proj, delta) -> {
            Random random = proj.screen.game.random;
            TextureRegion[][] particles = proj.screen.game.particlesAtlas;
            proj.screen.addParticle(
                new TextureRegion[]{particles[0][4], particles[0][5]},
                proj.x + random.nextInt(proj.width * 8),
                proj.y + random.nextInt(proj.height * 8),
                new Vector4(random.nextFloat(-100, 100), random.nextFloat(-100, 100), -2.5f, 5f),
                5f,
                0.3f,
                6f
            );
        }, (proj, ent) -> {
    }
    );



    // WARRIOR
    public static final SkillConfig SLASH = new SkillConfig(
        16, 16, 2, 0, 999, 0.05f, 0.15f,
        (proj, delta) -> {}, (proj, ent) -> {
            if (proj.screen.game.random.nextInt(3) == 0) {
                ent.applyEffect(new BleedEffect(proj.screen.game, 3, 1));
            }
        }
    );

    public static final SkillConfig AXE = new SkillConfig(
        8, 8, 2, 700, 2, 0.25f, 1f, (proj, delta) -> {}, (proj, ent) -> {
        if (proj.screen.game.random.nextInt(2) == 0) {
            ent.applyEffect(new BleedEffect(proj.screen.game, 8, proj.screen.game.random.nextInt(2) + 1));
        }
    }
    );

    public static final SkillConfig WHIRLWIND = new SkillConfig(
        8, 8, 1, 1200, 4, 0.1f, 3f, (proj, delta) -> {}, (proj, ent) -> {
        if (proj.screen.game.random.nextInt(5) == 0) {
            ent.applyEffect(new BleedEffect(proj.screen.game, 5, 1));
        }
    }
    );

    public static final SkillConfig MEGA_SLASH = new SkillConfig(
        48, 48, 10, 0, 999, 0.25f, 0.75f,
        (proj, delta) -> {}, (proj, ent) -> {
        ent.applyEffect(new BleedEffect(proj.screen.game, 15, 3));
    }
    );
}
