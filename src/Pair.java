public class Pair implements Comparable<Pair>{//this helps store the best chromosome and its value

    public final Key string;
    public final double fitness;

    public Pair(Key string, double fitness){
        this.string = string;
        this.fitness = fitness;
    }

    public String KeytoString(){
        char[] temp = new char[string.genes.length];
        for (int i = 0; i < string.genes.length; i++) {
            temp[i] = string.genes[i];
        }
        return new String(temp);
    }
    @Override
    public int compareTo(Pair other) {
        return 1 *Double.valueOf(this.fitness).compareTo(other.fitness); //it will compare by fitness (the sort)
    }
}
