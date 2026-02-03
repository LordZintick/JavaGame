package com.lordzintick.java_game.game.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.lordzintick.pixel_krush.core.api.ecs.LivingEntity;
import com.lordzintick.java_game.game.WarningCircle;
import com.lordzintick.java_game.game.entity.HostileEntity;
import com.lordzintick.java_game.game.entity.player.Player;
import com.lordzintick.java_game.game.proj.MeleeAttack;
import com.lordzintick.java_game.game.proj.Projectile;
import com.lordzintick.java_game.game.proj.StationaryProjectile;

import java.util.Optional;

public final class SkillHelper {
    public static Vector3 getWorldMousePos(OrthographicCamera cam) {
        return new Vector3(Gdx.input.getX() + cam.position.x, -Gdx.input.getY() + Gdx.graphics.getHeight() + cam.position.y, 0);
    }

    public static MeleeAttack triggerMeleeAttack(String sheetname, LivingEntity entity, Optional<String> skillID, Behaviour config) {
        OrthographicCamera cam = entity.screen.game.getCamera();
        cam.update();
        Vector2 entityPos = new Vector2(entity.x + (float) entity.width / 4 * entity.scale, entity.y + (float) entity.height / 4 * entity.scale);
        Vector2 direction;

        if (entity instanceof Player) {
            Vector3 mousePos = getWorldMousePos(cam);
            direction = new Vector2(mousePos.x, mousePos.y).sub(entityPos);
        } else {
            Vector2 targetPos = new Vector2(((HostileEntity) entity).player.x, ((HostileEntity) entity).player.y);
            direction = new Vector2(targetPos.x, targetPos.y).sub(entityPos);
        }

        float damageMult = (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillDamageMultiplier(entity.screen.game.queryRegistryOrThrow(entity.screen.game.getId("skill_types")).getOrThrow(entity.screen.game.getId(skillID.get()))) : 1);
        MeleeAttack attack = new MeleeAttack(
            entity.screen, entity, direction.angleDeg() - 45,
            config.projWidth, config.projHeight, entity.screen.game.getAssetOrThrow(sheetname), (int) (config.defaultDamage * damageMult),
            config.frameTime, config.lifeTime, config.aimAtCursor, config.tick, config.hit, entity instanceof Player
        );
        attack.x = entityPos.x;
        attack.y = entityPos.y;

        entity.screen.objects.add(attack);
        return attack;
    }

    public static StationaryProjectile createStationaryProjectile(String sheetname, LivingEntity entity, Optional<String> skillID, Behaviour config) {
        OrthographicCamera cam = entity.screen.game.getCamera();
        cam.update();
        Vector2 entityPos = new Vector2(entity.x + (float) entity.width / 4 * entity.scale, entity.y + (float) entity.height / 4 * entity.scale);

        float damageMult = (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillDamageMultiplier(entity.screen.game.queryRegistryOrThrow(entity.screen.game.getId("skill_types")).getOrThrow(entity.screen.game.getId(skillID.get()))) : 1);
        StationaryProjectile projectile = new StationaryProjectile(
            entity.screen, entity,
            config.projWidth, config.projHeight, entity.screen.game.getAssetOrThrow(sheetname), (int) (config.defaultDamage * damageMult),
            config.frameTime, config.lifeTime, config.aimAtCursor, config.tick, config.hit, entity instanceof Player
        );
        projectile.x = entityPos.x - (float) projectile.width * 4;
        projectile.y = entityPos.y - projectile.height * 4;

        return projectile;
    }

    public static void placeStationaryProjectile(String sheetname, LivingEntity entity, boolean spawnAtCursor, Optional<String> skillID, Behaviour config) {
        StationaryProjectile projectile = createStationaryProjectile(sheetname, entity, skillID, config);
        if (spawnAtCursor) {
            Vector3 mousePos = getWorldMousePos(entity.screen.game.getCamera());
            projectile.x = mousePos.x - (float) projectile.width / 2;
            projectile.y = mousePos.y - (float) projectile.height / 2;
        }
        entity.screen.objects.add(projectile);
    }

    public static void wrapWarning(StationaryProjectile projectile, boolean positionAtCursor, float warningTime) {
        float maxDim = Math.max(projectile.width, projectile.height) * 8;
        if (positionAtCursor) {
            Vector3 mousePos = getWorldMousePos(projectile.screen.game.getCamera());
            projectile.x = mousePos.x;
            projectile.y = mousePos.y;
        }
        WarningCircle warningCircle = new WarningCircle(projectile.screen, projectile.x + maxDim / 2, projectile.y + maxDim / 2, projectile, warningTime, (int) maxDim);
        warningCircle.screen.objects.add(warningCircle);
    }

    public static Projectile shootProjectile(String sheetname, LivingEntity entity, Optional<String> skillID, Behaviour config) {
        OrthographicCamera cam = entity.screen.game.getCamera();
        cam.update();
        Vector2 entityPos = new Vector2(entity.x + (float) entity.width / 4 * entity.scale, entity.y + (float) entity.height / 4 * entity.scale);
        Vector2 direction;

        if (entity instanceof Player) {
            //Vector3 mousePos = new Vector3(Gdx.input.getX() + cam.position.x, -Gdx.input.getY() + Gdx.graphics.getHeight() + cam.position.y, 0);
            Vector3 mousePos = getWorldMousePos(cam);
            direction = new Vector2(mousePos.x, mousePos.y).sub(entityPos);
        } else {
            Vector2 targetPos = new Vector2(((HostileEntity) entity).player.x, ((HostileEntity) entity).player.y);
            direction = new Vector2(targetPos.x, targetPos.y).sub(entityPos);
        }

        float damageMult = (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillDamageMultiplier(entity.screen.game.queryRegistryOrThrow(entity.screen.game.getId("skill_types")).getOrThrow(entity.screen.game.getId(skillID.get()))) : 1);
        Projectile projectile = new Projectile(
            entity.screen, entity, direction.x, direction.y,
            config.projWidth, config.projHeight, entity.screen.game.getAssetOrThrow(sheetname), (int) (config.defaultDamage * damageMult),
            config.frameTime, config.speed / (entity instanceof Player ? 1 : 2.5f),
            (entity instanceof Player ? config.pierce : 0), config.aimAtCursor, config.tick, config.hit, entity instanceof Player
        );
        projectile.x = entityPos.x;
        projectile.y = entityPos.y;

        entity.screen.objects.add(projectile);
        return projectile;
    }

    public static Projectile shootProjectile(String sheetname, LivingEntity entity, float x, float y, float angle, Optional<String> skillID, Behaviour config) {
        float damageMult = (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillDamageMultiplier(entity.screen.game.queryRegistryOrThrow(entity.screen.game.getId("skill_types")).getOrThrow(entity.screen.game.getId(skillID.get()))) : 1);
        Projectile projectile = new Projectile(
            entity.screen, entity, MathUtils.cosDeg(angle), MathUtils.sinDeg(angle),
            config.projWidth, config.projHeight, entity.screen.game.getAssetOrThrow(sheetname), (int) (config.defaultDamage * damageMult),
            config.frameTime, config.speed / (entity instanceof Player ? 1 : 2.5f),
            (entity instanceof Player ? config.pierce : 0), config.aimAtCursor, config.tick, config.hit, entity instanceof Player
        );
        projectile.x = x;
        projectile.y = y;
        projectile.angle = angle - 90;

        entity.screen.objects.add(projectile);
        return projectile;
    }
}
