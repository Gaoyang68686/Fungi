package board;

import cards.Card;
import java.util.ArrayList;

public class Display implements Displayable {

    private ArrayList<Card> displayList = new ArrayList<>();

    public void add(Card card) {
        displayList.add(card);
    }

    @Override
    public int size() {
        return displayList.size();
    }

    @Override
    public Card getElementAt(int i) {
        return displayList.get(i);
    }

    @Override
    public Card removeElement(int i) {
        return displayList.remove(i);
    }

}
