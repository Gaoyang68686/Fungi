package board;
import cards.Card;
import java.util.ArrayList;

public class CardList {

    private ArrayList<Card> cList;

    public CardList() {
        cList = new ArrayList<>();
    }

    public void add(Card card) {
        cList.add(card);
    }

    public int size() {
        return cList.size();
    }

    public Card getElementAt(int i) {
        return cList.get(i);
    }

    public Card removeCardAt(int i) {
        return cList.remove(i);
    }

}