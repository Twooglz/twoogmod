package me.twooglz.twoogmod.hud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HUDText {
    private static TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    private List<String> textList = new ArrayList<>();
    public HUDText() {}

    public enum position {
        TOP_LEFT,    TOP,    TOP_RIGHT,
        LEFT,        CENTER, RIGHT,
        BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT,
    }
    public enum anchor {
        TOP_LEFT,    TOP,    TOP_RIGHT,
        LEFT,        CENTER, RIGHT,
        BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT,
    }

    public int getWidth() {
        return textRenderer.getWidth(this.textList.get(0));
    }

    public int getWidth(int index) {
        return textRenderer.getWidth(this.textList.get(index));
    }


    public int textHeight() {
        return textRenderer.fontHeight;
    }

    public int totalTextHeight(int index) {
        return textRenderer.fontHeight * this.textList.size();
    }

    public List<String> getTextArray() {
        return this.textList;
    }
    public HUDText line(String line) {
        this.textList.add(line);
        return this;
    }

    public void draw(DrawContext context, position position, anchor anchor, int xOffset, int yOffset) {
        int baseX = 0;
        int baseY = 0;

        switch (position) {
            case TOP_LEFT, TOP, TOP_RIGHT -> {
                baseY = 0;
            }
            case LEFT, CENTER, RIGHT -> {
                baseY = context.getScaledWindowHeight()/2;
            }
            case BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT -> {
                baseY = context.getScaledWindowHeight();
            }
        }
        switch (position) {
            case TOP_LEFT, LEFT, BOTTOM_LEFT -> {
                baseX = 0;
            }
            case TOP, CENTER, BOTTOM -> {
                baseX = context.getScaledWindowWidth() / 2;
            }
            case TOP_RIGHT, RIGHT, BOTTOM_RIGHT -> {
                baseX = context.getScaledWindowWidth();
            }
        }

        int i = 0;

        switch (anchor) {
            case TOP_LEFT -> {
                for (String line : textList) {
                    context.drawText(textRenderer,
                            line,
                            baseX + xOffset,
                            baseY + i * textHeight() + yOffset,
                            Color.WHITE.getRGB(),
                            true);
                    i++;
                }
            }
            case TOP -> {
                for (String line : textList) {
                    context.drawText(textRenderer,
                            line,
                            baseX - getWidth(i)/2  + xOffset,
                            baseY + i * textHeight() + yOffset,
                            Color.WHITE.getRGB(),
                            true);
                    i++;
                }
            }
            case TOP_RIGHT -> {
                for (String line : textList) {
                    context.drawText(textRenderer,
                            line,
                            baseX - getWidth(i) - xOffset,
                            baseY + i * textHeight() + yOffset,
                            Color.WHITE.getRGB(),
                            true);
                    i++;
                }
            }
            case LEFT -> {
                for (String line : textList) {
                    context.drawText(textRenderer,
                            line,
                            baseX + xOffset,
                            baseY + (i - textList.size()/2) * textHeight() + yOffset,
                            Color.WHITE.getRGB(),
                            true);
                    i++;
                }
            }
            case CENTER -> {
                for (String line : textList) {
                    context.drawText(textRenderer,
                            line,
                            baseX - getWidth(i)/2 + xOffset,
                            baseY + (i - textList.size()/2) * textHeight() + yOffset,
                            Color.WHITE.getRGB(),
                            true);
                    i++;
                }
            }
            case RIGHT -> {
                for (String line : textList) {
                    context.drawText(textRenderer,
                            line,
                            baseX - getWidth(i) - xOffset,
                            baseY + (i - textList.size()/2) * textHeight() + yOffset,
                            Color.WHITE.getRGB(),
                            true);
                    i++;
                }
            }
            case BOTTOM_LEFT -> {
                for (String line : textList) {
                    context.drawText(textRenderer,
                            line,
                            baseX + xOffset,
                            baseY + (i - textList.size()) * textHeight() - yOffset,
                            Color.WHITE.getRGB(),
                            true);
                    i++;
                }
            }
            case BOTTOM -> {
                for (String line : textList) {
                    context.drawText(textRenderer,
                            line,
                            baseX - getWidth(i)/2 + xOffset,
                            baseY + (i - textList.size()) * textHeight() - yOffset,
                            Color.WHITE.getRGB(),
                            true);
                    i++;
                }
            }
            case BOTTOM_RIGHT -> {
                for (String line : textList) {
                    context.drawText(textRenderer,
                            line,
                            baseX - getWidth(i) - xOffset,
                            baseY + (i - textList.size()) * textHeight() - yOffset,
                            Color.WHITE.getRGB(),
                            true);
                    i++;
                }
            }
        }
    }
}
