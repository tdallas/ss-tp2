package engine;

import model.Cell;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {
    private FileWriter fw;
    private final BufferedWriter bw;

    public FileGenerator(String filename) {
        try {
            FileWriter pw = new FileWriter("out/" + filename + ".xyz");
            pw.close();
            this.fw = new FileWriter("out/" + filename + ".xyz", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bw = new BufferedWriter(fw);
    }

    public void addCells(Cell[][] cells, int numberOfParticles, long timeOfCycle, int particlesOnLeft, int particlesOnRight, long finalTime){
        double x, y, toX, toY, dY = (Math.sqrt(3)/2);
        try {
            bw.write(numberOfParticles + "\n");
            bw.write(particlesOnLeft + " " + particlesOnRight + " " + timeOfCycle + " " + finalTime + "\n");
            for(int i=0; i < 202; i++){
                for(int j=0; j < 203; j++){
                    y = i * dY;
                    if(j%2 == 0){
                        x = j;
                    }
                    else{
                        x = j + 0.5;
                    }
                    if(cells[i][j].isA()){
                        toX = x + 1;
                        toY = y;
                        bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                    }
                    if(cells[i][j].isB()){
                        toX = x + 1;
                        toY = y - dY;
                        bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                    }
                    if(cells[i][j].isC()){
                        toX = x - 1;
                        toY = y - dY;
                        bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                    }
                    if(cells[i][j].isD()){
                        toX = x - 1;
                        toY = y;
                        bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                    }
                    if(cells[i][j].isE()){
                        toX = x - 1;
                        toY = y + dY;
                        bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                    }
                    if(cells[i][j].isF()){
                        toX = x + 1;
                        toY = y + dY;
                        bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                    }
                }
            }
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
