package EntityClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Subject {
    private List<Observer> observers = new ArrayList<Observer>();
    /**
     * First paramter is menuItemID
     * Second paramter is promotinoPrice
     *
     */
    private List<Map<String, Double>> state = new ArrayList<>();
    public List<Map<String, Double>> getState() {
        return state;
    }

    public void setState(List<Map<String, Double>> state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }
    public void remove(Observer observer) {
        observers.remove(observer);
    }
    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.updatePromotionSetMeal();
        }
    }
}
