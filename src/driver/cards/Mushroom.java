package cards;

public class Mushroom extends EdibleItem {

    protected int sticksPerMushroom;

    public Mushroom(CardType type, String name) {
        super(type, name);
    }

    public int getSticksPerMushroom() {
        return sticksPerMushroom;
    }

}

