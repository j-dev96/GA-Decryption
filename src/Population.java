import java.util.Random;

public class Population {
    Key[] keys;
    double mostFit = Double.MAX_VALUE;

    public void initializePopulation(int popSize, Random r){
        keys = new Key[popSize];
        for (int i = 0; i < popSize; i++) {
            keys[i] = new Key(r);
        }
    }

    public Key getMostFit(){
        double maxFit = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < keys.length; i++) {
            if (maxFit>=keys[i].fitness){
                maxFit = keys[i].fitness;
                index = i;
            }
        }
        mostFit = keys[index].fitness;
        return keys[index];
    }

    public void calcAllFitness(){
        for (int i = 0; i < keys.length; i++) {
            keys[i].calcFitness();
        }
        getMostFit();
    }

    public int getLeastFitIndex() {
        double minFit = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < keys.length; i++) {
            if (minFit<=keys[i].fitness){
                minFit=keys[i].fitness;
                index = i;
            }
        }
        return index;
    }

    public int getBestFitIndex(){
        double maxFit = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < keys.length; i++) {
            if (maxFit>=keys[i].fitness){
                maxFit = keys[i].fitness;
                index = i;
            }
        }
        return index;
    }

    public double getAVGFit() {
        double avgFit = 0;
        for (int i = 0; i < keys.length; i++) {
            avgFit+=keys[i].fitness;
        }
        return avgFit = avgFit/keys.length;
    }
}
