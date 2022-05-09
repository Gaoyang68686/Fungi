package board;

import cards.Card;
import java.util.ArrayList;

public class Hand implements Displayable {

    private ArrayList<Card> handList = new ArrayList<>();

    public void add(Card card) {
        handList.add(card);
    }

    @Override
    public int size() {
        return handList.size();
    }

    @Override
    public Card getElementAt(int i) {
        return handList.get(i);
    }

    @Override
    public Card removeElement(int i) {
        return handList.remove(i);
    }
}
