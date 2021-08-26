import java.util.Random;

//An Individual is simply a key
public class Key {
    char[] genes = new char[26];
    double fitness;

    public Key(Random r){
        for (int i = 0; i < genes.length; i++) {
            genes[i] = (char)(r.nextInt(26)+'a');
        }
    }
    public void calcFitness(){
        String keyGen = new String(genes);
        this.fitness = Fitness.fitness(keyGen, GA.decryptMe);
    }
}
