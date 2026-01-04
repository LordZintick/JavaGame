package com.lordzintick.game.proj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.lordzintick.audio.Sound;
import com.lordzintick.core.Logger;
import com.lordzintick.game.AbstractGameObject;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.entity.HostileEntity;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

import java.util.function.BiConsumer;

public class Projectile extends AbstractGameObject {
    private static final Logger LOGGER = new Logger(Projectile.class);

    protected final boolean aimAtCursor;
    public float angle = 0;
    public Vector2 movementVector;
    public final Texture sheet;
    public final float damage;
    public final float frameTime;
    public float generalTicks = 0;
    protected float animTicks = 0;
    protected int frame = 0;
    protected final TextureRegion[][] splitFrames;
    private int pierce;
    private final BiConsumer<Projectile, Float> tick;
    private final BiConsumer<Projectile, Entity> hit;
    public final Entity owner;
    private final boolean friendly;

    /**
     * Constructs a new projectile in the provided screen
     *
     * @param screen The {@link AbstractGameScreen} that is the parent/holder of this projectile
     */
    public Projectile(AbstractGameScreen screen, Entity owner, float moveX, float moveY, int width, int height, Texture sheet, float damage, float frameTime, float speed, int pierce, boolean aimAtCursor, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, Entity> hit, boolean friendly) {
        super(screen);
        this.damage = damage;
        this.pierce = pierce;
        this.owner = owner;
        this.tick = tick;
        this.hit = hit;
        this.friendly = friendly;
        this.width = width;
        this.height = height;
        this.movementVector = new Vector2(moveX, moveY).nor().scl(speed);
        this.sheet = sheet;
        this.splitFrames = TextureRegion.split(sheet, width, height);
        this.frameTime = frameTime;
        this.aimAtCursor = aimAtCursor;
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        animTicks += deltaTime;
        if (animTicks >= frameTime) {
            animTicks = 0;
            if (frame >= splitFrames.length - 1) {
                frame = 0;
            } else {
                frame++;
            }
        }

        batch.draw(splitFrames[frame][0], x, y, 0, height * 4, width * 8, height * 8, 1, 1, angle);
    }

    @Override
    public void update(float deltaTime) {
        if (aimAtCursor) {
            angle = movementVector.angleDeg() - 90;

            this.collisionRect.set(
                x - width * 8 + MathUtils.cosDeg(movementVector.angleDeg()) * 50,
                y - height * 8 + MathUtils.sinDeg(movementVector.angleDeg()) * 50,
                width * 16,
                height * 16
            );
        } else {
            this.collisionRect.set(x, y, width * 8, height * 8);
        }

        generalTicks += deltaTime;
        tick.accept(this, deltaTime);
        x += movementVector.x * deltaTime;
        y += movementVector.y * deltaTime;

        if (Math.abs(x) >= Gdx.graphics.getWidth() * 2 || Math.abs(y) >= Gdx.graphics.getWidth() * 2) {
            shouldRemove = true;
        }
    }

    @Override
    public void collide(AbstractGameObject other) {
        if (other instanceof Entity && !other.shouldRemove && ((Entity) other).iframes <= 0) {
            if ((!friendly && other instanceof HostileEntity) || (friendly && other instanceof Player) || other == owner) return;

            hit.accept(this, (Entity) other);
            Entity entity = ((Entity) other);
            entity.damage(damage);

            if (pierce > 0) {
                pierce--;
            } else {
                this.shouldRemove = true;
            }
        }
    }

    public void resetGeneralTicks() {
        this.generalTicks = 0;
    }
}
