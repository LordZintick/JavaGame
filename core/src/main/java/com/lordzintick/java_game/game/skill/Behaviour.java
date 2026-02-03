package com.lordzintick.java_game.game.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.pixel_krush.core.api.ecs.LivingEntity;
import com.lordzintick.java_game.game.entity.effect.BleedEffect;
import com.lordzintick.java_game.game.entity.effect.BurnEffect;
import com.lordzintick.java_game.game.entity.effect.PoisonEffect;
import com.lordzintick.java_game.game.entity.effect.SlowEffect;
import com.lordzintick.java_game.game.proj.Projectile;

import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;

public final class Behaviour {
    public final int projWidth, projHeight, defaultDamage, pierce;
    public final float speed, frameTime, lifeTime;
    public final boolean aimAtCursor;
    public final BiConsumer<Projectile, Float> tick;
    public final BiConsumer<Projectile, LivingEntity> hit;

    public Behaviour(int projWidth, int projHeight, int defaultDamage, float speed, int pierce, float frameTime, float lifeTime, boolean aimAtCursor, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, LivingEntity> hit) {
        this.projWidth = projWidth;
        this.projHeight = projHeight;
        this.defaultDamage = defaultDamage;
        this.speed = speed;
        this.pierce = pierce;
        this.frameTime = frameTime;
        this.lifeTime = lifeTime;
        this.aimAtCursor = aimAtCursor;
        this.tick = tick;
        this.hit = hit;
    }

    public Behaviour(int projWidth, int projHeight, int defaultDamage, float speed, int pierce, float lifeTime, boolean aimAtCursor, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, LivingEntity> hit) {
        this.projWidth = projWidth;
        this.projHeight = projHeight;
        this.defaultDamage = defaultDamage;
        this.speed = speed;
        this.pierce = pierce;
        this.frameTime = (float) Math.pow(0.8, speed / 80);
        this.lifeTime = lifeTime;
        this.aimAtCursor = aimAtCursor;
        this.tick = tick;
        this.hit = hit;
    }

    // MAGE
    public static final Behaviour FIREBALL = new Behaviour(
        8, 8, 1, 800, 0, 0, false, (projectile, deltaTime) -> {
        Random random = projectile.screen.game.getRandom();
        projectile.screen.addParticle(
            new TextureRegion[]{projectile.screen.game.getCachedAtlas("particles").get(0, 0), projectile.screen.game.getCachedAtlas("particles").get(1, 0)},
            projectile.x + random.nextInt(projectile.width * 8),
            projectile.y + random.nextInt(projectile.height * 8),
            new Vector4(random.nextFloat(-100, 100), random.nextFloat(-100, 100), -4f, 10f),
            5f,
            0.1f,
            5f
        );
    }, (projectile, enemy) -> {
        if (projectile.screen.game.getRandom().nextInt(3) == 0) {
            enemy.applyEffect(new BurnEffect(projectile.screen.game, 5, 1));
        }
    });
    public static final Behaviour ICEBALL = new Behaviour(
        8, 8, 3, 400, 1, 0, false, (projectile, deltaTime) -> {
        Random random = projectile.screen.game.getRandom();
        projectile.screen.addParticle(
            new TextureRegion[]{projectile.screen.game.getCachedAtlas("particles").get(2, 0), projectile.screen.game.getCachedAtlas("particles").get(3, 0)},
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
    public static final Behaviour ACIDBALL = new Behaviour(
        8, 8, 2, 600, 0, 0, false, (projectile, deltaTime) -> {
        Random random = projectile.screen.game.getRandom();
        projectile.screen.addParticle(
            new TextureRegion[]{projectile.screen.game.getCachedAtlas("particles").get(2, 1)},
            projectile.x + random.nextInt(projectile.width * 8),
            projectile.y + random.nextInt(projectile.height * 8),
            new Vector4(random.nextFloat(-100, 100), random.nextFloat(100), -2.5f, 5f),
            5f,
            0.3f,
            6f
        );
    }, (projectile, enemy) -> {
        enemy.applyEffect(new PoisonEffect(projectile.screen.game, 10, 1));
    });
    public static final Behaviour WATERBALL = new Behaviour(
        8, 8, 1, 700, 4, 0, false, (projectile, deltaTime) -> {
        Random random = projectile.screen.game.getRandom();
        projectile.screen.addParticle(
            new TextureRegion[]{projectile.screen.game.getCachedAtlas("particles").get(10, 0), projectile.screen.game.getCachedAtlas("particles").get(11, 0)},
            projectile.x + random.nextInt(projectile.width * 8),
            projectile.y + random.nextInt(projectile.height * 8),
            new Vector4(random.nextFloat(-100, 100), random.nextFloat(-100, 100), -2.5f, 5f),
            5f,
            0.3f,
            6f
        );
    }, (projectile, enemy) -> {
            Vector2 normal = projectile.movementVector.cpy().nor();
            enemy.x += normal.x * 64;
            enemy.y += normal.y * 64;
    });
    public static final Behaviour LIGHTNINGBALL = new Behaviour(
        8, 8, 5, 1400, 0, 0, false, (projectile, deltaTime) -> {
        Random random = projectile.screen.game.getRandom();
        projectile.screen.addParticle(
            new TextureRegion[]{projectile.screen.game.getCachedAtlas("particles").get(0, 1), projectile.screen.game.getCachedAtlas("particles").get(1, 1)},
            projectile.x + random.nextInt(projectile.width * 8),
            projectile.y + random.nextInt(projectile.height * 8),
            new Vector4(random.nextFloat(-100, 100), random.nextFloat(-100, 100), -2.5f, 5f),
            5f,
            0.3f,
            6f
        );
    }, (projectile, enemy) -> {
    });
    public static final Behaviour FLOWERBALL = new Behaviour(
        8, 8, 2, 500, 0, 0, false, (projectile, deltaTime) -> {
        Random random = projectile.screen.game.getRandom();
        projectile.screen.addParticle(
            new TextureRegion[]{projectile.screen.game.getCachedAtlas("particles").get(8, 0), projectile.screen.game.getCachedAtlas("particles").get(9, 0)},
            projectile.x + random.nextInt(projectile.width * 8),
            projectile.y + random.nextInt(projectile.height * 8),
            new Vector4(random.nextFloat(-100, 100), random.nextFloat(-100, 100), -2.5f, 5f),
            5f,
            0.3f,
            6f
        );
    }, (projectile, enemy) -> {
            if (projectile.screen.game.getRandom().nextInt(4) == 0)
                projectile.owner.heal(1);
    });
    public static final Behaviour POISON_NOVA = new Behaviour(
        32, 32, 8, 0.25f, 999, 0.25f, 2, false, (projectile, deltaTime) -> {
    }, (projectile, enemy) -> {
        enemy.applyEffect(new PoisonEffect(projectile.screen.game, 10, 1));
    });
    public static final Behaviour LIGHTNING_BOLT = new Behaviour(
        8, 8, 2, 1400, 3, 0, false, (projectile, deltaTime) -> {
        Random random = projectile.screen.game.getRandom();
        projectile.screen.addParticle(
            new TextureRegion[]{projectile.screen.game.getCachedAtlas("particles").get(0, 1), projectile.screen.game.getCachedAtlas("particles").get(1, 1)},
            projectile.x + random.nextInt(projectile.width * 8),
            projectile.y + random.nextInt(projectile.height * 8),
            new Vector4(random.nextFloat(-100, 100), random.nextFloat(-100, 100), -5f, 10f),
            5f,
            0.1f,
            5f
        );
    }, (projectile, enemy) -> {
    });
    public static final Behaviour PLASMA_BOLT = new Behaviour(
        8, 8, 2, 600, 999, 0, false,
        (proj, delta) -> {
            Random random = proj.screen.game.getRandom();
            TextureRegion[][] particles = proj.screen.game.getCachedAtlas("particles").list();
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
        if (proj.screen.game.getRandom().nextInt(3) == 0) {
            ent.applyEffect(new BurnEffect(proj.screen.game, 5, 2));
        }
    }
    );
    public static final Behaviour LASER_BEAM = new Behaviour(
        64, 16, 8, 0, 999, 0.1f, 8, true, (proj, delta) -> {
            proj.screen.game.getAudioSample("laser_ambient").play();
    }, (proj, ent) -> {
            ent.applyEffect(new BurnEffect(proj.screen.game, 15, 2));
    }
    );
    public static final Behaviour WAVE = new Behaviour(
        16, 48, 5, 800, 999, 0, true, (proj, delta) -> {}, (proj, ent) -> {
        Vector2 normal = proj.movementVector.cpy().nor();
            ent.x += normal.x * 128;
            ent.y += normal.y * 128;
    }
    );
    public static final Behaviour PETAL = new Behaviour(
        6, 4, 1, 650, 0, 0, true, (proj, delta) -> {}, (proj, ent) -> {
        if (proj.screen.game.getRandom().nextInt(10) == 0)
            proj.owner.heal(1);
    }
    );
    public static final Behaviour PETALSTORM_BALL = new Behaviour(
        8, 8, 3, 300, 8, 0.25f, 0, false, (proj, delta) -> {
            proj.angle += 1;
            if (proj.generalTicks >= 0.75f) {
                proj.resetGeneralTicks();
                for (int i = 0; i < 4; i++) {
                    float angle = 45 + proj.angle + i * 90;
                    SkillHelper.shootProjectile("textures/game/skills/petal.png", proj.owner, proj.x + proj.width * 4, proj.y + proj.height * 4, angle, Optional.of("petalstorm"), Behaviour.PETAL);
                }
            }
    }, (proj, ent) -> {
        if (proj.screen.game.getRandom().nextInt(8) == 0)
            proj.owner.heal(1);
    }
    );
    public static final Behaviour LIGHTNING_STRIKE = new Behaviour(
        32, 32, 15, 0, 999, 0.5f, 1f, false, (proj, delta) -> {}, (proj, ent) -> {}
    );



    // WARRIOR
    public static final Behaviour SLASH = new Behaviour(
        16, 16, 2, 0, 999, 0.05f, 0.15f,
        true, (proj, delta) -> {}, (proj, ent) -> {
            if (proj.screen.game.getRandom().nextInt(3) == 0) {
                ent.applyEffect(new BleedEffect(proj.screen.game, 3, 1));
            }
        }
    );

    public static final Behaviour AXE = new Behaviour(
        8, 8, 2, 700, 2, 0.25f, 1f, false, (proj, delta) -> {}, (proj, ent) -> {
        if (proj.screen.game.getRandom().nextInt(2) == 0) {
            ent.applyEffect(new BleedEffect(proj.screen.game, 8, proj.screen.game.getRandom().nextInt(2) + 1));
        }
    }
    );

    public static final Behaviour WHIRLWIND = new Behaviour(
        8, 8, 1, 1200, 4, 0.1f, 3f, false, (proj, delta) -> {}, (proj, ent) -> {
        if (proj.screen.game.getRandom().nextInt(5) == 0) {
            ent.applyEffect(new BleedEffect(proj.screen.game, 5, 1));
        }
    }
    );

    public static final Behaviour MEGA_SLASH = new Behaviour(
        48, 48, 10, 0, 999, 0.25f, 0.75f,
        true, (proj, delta) -> {}, (proj, ent) -> {
        ent.applyEffect(new BleedEffect(proj.screen.game, 15, 3));
    }
    );
}
