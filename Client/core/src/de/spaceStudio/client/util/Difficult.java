package de.spaceStudio.client.util;

/**
 * Enum class for difficult values
 * <li>
 * <ul>0 = easy</ul>
 * <ul>1 = normal</ul>
 * </li>
 */
public enum Difficult {
    EASY(0),
    NORMAL(1);

    private final int difficult;

    Difficult(int difficultCode) {
        this.difficult = difficultCode;
    }

    public int getLevelCode() {
        return this.difficult;
    }

}


