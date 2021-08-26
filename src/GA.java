import java.util.*;

public class GA {

    public static Population POP = new Population();
    public static Key[] individuals;
    public static String decryptMe;
    public static double avgBest = 0;
    public static double avgPop = 0;

    public static void main(String[] args) {

        int popSize = 10;
        int maxGen = 1000;
        int k = 3;
        int elitValue = 1;
        individuals = new Key[k];
        double muRate = 0.1;
        double coRate = 0.9;
        Random r = new Random();
        long seed = r.nextLong();
        Random random = new Random();
        random.setSeed(seed);
        Scanner s = new Scanner(System.in);
        System.out.println("What message would you like me to decrypt?");
        decryptMe = s.nextLine();
        GA geneticAlgorithm = new GA();
        ArrayList<Pair> arrayList = new ArrayList<>();
        geneticAlgorithm.POP.initializePopulation(popSize, random);
        geneticAlgorithm.POP.calcAllFitness();
        for (int i = 0; i < maxGen; i++) {
            doElitism(elitValue);
            Key p1 = geneticAlgorithm.tournamentSelection(k, random); //parent 1 will be winner from k candidates
            Key p2 = geneticAlgorithm.tournamentSelection(k, random);
            if (random.nextDouble()<=coRate) uniformCrossover(p1, p2, random); //can change to twoPointCrossover/onePointCrossover if you like
            if (random.nextDouble()<=muRate) mutate(p1, p2, random);
            geneticAlgorithm.addMostFit(p1, p2);
            geneticAlgorithm.POP.calcAllFitness();
            System.out.println("Generation: "+(i+1));
            avgBest+= POP.getMostFit().fitness;
            avgPop+= POP.getAVGFit();
            System.out.println("Best Fitness Value: "+POP.getMostFit().fitness+" Average Fitness Value: "+POP.getAVGFit());
            arrayList.add(new Pair(POP.getMostFit(), POP.mostFit));
        }
        Pair[] pairArr = new Pair[arrayList.size()];
        for (int i = 0; i < pairArr.length; i++) {
            pairArr[i] = arrayList.get(i);
        }
        Arrays.sort(pairArr);
        String string = pairArr[0].KeytoString();
        System.out.println("Population Size: "+popSize+", Max Generations: "+maxGen+", K value: "+k+", Elitism Value: "+elitValue+", Crossover Rate: "+coRate+", Mutation Rate: "+muRate+", Seed: "+seed+" ");
        System.out.println("Average Best fitness: "+(avgBest/maxGen)+" Average Population fitness "+(avgPop/maxGen));
        System.out.println("Best Chromosome: "+string +" "+" Fitness: "+pairArr[0].fitness);

    }

    private static void doElitism(int elitValue) { //replace the least fit by the most fittest
        ArrayList<Key> ArrAsList = new ArrayList<>();
        for (int i = 0; i < POP.keys.length; i++) {
            ArrAsList.add(POP.keys[i]);
        }
        for (int i = 0; i < elitValue; i++) {
            POP.keys[POP.getLeastFitIndex()] = ArrAsList.remove(POP.getBestFitIndex());
        }
    }

    private static Key tournamentSelection(int k, Random random) {
        double probablilitytoSelect = 0.80; //chance that best candidate wont be chosen
        for (int i = 0; i < k; i++) {
            individuals[i] = POP.keys[random.nextInt(POP.keys.length)];
        }
        if (random.nextDouble()<= probablilitytoSelect) return findBestKey(individuals);
        else return POP.keys[random.nextInt(POP.keys.length)];
    }

    private static Key findBestKey(Key[] individuals) {
        double fit = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < individuals.length; i++) {
            individuals[i].calcFitness();
            if (fit >= individuals[i].fitness){
                fit = individuals[i].fitness;
                index = i;
            }
        }
        return individuals[index];
    }

    private static void onePointCrossover(Key p1, Key p2, Random random) {
        int point = random.nextInt(POP.keys.length);
        for (int i = point; i <= POP.keys.length; i++) {
            char temp = p1.genes[i];
            p1.genes[i] = p2.genes[i];
            p2.genes[i] = temp;
        }
    }

    private static void twoPointCrossover(Key p1, Key p2, Random random){
        int point1 = random.nextInt(POP.keys.length);
        int point2 = random.nextInt(POP.keys.length);
        while (point2==point1) point2 = random.nextInt(POP.keys.length);
        if (point2 > point1) {
            for (int i = point1; i <= point2; i++) {
                char temp = p1.genes[i];
                p1.genes[i] = p2.genes[i];
                p2.genes[i] = temp;
            }
        }
        else{
            for (int i = point2; i <= point1; i++) {
                char temp = p1.genes[i];
                p1.genes[i] = p2.genes[i];
                p2.genes[i] = temp;
            }
        }
    }

    private static void uniformCrossover(Key p1, Key p2, Random random){
        int[] mask = new int[p1.genes.length];
        for (int i = 0; i < mask.length; i++) {
            if (random.nextDouble()<= 0.5) mask[i] = 1;
            else mask[i] = 0;
        }
        for (int i = 0; i < p1.genes.length; i++) {
            if (mask[i] == 1) {
                char temp = p1.genes[i];
                p1.genes[i] = p2.genes[i];
                p2.genes[i] = temp;
            }
        }
    }

    private static void mutate(Key p1, Key p2, Random random) { //will swap two random genes in a key
        int f = random.nextInt(p1.genes.length);
        int s = random.nextInt(p1.genes.length);
        char temp = p1.genes[f];
        p1.genes[f] = p1.genes[s];
        p1.genes[s] = temp;
        f = random.nextInt(p2.genes.length);
        s = random.nextInt(p2.genes.length);
        temp = p2.genes[f];
        p2.genes[f] = p2.genes[s];
        p2.genes[s] = temp;
    }

    Key getFittestChild(Key p1, Key p2){
        if (p1.fitness > p2.fitness){
            return p1;
        }
        return p2;
    }

    private void addMostFit(Key p1, Key p2) {
        p1.calcFitness();
        p2.calcFitness();
        int leastFitIndex = POP.getLeastFitIndex();
        POP.keys[leastFitIndex] = getFittestChild(p1, p2);
    }

}
