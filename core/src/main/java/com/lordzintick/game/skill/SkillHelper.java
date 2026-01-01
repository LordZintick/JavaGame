package com.lordzintick.game.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.entity.HostileEntity;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.proj.MeleeAttack;
import com.lordzintick.game.proj.Projectile;
import com.lordzintick.game.proj.StationaryProjectile;

import java.util.Optional;

public final class SkillHelper {
    public static MeleeAttack triggerMeleeAttack(Texture sheet, Entity entity, Optional<String> skillID, SkillConfig config) {
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

        MeleeAttack attack = new MeleeAttack(
            entity.screen, entity, direction.angleDeg() - 45,
            config.projWidth, config.projHeight, sheet, (int) (config.defaultDamage * (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillCooldownMultiplier(entity.screen.game.skillTypes.SKILL_TYPES.get(skillID.get())) : 1)),
            config.frameTime, config.pierce, config.lifeTime, config.tick, config.hit, entity instanceof Player
        );
        attack.x = entityPos.x;
        attack.y = entityPos.y;

        entity.screen.queueAddObject(attack);
        return attack;
    }

    public static StationaryProjectile placeStationaryProjectile(Texture sheet, Entity entity, Optional<String> skillID, SkillConfig config) {
        OrthographicCamera cam = entity.screen.game.camera;
        cam.update();
        Vector2 entityPos = new Vector2(entity.x + (float) entity.width / 4 * entity.scale, entity.y + (float) entity.height / 4 * entity.scale);

        StationaryProjectile projectile = new StationaryProjectile(
            entity.screen, entity,
            config.projWidth, config.projHeight, sheet, (int) (config.defaultDamage * (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillDamageMultiplier(entity.screen.game.skillTypes.SKILL_TYPES.get(skillID.get())) : 1)),
            config.frameTime, config.pierce, config.lifeTime, config.tick, config.hit, entity instanceof Player
        );
        projectile.x = entityPos.x;
        projectile.y = entityPos.y;

        entity.screen.queueAddObject(projectile);
        return projectile;
    }

    public static Projectile shootProjectile(Texture sheet, Entity entity, Optional<String> skillID, SkillConfig config) {
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
            config.projWidth, config.projHeight, sheet, (int) (config.defaultDamage * (entity instanceof Player && skillID.isPresent() ? ((Player) entity).getSkillDamageMultiplier(entity.screen.game.skillTypes.SKILL_TYPES.get(skillID.get())) : 1)),
            config.frameTime, config.speed / (entity instanceof Player ? 1 : 2.5f),
            (entity instanceof Player ? config.pierce : 0), config.tick, config.hit, entity instanceof Player
        );
        projectile.x = entityPos.x;
        projectile.y = entityPos.y;

        entity.screen.queueAddObject(projectile);
        return projectile;
    }
}
