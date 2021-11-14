package EntityClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Subject {

    /**
     * ArrayList of observers
     */
    private final List<Observer> observers = new ArrayList<Observer>();

    /**
     * ArrayList with 2 attributes
     * MenuItemID
     * Promotion price
     */
    private List<Map<String, Double>> state = new ArrayList<>();

    /**
     * function to get state of the menuItem
     * @return state which is an arraylist of maps of menuItemID and the associated promotion price
     *
     */
    public List<Map<String, Double>> getState() {
        return state;
    }

    /**
     * function to set state of the ArrayList of menuItemID with the corresponding promotion price
     * calls notifyAllObservers function to notify the subscribed subjects once state has been set
     * @param state listmap of menuItemID with corresponding promotion price
     */

    public void setState(List<Map<String, Double>> state) {
        this.state = state;
        notifyAllObservers();
    }

    /**
     * function to attach observer to this subject
     * @param observer observer to be subscribed
     */

    public void attach(Observer observer){
        observers.add(observer);
    }
    /**
     * function to remove observer to this subject
     * @param observer observer to be removed
     */
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    /**
     * function to notify all observers that are subscribed
     */
    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.updatePromotionSetMeal();
        }
    }
}
