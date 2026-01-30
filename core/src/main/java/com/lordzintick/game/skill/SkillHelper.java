package com.lordzintick.game.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.lordzintick.game.WarningCircle;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.entity.HostileEntity;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.entity.player.StatModifierType;
import com.lordzintick.game.proj.MeleeAttack;
import com.lordzintick.game.proj.Projectile;
import com.lordzintick.game.proj.StationaryProjectile;

import java.util.Optional;

public final class SkillHelper {
    public static Vector3 getWorldMousePos(OrthographicCamera cam) {
        return new Vector3(Gdx.input.getX() + cam.position.x, -Gdx.input.getY() + Gdx.graphics.getHeight() + cam.position.y, 0);
    }

    public static MeleeAttack triggerMeleeAttack(String sheetname, Entity entity, Optional<String> skillID, Behaviour config) {
        OrthographicCamera cam = entity.screen.game.camera;
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

        MeleeAttack attack = new MeleeAttack(
            entity.screen, entity, direction.angleDeg() - 45,
            config.projWidth, config.projHeight, entity.screen.game.assets.get(sheetname), (int) (config.defaultDamage * (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillCooldownMultiplier(entity.screen.game.skillTypes.SKILL_TYPES.get(skillID.get())) : 1)),
            config.frameTime, config.lifeTime, config.aimAtCursor, config.tick, config.hit, entity instanceof Player
        );
        attack.x = entityPos.x;
        attack.y = entityPos.y;

        entity.screen.queueAddObject(attack);
        return attack;
    }

    public static StationaryProjectile createStationaryProjectile(String sheetname, Entity entity, Optional<String> skillID, Behaviour config) {
        OrthographicCamera cam = entity.screen.game.camera;
        cam.update();
        Vector2 entityPos = new Vector2(entity.x + (float) entity.width / 4 * entity.scale, entity.y + (float) entity.height / 4 * entity.scale);

        StationaryProjectile projectile = new StationaryProjectile(
            entity.screen, entity,
            config.projWidth, config.projHeight, entity.screen.game.assets.get(sheetname), (int) (config.defaultDamage * (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillDamageMultiplier(entity.screen.game.skillTypes.SKILL_TYPES.get(skillID.get())) : 1)),
            config.frameTime, config.lifeTime, config.aimAtCursor, config.tick, config.hit, entity instanceof Player
        );
        projectile.x = entityPos.x - (float) projectile.width * 4;
        projectile.y = entityPos.y - projectile.height * 4;

        return projectile;
    }

    public static void placeStationaryProjectile(String sheetname, Entity entity, boolean spawnAtCursor, Optional<String> skillID, Behaviour config) {
        StationaryProjectile projectile = createStationaryProjectile(sheetname, entity, skillID, config);
        if (spawnAtCursor) {
            Vector3 mousePos = getWorldMousePos(entity.screen.game.camera);
            projectile.x = mousePos.x - (float) projectile.width / 2;
            projectile.y = mousePos.y - (float) projectile.height / 2;
        }
        entity.screen.queueAddObject(projectile);
    }

    public static WarningCircle wrapWarning(StationaryProjectile projectile, boolean positionAtCursor, float warningTime) {
        float maxDim = Math.max(projectile.width, projectile.height) * 8;
        if (positionAtCursor) {
            Vector3 mousePos = getWorldMousePos(projectile.screen.game.camera);
            projectile.x = mousePos.x;
            projectile.y = mousePos.y;
        }
        WarningCircle warningCircle = new WarningCircle(projectile.screen, projectile.x + maxDim / 2, projectile.y + maxDim / 2, projectile, warningTime, (int) maxDim);
        warningCircle.screen.queueAddObject(warningCircle);
        return warningCircle;
    }

    public static Projectile shootProjectile(String sheetname, Entity entity, Optional<String> skillID, Behaviour config) {
        OrthographicCamera cam = entity.screen.game.camera;
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

        Projectile projectile = new Projectile(
            entity.screen, entity, direction.x, direction.y,
            config.projWidth, config.projHeight, entity.screen.game.assets.get(sheetname), (int) (config.defaultDamage * (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillDamageMultiplier(entity.screen.game.skillTypes.SKILL_TYPES.get(skillID.get())) : 1)),
            config.frameTime, config.speed / (entity instanceof Player ? 1 : 2.5f),
            (entity instanceof Player ? config.pierce : 0), config.aimAtCursor, config.tick, config.hit, entity instanceof Player
        );
        projectile.x = entityPos.x;
        projectile.y = entityPos.y;

        entity.screen.queueAddObject(projectile);
        return projectile;
    }

    public static Projectile shootProjectile(String sheetname, Entity entity, float x, float y, float angle, Optional<String> skillID, Behaviour config) {
        Projectile projectile = new Projectile(
            entity.screen, entity, MathUtils.cosDeg(angle), MathUtils.sinDeg(angle),
            config.projWidth, config.projHeight, entity.screen.game.assets.get(sheetname), (int) (config.defaultDamage * (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillDamageMultiplier(entity.screen.game.skillTypes.SKILL_TYPES.get(skillID.get())) : 1)),
            config.frameTime, config.speed / (entity instanceof Player ? 1 : 2.5f),
            (entity instanceof Player ? config.pierce : 0), config.aimAtCursor, config.tick, config.hit, entity instanceof Player
        );
        projectile.x = x;
        projectile.y = y;
        projectile.angle = angle - 90;

        entity.screen.queueAddObject(projectile);
        return projectile;
    }
}
