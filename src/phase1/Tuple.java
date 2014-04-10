package phase1;

/**
 * Subclass Tuple. Used to represent a square with coordinates (x, y) in the board.
 */
public class Tuple { 
    public final Integer x; 
    public final Integer y; 
    public Tuple(Integer x, Integer y) { 
      this.x = x; 
      this.y = y; 
    } 
    
    /**
     * @param obj other Object to compare this to.
     * @return boolean whether this equals obj or not.
     * necessary because hashmap uses equals() to compare keys in get() and put().
     */
    @Override
    public boolean equals(Object obj){
        if (obj == null) {return false;}
        if (obj.getClass() == Tuple.class) {
            Tuple other = (Tuple) obj;      
            return other.x.equals(this.x) && other.y.equals(this.y);
        }
        return false;
    }
    
    /**
     * @return int that represents this tuple.
     */
    @Override
    public int hashCode(){
        return this.x.hashCode()*this.y.hashCode();
    }
    
    /**
     * @return String that represent this tuple.
     */
    @Override
    public String toString(){
        return "("+this.x+" , "+this.y+")";
    }
}
