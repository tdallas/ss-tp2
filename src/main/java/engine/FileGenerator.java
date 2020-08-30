package engine;

import model.Cell;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {
    private FileWriter fw1;
    private FileWriter fw2;
    private final BufferedWriter bw1;
    private final BufferedWriter bw2;

    public FileGenerator(String filename) {
        try {
            File directory = new File("out/");
            if (!directory.exists()) {
                directory.mkdir();
            }
            FileWriter pw1 = new FileWriter("out/" + filename + ".xyz");
            FileWriter pw2 = new FileWriter("out/" + filename + ".csv");
            pw1.close();
            pw2.close();
            this.fw1 = new FileWriter("out/" + filename + ".xyz", true);
            this.fw2 = new FileWriter("out/" + filename + ".csv", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bw1 = new BufferedWriter(fw1);
        this.bw2 = new BufferedWriter(fw2);
        try {
            bw2.write("t,nL,nR\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToCSV(long time, int particlesOnLeft, int particlesOnRight, int numberOfParticles){
        try {
            bw2.write(time + "," + ((double)particlesOnLeft/(double)numberOfParticles) + "," + ((double)particlesOnRight/(double)numberOfParticles) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToXYZ(Cell[][] cells, int numberOfParticles, long timeOfCycle, int particlesOnLeft, int particlesOnRight, long finalTime){
        double x, y, toX, toY, dY = (Math.sqrt(3)/2);
        double color;
        try {
            bw1.write(numberOfParticles + "\n");
            bw1.write(particlesOnLeft + " " + particlesOnRight + " " + timeOfCycle + " " + finalTime + "\n");
            for(int i=0; i < 202; i++){
                for(int j=0; j < 203; j++){
                    if(!cells[i][j].isWall()) {
                        if(j < 101){
                            color = (double)particlesOnLeft/(double)numberOfParticles;
                        }
                        else{
                            color = (double)particlesOnRight/(double)numberOfParticles;
                        }
                        y = i * dY;
                        if (j % 2 == 0) {
                            x = j;
                        } else {
                            x = j + 0.5;
                        }
                        if (cells[i][j].isA()) {
                            toX = x + 1;
                            toY = y;
                            bw1.write(x + " " + y + " " + (toX - x) + " " + (toY - y) + " " + color + "\n");
                        }
                        if (cells[i][j].isB()) {
                            toX = x + 1;
                            toY = y - dY;
                            bw1.write(x + " " + y + " " + (toX - x) + " " + (toY - y) + " " + color + "\n");
                        }
                        if (cells[i][j].isC()) {
                            toX = x - 1;
                            toY = y - dY;
                            bw1.write(x + " " + y + " " + (toX - x) + " " + (toY - y) + " " + color + "\n");
                        }
                        if (cells[i][j].isD()) {
                            toX = x - 1;
                            toY = y;
                            bw1.write(x + " " + y + " " + (toX - x) + " " + (toY - y) + " " + color + "\n");
                        }
                        if (cells[i][j].isE()) {
                            toX = x - 1;
                            toY = y + dY;
                            bw1.write(x + " " + y + " " + (toX - x) + " " + (toY - y) + " " + color + "\n");
                        }
                        if (cells[i][j].isF()) {
                            toX = x + 1;
                            toY = y + dY;
                            bw1.write(x + " " + y + " " + (toX - x) + " " + (toY - y) + " " + color + "\n");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFiles(){
        try {
            bw1.close();
            bw2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
