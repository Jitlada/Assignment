package f2.spw;

public class Alive{
    private int heart;
    public Alive(int heart){
        this.heart = heart;
    }
    public void setHeart(int heart){
        this.heart = heart;
    }
    public int getHeart(){
        return this.heart;
    }
    public void dec(){
        heart--;
    }
}