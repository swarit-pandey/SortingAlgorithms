package VTULabExperiments;

import java.io.*;
import java.util.Random;
//sizes + "_" + x + ".txt"
public class SortingTest {
    public static void main(String[] args) {
        int x = 0;
        while(x < 5) {
            try {
                int[] size = new int[]{5000, 10999, 99989, 100020, 99987, 56099, 1000099, 10000999, 89898, 999999};
                for (int sizes : size) {
                    Writer wr = new FileWriter(sizes + "_" + x + ".txt");
                    Random rand = new Random();
                    for(int i = 0; i < sizes; ++i) {
                        int n = rand.nextInt();
                        wr.write(n + "\n");
                    }
                    wr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            x++;
        }
    }
}
