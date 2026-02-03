package com.github.lordzintick.java_game.game.entity.player;

import com.github.lordzintick.java_game.game.accessory.Accessory;
import com.github.lordzintick.pixel_krush.core.util.Identifier;

import java.util.List;

public class StatModifier<T extends StatModifierType> {
    public final T type;
    public final float amount;

    StatModifier(T type, float amount) {
        this.type = type;
        this.amount = amount;
    }

    public void modify(Player player, List<Identifier> targets) {
        type.modificator.accept(player, amount, targets);
    }

    public String getDescription(List<Identifier> targets) {
        StringBuilder builder = new StringBuilder();

        if (!targets.contains(Accessory.ALL)) {
            builder.append(" for ");

            if (targets.size() > 2) {
                for (int i = 0; i < targets.size(); i++) {
                    String target = targets.get(i).toString();
                    if (i < targets.size() - 1) {
                        builder.append(target.replace("_", " ")).append(", ");
                    } else {
                        builder.append("and ").append(target.replace("_", " "));
                    }
                }
            } else if (targets.size() == 2) {
                builder.append(targets.get(0).toString().replace("_", " ")).append(" and ").append(targets.get(1).toString().replace("_", " "));
            } else if (!targets.isEmpty()) {
                builder.append(targets.get(0).toString().replace("_", " "));
            }
        }

        return type.description.replace("%ah",
            String.valueOf(Math.round(amount * 100)))
            .replace("%a",
                String.valueOf(amount == Math.floor(amount) ? Math.floor(amount) : Math.floor(amount * 10) / 10))
            .replace("%t", builder.toString());
    }
}
