package phase1;
import physics.*;

public interface Gadget {    
    public void addTrigger(Gadget gadget);
    public void collide(Ball ball);
    public void action();
    //everyone's action method will be something like
    //1. do the collision/reflection
    //1. while doing this gadget's action ( e.g. use reflectrotatingwall when using flipper)
    //2. call the next gadget.action() if it's not null
    //recursive call!
    
    
}
