import engine.FileGenerator;
import engine.ParticlesGenerator;

import java.util.Random;

public class Main {
    public static void main(String[] args){
        int n = 5000;
        ParticlesGenerator particlesGenerator = new ParticlesGenerator(new Random(), n);
        FileGenerator fileGenerator = new FileGenerator("output");
        fileGenerator.addCells(particlesGenerator.getCells(), n, 10);
        fileGenerator.closeFile();
    }
}
