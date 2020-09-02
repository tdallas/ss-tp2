package engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//This is used to generate multiple cases and create a csv with the time it took for each simulation to reach equilibrium
public class TimeFileGenerator {
    private FileWriter fw;
    private final BufferedWriter bw;

    public TimeFileGenerator(String filename, int numberOfRepetitions) {
        try {
            File directory = new File("out/");
            if (!directory.exists()) {
                directory.mkdir();
            }
            FileWriter pw = new FileWriter("out/r-" + numberOfRepetitions + "-" + filename + ".csv");
            pw.close();
            this.fw = new FileWriter("out/r-" + numberOfRepetitions + "-" + filename + ".csv", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bw = new BufferedWriter(fw);
        try {
            bw.write("n,t\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToCSV(int numberOfParticles, long time){
        try {
            bw.write(numberOfParticles + "," + time +"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFile(){
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
