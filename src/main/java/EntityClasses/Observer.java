package EntityClasses;
/**
 * Observer implementation
 @author Daniel Chu Jia Hao
 @version 1.1
 @since 2021-11-13
 */
public abstract class Observer {
    /**
     * A subject
     */
    protected Subject subject;

    /**
     * A function to enforce observer to listen the changes of a subject
     */
    public abstract void updatePromotionSetMeal();
}
