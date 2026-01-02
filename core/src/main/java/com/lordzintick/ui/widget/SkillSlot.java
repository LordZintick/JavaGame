package com.lordzintick.ui.widget;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lordzintick.core.Logger;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.MainGameScreen;
import com.lordzintick.game.skill.Skill;
import com.lordzintick.util.BaseScreen;

public class SkillSlot extends Widget {
    private static final Logger LOGGER = new Logger(SkillSlot.class);

    public Skill slottedSkill = null;
    private final Player player;
    private final int index;

    public SkillSlot(BaseScreen screen, Player player, int x, int y, int index) {
        super(screen, 128, 128);
        this.player = player;
        this.index = index;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        super.render(batch, deltaTime);
        if (!visible) return;

        if (slottedSkill == null) {
            batch.draw(screen.game.slotTexture, x, y, width, height);
        } else {
            slottedSkill.tick(deltaTime);
            batch.setColor(slottedSkill.type.rarity.color);
            batch.draw(screen.game.slotTexture, x, y, width, height);

            batch.setColor(Color.WHITE);

            if (index < 3) {
                float cooldownValue = slottedSkill.getRemainingCooldown() / (slottedSkill.type.cooldown * player.getSkillCooldownMultiplier(slottedSkill.type));
                batch.draw(screen.game.cooldownTexture, x, y, width, Math.max(0, height * cooldownValue));
            }

            batch.draw(slottedSkill.type.icon, x + (float) width / 4, y + (float) height / 4, (float) width / 2, (float) height / 2);

            if (hovering) {
                slottedSkill.displayTooltip(batch, player, screen.game, x + width, y + height);
            }
        }
    }

    @Override
    public void click(int button) {
        if (button == Input.Buttons.LEFT && player.skillPoints > 0) {
            if (player.equippingSkill != null && index < 3) {
                LOGGER.log("Equipping spell \"" + player.equippingSkill.type.displayName + "\" into slot " + index);
                slottedSkill = player.equippingSkill;
                player.equippedSkills[index] = player.equippingSkill;
                ((MainGameScreen) player.screen).skillSlots[index].slottedSkill = player.equippingSkill;
                player.equippingSkill = null;
                player.skillPoints--;
                if (player.skillPoints <= 0) {
                    screen.resume();
                }
                return;
            }

            if (player.equippingSkill == null && index >= 3 && this.slottedSkill != null) {
                LOGGER.log("Selecting spell \"" + slottedSkill.type.displayName + "\" from slot " + index);
                player.equippingSkill = slottedSkill;
            }
        }
    }
}
