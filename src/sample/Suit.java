package sample;

public enum Suit {
    ZELOD("Zelod"),
    TIKEV("Tikev"),
    SRCE("Srce"),
    ZELJE("Zelje");

    private final String suitText;

    Suit(String suitText){
        this.suitText=suitText;
    }

    public String getSuitText() {
        return suitText;
    }


}
