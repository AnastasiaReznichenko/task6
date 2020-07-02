import java.util.Comparator;

public class Collocation {
    private String collocation;
    private int frequency;

    Collocation(String collocation, int frequency) {
        this.collocation = collocation;
        this.frequency = frequency;
    }

    public int getFrequency() { return frequency; }
    public String getCollocation() { return collocation; }

    public Comparator<Collocation> comparator() {
        return Comparator.comparingInt(Collocation::getFrequency);
    }

    public void increaseFrequency() { frequency++; }
}
