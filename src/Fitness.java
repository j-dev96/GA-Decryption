import static java.lang.Math.abs;

public class Fitness {

  //Decrypt text using the provided key
  public static String decrypt(String k, String c) {

    //Sanitize the cipher and the key
    String cipher = c.toLowerCase();
    cipher = cipher.replaceAll("[^a-z]", "");
    cipher = cipher.replaceAll("\\s", "");

    String ke = k.toLowerCase();
    ke = ke.replaceAll("[^a-z]", "");
    ke = ke.replaceAll("\\s", "");

    char[] key = ke.toCharArray();
    for (int i = 0; i < key.length; i++) key[i] = (char) (key[i] - 97);

    //Run the decryption
    String plain = "";
    int keyPtr = 0;
    for (int i = 0; i < cipher.length(); i++) {
      //Ignore any value not in the expected range
      while (key[keyPtr % key.length] > 25 || key[keyPtr % key.length] < 0) {
        keyPtr++;
      }
      plain += ((char) (((cipher.charAt(i) - 97 + 26 - key[keyPtr % key.length]) % 26) + 97));
      keyPtr++;
    }
    return plain;
  }

  //Encrypt text using the provided key
  public static String encrypt(String k, String p) {

    //Sanitize the cipher and the key
    String plain = p.toLowerCase();
    plain = plain.replaceAll("[^a-z]", "");
    plain = plain.replaceAll("\\s", "");
    String cipher = "";

    String ke = k.toLowerCase();
    ke = ke.replaceAll("[^a-z]", "");
    ke = ke.replaceAll("\\s", "");

    char[] key = ke.toCharArray();
    for (int i = 0; i < key.length; i++) key[i] = (char) (key[i] - 97);

    //Encrypt the text
    int keyPtr = 0;
    for (int i = 0; i < plain.length(); i++) {
      //Ignore any characters outside the expected range
      while (key[keyPtr % key.length] > 25 || key[keyPtr % key.length] < 0) {
        keyPtr++;
      }
      cipher += ((char) (((plain.charAt(i) - 97 + key[keyPtr % key.length]) % 26) + 97));
      keyPtr++;
    }
    return cipher;
  }

  //This is a very simply fitness function based on the expected frequency of each letter in english
  //There is lots of room for improvement in this function.
  public static double fitness(String k, String c) {
    //The expected frequency of each character in english language text according to
    //http://practicalcryptography.com/cryptanalysis/letter-frequencies-various-languages/english-letter-frequencies/
    double[] expectedFrequencies = new double[26];
    expectedFrequencies[0] = 0.085;
    expectedFrequencies[1] = 0.016;
    expectedFrequencies[2] = 0.0316;
    expectedFrequencies[3] = 0.0387;
    expectedFrequencies[4] = 0.121;
    expectedFrequencies[5] = 0.0218;
    expectedFrequencies[6] = 0.0209;
    expectedFrequencies[7] = 0.0496;
    expectedFrequencies[8] = 0.0733;
    expectedFrequencies[9] = 0.0022;
    expectedFrequencies[10] = 0.0081;
    expectedFrequencies[11] = 0.0421;
    expectedFrequencies[12] = 0.0253;
    expectedFrequencies[13] = 0.0717;
    expectedFrequencies[14] = 0.0747;
    expectedFrequencies[15] = 0.0207;
    expectedFrequencies[16] = 0.001;
    expectedFrequencies[17] = 0.0633;
    expectedFrequencies[18] = 0.0673;
    expectedFrequencies[19] = 0.0894;
    expectedFrequencies[20] = 0.0268;
    expectedFrequencies[21] = 0.0106;
    expectedFrequencies[22] = 0.0183;
    expectedFrequencies[23] = 0.0019;
    expectedFrequencies[24] = 0.0172;
    expectedFrequencies[25] = 0.0011;

    //Sanitize the cipher text and key
    String d = c.toLowerCase();
    d = d.replaceAll("[^a-z]", "");
    d = d.replaceAll("\\s", "");
    int[] cipher = new int[c.length()];
    for (int x = 0; x < c.length(); x++) {
      cipher[x] = ((int) d.charAt(x)) - 97;
    }

    String ke = k.toLowerCase();
    ke = ke.replaceAll("[^a-z]", "");
    ke = ke.replaceAll("\\s", "");

    char[] key = ke.toCharArray();
    for (int i = 0; i < key.length; i++) key[i] = (char) (key[i] - 97);


    int[] charCounts = new int[26];
    for (int i = 0; i < charCounts.length; i++) charCounts[i] = 0;

    int[] plain = new int[cipher.length];

    //Decrypt each character
    int keyPtr = 0;
    for (int i = 0; i < cipher.length; i++) {
      //If the key contains any character outside [a-z] skip it
      while (key[keyPtr] < 0 || key[keyPtr] > 25) keyPtr = (keyPtr + 1) % key.length;

      plain[i] = ((26 + cipher[i] - key[keyPtr]) % 26);
      keyPtr = (keyPtr + 1) % key.length;
    }

    //Count the occurences of each character
    for (int x : plain) {
      charCounts[x]++;
    }
    //Calculate the total difference between the expected frequencies and the actual frequencies
    double score = 0;
    for (int y = 0; y < charCounts.length; y++) {
      score += abs((((float) charCounts[y]) / plain.length) - expectedFrequencies[y]);
    }

    return score;
  }
}
