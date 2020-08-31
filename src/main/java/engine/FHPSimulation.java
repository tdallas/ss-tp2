package engine;

import model.Cell;

import java.util.Random;

public class FHPSimulation {
    private final FileGenerator fileGenerator;
    private final int numberOfParticles;
    private final Cell[][] propagatedCells;
    private final Cell[][] collisionCells;
    private int particlesOnLeft;
    private int particlesOnRight;
    private final Random rand;
    private final int BALANCE_LIMIT;

    public FHPSimulation(int numberOfParticles, Cell[][] cells, String outputFilename, Random rand){
        this.numberOfParticles = numberOfParticles;
        this.fileGenerator = new FileGenerator(outputFilename);
        this.collisionCells = cloneCells(cells);
        this.propagatedCells = cloneCells(cells);
        cleanCells(this.propagatedCells);
        this.particlesOnRight = 0;
        this.particlesOnLeft = numberOfParticles;
        this.rand = rand;
        this.BALANCE_LIMIT = (int)(numberOfParticles * 0.01);
    }

    public void simulate(){
        fileGenerator.addToXYZ(collisionCells, numberOfParticles, 0, particlesOnLeft, particlesOnRight, 0);
        fileGenerator.addToCSV(0, particlesOnLeft, particlesOnRight, numberOfParticles);
        long startTime = System.currentTimeMillis();
        long endTime, startCycleTime;
        while(!isBalanced()){
            startCycleTime = System.currentTimeMillis();
            Propagator.propagateParticles(collisionCells, propagatedCells);
            endTime = System.currentTimeMillis();
            fileGenerator.addToXYZ(propagatedCells, numberOfParticles, endTime - startCycleTime, particlesOnLeft, particlesOnRight, endTime - startTime);
            startCycleTime = System.currentTimeMillis();
            Colissioner.collisionParticles(propagatedCells, collisionCells, rand);
            endTime = System.currentTimeMillis();
            particlesOnLeft = getParticlesOnLeft(collisionCells);
            particlesOnRight = getParticlesOnRight(collisionCells);
            fileGenerator.addToXYZ(collisionCells, numberOfParticles, endTime - startCycleTime, particlesOnLeft, particlesOnRight, endTime - startTime);
            fileGenerator.addToCSV(endTime - startTime, particlesOnLeft, particlesOnRight, numberOfParticles);
        }
        fileGenerator.closeFiles();
    }

    private void cleanCells(Cell[][] cells){
        for (Cell[] cellRow : cells) {
            for (Cell cellValue : cellRow) {
                cellValue.resetCell();
            }
        }
    }

    private boolean isBalanced(){
        return Math.abs(particlesOnLeft - particlesOnRight) < BALANCE_LIMIT;
    }

    private int getParticlesOnLeft(Cell[][] cells){
        int particlesOnLeft = 0;
        for (Cell[] cellRow : cells) {
            for (int j = 0; j < cellRow.length / 2; j++) {
                particlesOnLeft += cellRow[j].particleCount();
            }
        }
        return particlesOnLeft;
    }

    private int getParticlesOnRight(Cell[][] cells){
        int particlesOnRight = 0;
        for (Cell[] cellRow : cells) {
            for (int j = cellRow.length / 2; j < cellRow.length; j++) {
                particlesOnRight += cellRow[j].particleCount();
            }
        }
        return particlesOnRight;
    }

    private Cell[][] cloneCells(Cell[][] cells){
        Cell[][] result = new Cell[cells.length][cells[0].length];
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++){
                result[i][j] = new Cell(cells[i][j]);
            }
        }
        return result;
    }
}
