package sypztep.sifu.common.util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class ItemDescriptionHandler {
    private final List<DescriptionEntry> descriptions = new ArrayList<>();

    public void addDescription(String text, DescriptionType type) {
        descriptions.add(new DescriptionEntry(text, type));
    }

    public List<Text> getDescriptions() {
        List<Text> formattedDescriptions = new ArrayList<>();
        for (DescriptionEntry description : descriptions) {
            Text descriptionText = description.getText();
            if (descriptionText != null) {
                formattedDescriptions.add(descriptionText);
            }
        }
        return formattedDescriptions;
    }

    private record DescriptionEntry(String text, DescriptionType type) {
        public Text getText() {
            Formatting prefixColor = switch (type) {
                case PASSIVE, ON_HIT -> Formatting.GOLD;
                case LORE -> Formatting.GRAY;
                case SPECIAL -> Formatting.AQUA;
            };

            String prefixText = switch (type) {
                case PASSIVE -> "Passive: ";
                case ON_HIT -> "On Hit: ";
                case LORE -> "";
                case SPECIAL -> "Special: ";
            };
            return Text.literal(prefixText).formatted(prefixColor)
                    .append(Text.literal(text).formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
        }
    }

    public enum DescriptionType {
        PASSIVE,
        ON_HIT,
        LORE,
        SPECIAL
    }
}
