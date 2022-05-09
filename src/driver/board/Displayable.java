package board;

import cards.Card;

public interface Displayable {

    public void add(Card c);

    public int size();

    public Card getElementAt(int i);

    public Card removeElement(int i);

}
