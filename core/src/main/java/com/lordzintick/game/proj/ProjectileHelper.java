package com.lordzintick.game.proj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.entity.HostileEntity;
import com.lordzintick.game.entity.Player;
import com.lordzintick.game.entity.effect.OnFireEffect;
import com.lordzintick.game.entity.effect.PoisonEffect;
import com.lordzintick.game.entity.effect.SlowEffect;

import java.util.Random;
import java.util.function.BiConsumer;

public final class ProjectileHelper {
    public static final class ProjConfig {
        public final int projWidth, projHeight, damage, pierce;
        public final float speed, frameTime, lifeTime;
        public final BiConsumer<Projectile, Float> tick;
        public final BiConsumer<Projectile, Entity> hit;

        public ProjConfig(int projWidth, int projHeight, int damage, float speed, int pierce, float frameTime, float lifeTime, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, Entity> hit) {
            this.projWidth = projWidth;
            this.projHeight = projHeight;
            this.damage = damage;
            this.speed = speed;
            this.pierce = pierce;
            this.frameTime = frameTime;
            this.lifeTime = lifeTime;
            this.tick = tick;
            this.hit = hit;
        }

        public ProjConfig(int projWidth, int projHeight, int damage, float speed, int pierce, float lifeTime, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, Entity> hit) {
            this.projWidth = projWidth;
            this.projHeight = projHeight;
            this.damage = damage;
            this.speed = speed;
            this.pierce = pierce;
            this.frameTime = (float) Math.pow(0.8, speed / 80);
            this.lifeTime = lifeTime;
            this.tick = tick;
            this.hit = hit;
        }

        public static final ProjConfig FIREBALL = new ProjConfig(
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
                enemy.applyEffect(new OnFireEffect(projectile.screen.game, 5, 1));
            }
        });
        public static final ProjConfig ICEBALL = new ProjConfig(
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
        public static final ProjConfig POISON_NOVA = new ProjConfig(
            16, 16, 8, 0.25f, 2, 999, (projectile, deltaTime) -> {
        }, (projectile, enemy) -> {
            enemy.applyEffect(new PoisonEffect(projectile.screen.game, 10, 1));
        });
        public static final ProjConfig LIGHTNING_BOLT = new ProjConfig(
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
        public static final ProjConfig PLASMA_BOLT = new ProjConfig(
            8, 8, 1, 700, 0, 0,
            (proj, delta) -> {
                Random random = proj.screen.game.random;
                TextureRegion[][] particles = proj.screen.game.particlesAtlas;
                proj.screen.addParticle(
                    new TextureRegion[] {particles[0][4], particles[0][5]},
                    proj.x + random.nextInt(proj.width * 8),
                    proj.y + random.nextInt(proj.height * 8),
                    new Vector4(random.nextFloat(-100, 100), random.nextFloat(-100, 100), -2.5f, 5f),
                    5f,
                    0.3f,
                    6f
                );
            }, (proj, ent) -> {}
        );
    }

    public static void placeStationaryProjectile(Texture sheet, Entity entity, ProjConfig config) {
        OrthographicCamera cam = entity.screen.game.camera;
        cam.update();
        Vector2 entityPos = new Vector2(entity.x + (float) entity.width / 4 * entity.scale, entity.y + (float) entity.height / 4 * entity.scale);
        Vector2 direction;

        if (entity instanceof Player) {
            Vector3 mousePos = new Vector3(Gdx.input.getX() + cam.position.x, -Gdx.input.getY() + Gdx.graphics.getHeight() + cam.position.y, 0);
            direction = new Vector2(mousePos.x, mousePos.y).sub(entityPos);
        } else {
            Vector2 targetPos = new Vector2(((HostileEntity) entity).player.x, ((HostileEntity) entity).player.y);
            direction = new Vector2(targetPos.x, targetPos.y).sub(entityPos);
        }

        StationaryProjectile projectile = new StationaryProjectile(
            entity.screen, entity, direction.x, direction.y,
            config.projWidth, config.projHeight, sheet, config.damage,
            config.frameTime, config.pierce, config.lifeTime, config.tick, config.hit, entity instanceof Player
        );
        projectile.x = entityPos.x;
        projectile.y = entityPos.y;

        entity.screen.queueAddObject(projectile);
    }

    public static void shootProjectile(Texture sheet, Entity entity, ProjConfig config) {
        OrthographicCamera cam = entity.screen.game.camera;
        cam.update();
        Vector2 entityPos = new Vector2(entity.x + (float) entity.width / 4 * entity.scale, entity.y + (float) entity.height / 4 * entity.scale);
        Vector2 direction;

        if (entity instanceof Player) {
            Vector3 mousePos = new Vector3(Gdx.input.getX() + cam.position.x, -Gdx.input.getY() + Gdx.graphics.getHeight() + cam.position.y, 0);
            direction = new Vector2(mousePos.x, mousePos.y).sub(entityPos);
        } else {
            Vector2 targetPos = new Vector2(((HostileEntity) entity).player.x, ((HostileEntity) entity).player.y);
            direction = new Vector2(targetPos.x, targetPos.y).sub(entityPos);
        }

        Projectile projectile = new Projectile(
            entity.screen, entity, direction.x, direction.y,
            config.projWidth, config.projHeight, sheet, config.damage,
            config.frameTime, config.speed / (entity instanceof Player ? 1 : 2),
            config.pierce, config.tick, config.hit, entity instanceof Player
        );
        projectile.x = entityPos.x;
        projectile.y = entityPos.y;

        entity.screen.queueAddObject(projectile);
    }

    @Deprecated
    public static void placeStationaryProjectile(Texture sheet, Entity entity, int projWidth, int projHeight, int damage, float frameTime, float lifeTime, int pierce, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, Entity> hit) {
        OrthographicCamera cam = entity.screen.game.camera;
        cam.update();
        Vector2 entityPos = new Vector2(entity.x + (float) entity.width / 4 * entity.scale, entity.y + (float) entity.height / 4 * entity.scale);
        Vector2 direction;

        if (entity instanceof Player) {
            Vector3 mousePos = new Vector3(Gdx.input.getX() + cam.position.x, -Gdx.input.getY() + Gdx.graphics.getHeight() + cam.position.y, 0);
            direction = new Vector2(mousePos.x, mousePos.y).sub(entityPos);
        } else {
            Vector2 targetPos = new Vector2(((HostileEntity) entity).player.x, ((HostileEntity) entity).player.y);
            direction = new Vector2(targetPos.x, targetPos.y).sub(entityPos);
        }

        StationaryProjectile projectile = new StationaryProjectile(
            entity.screen, entity, direction.x, direction.y,
            projWidth, projHeight, sheet, damage,
            frameTime, pierce, lifeTime, tick, hit, entity instanceof Player
        );
        projectile.x = entityPos.x;
        projectile.y = entityPos.y;

        entity.screen.queueAddObject(projectile);
    }

    @Deprecated
    public static void shootProjectile(Texture sheet, Entity entity, int projWidth, int projHeight, int damage, float speed, int pierce, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, Entity> hit) {
        OrthographicCamera cam = entity.screen.game.camera;
        cam.update();
        Vector2 entityPos = new Vector2(entity.x + (float) entity.width / 4 * entity.scale, entity.y + (float) entity.height / 4 * entity.scale);
        Vector2 direction;

        if (entity instanceof Player) {
            Vector3 mousePos = new Vector3(Gdx.input.getX() + cam.position.x, -Gdx.input.getY() + Gdx.graphics.getHeight() + cam.position.y, 0);
            direction = new Vector2(mousePos.x, mousePos.y).sub(entityPos);
        } else {
            Vector2 targetPos = new Vector2(((HostileEntity) entity).player.x, ((HostileEntity) entity).player.y);
            direction = new Vector2(targetPos.x, targetPos.y).sub(entityPos);
        }

        Projectile projectile = new Projectile(
            entity.screen, entity, direction.x, direction.y,
            projWidth, projHeight, sheet, damage,
            (float) Math.pow(0.8, speed / 80), speed / (entity instanceof Player ? 1 : 2),
            pierce, tick, hit, entity instanceof Player
        );
        projectile.x = entityPos.x;
        projectile.y = entityPos.y;

        entity.screen.queueAddObject(projectile);
    }
}
