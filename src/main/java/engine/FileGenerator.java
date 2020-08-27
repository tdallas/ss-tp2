package engine;

import model.Cell;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {
    private FileWriter fw;
    private final BufferedWriter bw;
    private final boolean angle;

    public FileGenerator(String filename, boolean angle) {
        try {
            FileWriter pw = new FileWriter("out/" + filename + ".xyz");
            pw.close();
            this.fw = new FileWriter("out/" + filename + ".xyz", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bw = new BufferedWriter(fw);
        this.angle = angle;
    }

    public void addCells(Cell[][] cells, int numberOfParticles, long timeOfCycle, int particlesOnLeft, int particlesOnRight, long finalTime){
        double x, y, toX, toY, dY = (Math.sqrt(3)/2);
        try {
            bw.write(numberOfParticles + "\n");
            bw.write(particlesOnLeft + " " + particlesOnRight + " " + timeOfCycle + " " + finalTime + "\n");
            for(int i=0; i < 202; i++){
                for(int j=0; j < 203; j++){
                    if(!cells[i][j].isWall()) {
                        y = i * dY;
                        if (j % 2 == 0) {
                            x = j;
                        } else {
                            x = j + 0.5;
                        }
                        if (cells[i][j].isA()) {
                            if(angle){
                                bw.write(x + " " + y + " 0.0\n");
                            }
                            else {
                                toX = x + 1;
                                toY = y;
                                bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                            }
                        }
                        if (cells[i][j].isB()) {
                            if(angle){
                                bw.write(x + " " + y + " 60.0\n");
                            }
                            else {
                                toX = x + 1;
                                toY = y - dY;
                                bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                            }
                        }
                        if (cells[i][j].isC()) {
                            if(angle){
                                bw.write(x + " " + y + " 120.0\n");
                            }
                            else {
                                toX = x - 1;
                                toY = y - dY;
                                bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                            }
                        }
                        if (cells[i][j].isD()) {
                            if(angle){
                                bw.write(x + " " + y + " 180.0\n");
                            }
                            else {
                                toX = x - 1;
                                toY = y;
                                bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                            }
                        }
                        if (cells[i][j].isE()) {
                            if(angle){
                                bw.write(x + " " + y + " 240.0\n");
                            }
                            else {
                                toX = x - 1;
                                toY = y + dY;
                                bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                            }
                        }
                        if (cells[i][j].isF()) {
                            if(angle){
                                bw.write(x + " " + y + " 300.0\n");
                            }
                            else {
                                toX = x + 1;
                                toY = y + dY;
                                bw.write(x + " " + y + " " + toX + " " + toY + "\n");
                            }
                        }
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
