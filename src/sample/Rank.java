package sample;

public enum Rank {
    SPODNJIC(2),
    ZGORNJIC(3),
    KRALJ(4),
    DESET(10),
    AS(11);

    private final int rankValue;

    Rank(int rankValue) {
        this.rankValue = rankValue;
    }

    public int getRankValue() {
        return rankValue;
    }
}
