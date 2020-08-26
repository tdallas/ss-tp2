package engine;

import lombok.Getter;
import model.Cell;

import java.util.Random;

@Getter
public class ParticlesGenerator {
    private final Random rand;
    private final int numberOfParticles;
    private final Cell[][] cells;

    private static final int ALLOWED_ATTEMPTS = 30;

    public ParticlesGenerator(Random rand, int numberOfParticles){
        this.rand = rand;
        this.numberOfParticles = numberOfParticles;
        this.cells = new Cell[202][203];
        generateParticles();
    }

    public void generateParticles(){
        for(int j = 0; j < 203; j++){
            cells[0][j]  = new Cell(true, false, false, false, false, false, false);
            cells[201][j]  = new Cell(true, false, false, false, false, false, false);
        }
        for(int i = 0; i < 202; i++){
            cells[i][0]  = new Cell(true, false, false, false, false, false, false);
            cells[i][202]  = new Cell(true, false, false, false, false, false, false);
            if(i > 75 && i < 126){
                cells[i][101]  = new Cell(false, false, false, false, false, false, false);
            }
            else{
                cells[i][101]  = new Cell(true, false, false, false, false, false, false);
            }
        }
        for(int i = 1 ; i < 201; i++){
            for(int j = 102; j < 202; j++){
                cells[i][j]  = new Cell(false, false, false, false, false, false, false);
            }
        }
        for(int i = 1 ; i < 201; i++){
            for(int j = 1; j < 101; j++){
                cells[i][j]  = new Cell(false, false, false, false, false, false, false);
            }
        }
        int aux = numberOfParticles;
        while (aux > 0) {
            createParticle();
            aux--;
        }
    }

    public void createParticle() {
        int randomX = 0, randomY = 0;
        int attempts = 0;
        boolean particleOverlaps = true;

        while (particleOverlaps && attempts < ALLOWED_ATTEMPTS) {
            randomX = generateRandomInt(1, 101);
            randomY = generateRandomInt(1, 101);

            if(cells[randomY][randomX].particleCount() == 0){
                particleOverlaps = false;
                setRandomDirection(cells[randomY][randomX]);
            }

            attempts++;
        }

        if(particleOverlaps && attempts < ALLOWED_ATTEMPTS){
            throw new IllegalArgumentException("Could not generate particle in less attempts than allowed.");
        }
        System.out.println(randomX + " " + randomY);
    }

    private void setRandomDirection(Cell cell) {
        int randomDirection = generateRandomInt(0, 6);
        switch (randomDirection){
            case 0:
                cell.setA(true);
                break;
            case 1:
                cell.setB(true);
                break;
            case 2:
                cell.setC(true);
                break;
            case 3:
                cell.setD(true);
                break;
            case 4:
                cell.setE(true);
                break;
            case 5:
                cell.setF(true);
                break;
            default:
                cell.setA(true);
        }
    }

    private int generateRandomInt(final int min, final int max) {
        return rand.nextInt((max - min)) + min;
    }

}
