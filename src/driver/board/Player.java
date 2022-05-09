package board;

import cards.*;

import java.util.ArrayList;
import java.util.Locale;

public class Player {

    private Hand h;
    private Display d;
    private int score;
    private int handlimit;
    private int sticks;

    public Player() {
        h = new Hand();
        d = new Display();
        d.add(new Pan());
        score = 0;
        handlimit = 8;
        sticks = 0;
    }

    public int getScore() {
        return score;
    }

    public int getHandLimit() {
        return handlimit;
    }

    public int getStickNumber() {
        return sticks;
    }

    public void addSticks(int i) {
        sticks += i;
        for (int j = 0; j < i; j++) {
            d.add(new Stick());
        }
    }

    public void removeSticks(int i) {
        sticks -= i;
        int size = d.size();
        for (int j = size - 1; j >= 0 && i > 0; j--) {
            if (d.getElementAt(j).getType().equals(CardType.STICK)) {
                d.removeElement(j);
                i--;
            }
        }

    }

    public Hand getHand() {
        return h;
    }

    public Display getDisplay() {
        return d;
    }

    public void addCardtoHand(Card card) {
        if (card.getType().equals(CardType.BASKET)) {
            d.add(card);
            handlimit += 2;
        } else {
            h.add(card);
        }
    }

    public void addCardtoDisplay(Card card) {
        d.add(card);
    }

    public boolean takeCardFromTheForest(int i) {
        if (i - 2 > sticks) {
            return false;
        }
        Card c = Board.getForest().getElementAt(8 - i);
        if (c.getType().equals(CardType.BASKET)) {
            d.add(Board.getForest().removeCardAt(8 - i));
            handlimit += 2;
            if (i > 2) {
                removeSticks(i - 2);
            }
            return true;
        }
        if (h.size() == handlimit) {
            return false;
        }
        if (i > 2) {
            removeSticks(i - 2);
        }
        h.add(Board.getForest().removeCardAt(8 - i));

        return true;
    }

    public boolean takeFromDecay() {
        ArrayList<Card> decayPile = Board.getDecayPile();
        int decaySize = decayPile.size();
        int basketNum = 0;
        for (int i = 0; i < decaySize; i++) {
            if (decayPile.get(i).getType().equals(CardType.BASKET)) {
                basketNum++;
            }
        }

        if (h.size() + decaySize - basketNum <= handlimit + 2 * basketNum) {
            for (int i = 0; i < basketNum; i++) {
                d.add(new Basket());
            }
            for (Card c : decayPile) {
                if (!c.getType().equals(CardType.BASKET)) {
                    h.add(c);
                }
            }
            handlimit += 2 * basketNum;
            return true;
        }

        return false;
    }

    public boolean cookMushrooms(ArrayList<Card> mushrooms) {
        int panNum = 0;
        int butterNum = 0;
        int ciderNum = 0;
        int dayNum = 0;
        int nightNum = 0;
        int flavourPoint = 0;
        String mushroomName = "";
        for (Card c : mushrooms) {
            if (c.getType().equals(CardType.DAYMUSHROOM) || c.getType().equals(CardType.NIGHTMUSHROOM)) {
                if (c.getType().equals(CardType.DAYMUSHROOM)) {
                    dayNum++;
                } else {
                    nightNum++;
                }
                if (mushroomName == "") {
                    mushroomName = c.getName();
                } else if (!mushroomName.equals(c.getName())) {
                    return false;
                }
            } else if (c.getType().equals(CardType.PAN)) {
                panNum++;
            } else if (c.getType().equals(CardType.BUTTER)) {
                butterNum++;
            } else if (c.getType().equals(CardType.CIDER)) {
                ciderNum++;
            } else {
                return false;
            }
        }
        if (panNum == 0) {
            for (int i = 0; i < d.size(); i++) {
                if (d.getElementAt(i).getType().equals(CardType.PAN)) {
                    panNum = 1;
                    break;
                }
            }
        }
        if (panNum != 1) {
            return false;
        }

        if (dayNum + nightNum * 2 < 3) {
            return false;
        }
        if (dayNum + nightNum * 2 < 4 * butterNum + 5 * ciderNum) {
            return false;
        }
        switch (mushroomName) {
            case "honeyfungus":
            case "treeear": flavourPoint = 1;break;
            case "lawyerswig":
            case "shiitake": flavourPoint = 2;break;
            case "chanterelle": flavourPoint = 4;break;
            case "morel": flavourPoint = 6; break;
            default: flavourPoint = 3;
        }

        score += flavourPoint * (dayNum + 2 * nightNum) + butterNum * 3 + ciderNum * 5;

        return true;
    }

    public boolean sellMushrooms(String name, int num) {
        if (num < 2) {
            return false;
        }
        String canonicalName = name.replaceAll("[^a-zA-Z]", "").toLowerCase();
        int stickValue = 0;
        int count = 0;

        for (int i = 0; i < h.size(); i++) {
            Card c = h.getElementAt(i);
            if (c.getName().equals(canonicalName)) {
                if (c.getType().equals(CardType.DAYMUSHROOM)) {
                    count++;
                } else {
                    count += 2;
                }
            }
        }

        if (count < num) {
            return false;
        }

        switch (canonicalName) {
            case "honeyfungus":
            case "lawyerswig":
            case "henofwoods": stickValue = 1; break;
            case "porcini": stickValue = 3; break;
            case "morel": stickValue = 4; break;
            default: stickValue = 2;
        }
        addSticks(stickValue * num);

        for (int i = 0; i < h.size() && num >= 2; i++) {
            Card c = h.getElementAt(i);
            if (c.getName().equals(canonicalName) && c.getType().equals(CardType.NIGHTMUSHROOM) && num >= 2) {
                num -= 2;
                h.removeElement(i);
            }
        }
        for (int i = h.size() - 1; i >= 0 && num >= 0; i--) {
            Card c = h.getElementAt(i);
            if (c.getName().equals(canonicalName) && c.getType().equals(CardType.DAYMUSHROOM)) {
                num--;
                h.removeElement(i);
            }
        }


        return true;
    }

    public boolean putPanDown() {
        for (int i = 0; i < h.size(); i++) {
            if (h.getElementAt(i).getType().equals(CardType.PAN)) {
                d.add(h.removeElement(i));
                return true;
            }
        }
        return false;
    }

}
