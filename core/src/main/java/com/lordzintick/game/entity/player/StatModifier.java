package com.lordzintick.game.entity.player;

import com.lordzintick.game.skill.SkillType;

import java.util.List;
import java.util.function.BiConsumer;

public class StatModifier {
    public final StatModifierType type;
    public final float amount;

    StatModifier(StatModifierType type, float amount) {
        this.type = type;
        this.amount = amount;
    }

    public void modify(Player player, List<String> targets) {
        type.modificator.accept(player, amount, targets);
    }

    public String getDescription(List<String> targets) {
        StringBuilder builder = new StringBuilder();

        if (!targets.contains("ALL")) {
            builder.append(" for ");

            if (targets.size() > 2) {
                for (int i = 0; i < targets.size(); i++) {
                    String target = targets.get(i);
                    if (i < targets.size() - 1) {
                        builder.append(target.replace("_", " ")).append(", ");
                    } else {
                        builder.append("and ").append(target.replace("_", " "));
                    }
                }
            } else if (targets.size() == 2) {
                builder.append(targets.get(0).replace("_", " ")).append(" and ").append(targets.get(1).replace("_", " "));
            } else if (!targets.isEmpty()) {
                builder.append(targets.get(0).replace("_", " "));
            }
        }

        return type.description.replace("%ah",
            String.valueOf(Math.round(amount * 100)))
            .replace("%a",
                String.valueOf(amount == Math.floor(amount) ? Math.floor(amount) : Math.floor(amount * 10) / 10))
            .replace("%t", builder.toString());
    }
}
