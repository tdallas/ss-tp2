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
        this.fileGenerator = new FileGenerator(outputFilename, false);
        this.collisionCells = cloneCells(cells);
        this.propagatedCells = cloneCells(cells);
        cleanCells(this.propagatedCells);
        this.particlesOnRight = 0;
        this.particlesOnLeft = numberOfParticles;
        this.rand = rand;
        this.BALANCE_LIMIT = (int)(numberOfParticles * 0.01);
    }

    public void simulate(){
        fileGenerator.addCells(collisionCells, numberOfParticles, 0, particlesOnLeft, particlesOnRight, 0);
        long startTime = System.currentTimeMillis();
        long endTime, startCycleTime;
        while(!isBalanced()){
            startCycleTime = System.currentTimeMillis();
            propagateParticles(collisionCells, propagatedCells);
            endTime = System.currentTimeMillis();
            fileGenerator.addCells(propagatedCells, numberOfParticles, endTime - startCycleTime, particlesOnLeft, particlesOnRight, endTime - startTime);
            startCycleTime = System.currentTimeMillis();
            collisionParticles(propagatedCells, collisionCells);
            endTime = System.currentTimeMillis();
            particlesOnLeft = getParticlesOnLeft(collisionCells);
            particlesOnRight = getParticlesOnRight(collisionCells);
            fileGenerator.addCells(collisionCells, numberOfParticles, endTime - startCycleTime, particlesOnLeft, particlesOnRight, endTime - startTime);
        }
        fileGenerator.closeFile();
    }

    private void propagateParticles(Cell[][] fromCells, Cell[][] toCells){
        for(int i = 0; i < fromCells.length; i++){
            for(int j = 0; j < fromCells[i].length; j++){
                if(!fromCells[i][j].isWall()) {
                    if (fromCells[i][j].isA()) {
                        if (!fromCells[i][j + 1].isWall()) {
                            toCells[i][j + 1].setA(true);
                        } else {
                            toCells[i][j].setD(true);
                        }
                    }
                    if (fromCells[i][j].isB()) {
                        if (!fromCells[i - 1][j + 1].isWall()) {
                            toCells[i - 1][j + 1].setB(true);
                        } else {
                            if(fromCells[i - 1][j].isWall() && fromCells[i][j + 1].isWall()){
                                toCells[i][j].setE(true);
                            }
                            else if(fromCells[i][j + 1].isWall()){
                                toCells[i - 1][j].setC(true);
                            }
                            else if(fromCells[i - 1][j].isWall()){
                                toCells[i][j + 1].setF(true);
                            }
                            else{
                                toCells[i][j].setE(true);
                            }
                        }
                    }
                    if (fromCells[i][j].isC()) {
                        if (!fromCells[i - 1][j - 1].isWall()) {
                            toCells[i - 1][j - 1].setC(true);
                        } else {
                            if(fromCells[i - 1][j].isWall() && fromCells[i][j - 1].isWall()){
                                toCells[i][j].setF(true);
                            }
                            else if(fromCells[i][j - 1].isWall()){
                                toCells[i - 1][j].setB(true);
                            }
                            else if(fromCells[i - 1][j].isWall()){
                                toCells[i][j - 1].setE(true);
                            }
                            else{
                                toCells[i][j].setF(true);
                            }
                        }
                    }
                    if (fromCells[i][j].isD()) {
                        if (!fromCells[i][j - 1].isWall()) {
                            toCells[i][j - 1].setD(true);
                        } else {
                            toCells[i][j].setA(true);
                        }
                    }
                    if (fromCells[i][j].isE()) {
                        if (!fromCells[i + 1][j - 1].isWall()) {
                            toCells[i + 1][j - 1].setE(true);
                        } else {
                            if(fromCells[i + 1][j].isWall() && fromCells[i][j - 1].isWall()){
                                toCells[i][j].setB(true);
                            }
                            else if(fromCells[i][j - 1].isWall()){
                                toCells[i + 1][j].setF(true);
                            }
                            else if(fromCells[i + 1][j].isWall()){
                                toCells[i][j - 1].setC(true);
                            }
                            else{
                                toCells[i][j].setB(true);
                            }
                        }
                    }
                    if (fromCells[i][j].isF()) {
                        if (!fromCells[i + 1][j + 1].isWall()) {
                            toCells[i + 1][j + 1].setF(true);
                        } else {
                            if(fromCells[i + 1][j].isWall() && fromCells[i][j + 1].isWall()){
                                toCells[i][j].setC(true);
                            }
                            else if(fromCells[i][j + 1].isWall()){
                                toCells[i + 1][j].setE(true);
                            }
                            else if(fromCells[i + 1][j].isWall()){
                                toCells[i][j + 1].setB(true);
                            }
                            else{
                                toCells[i][j].setC(true);
                            }
                        }
                    }
                    //Resets cell for next iteration
                    fromCells[i][j].resetCell();
                }
            }
        }
    }

    private void collisionParticles(Cell[][] fromCells, Cell[][] toCells){
        for(int i = 0; i < fromCells.length; i++){
            for(int j = 0; j < fromCells[i].length; j++){
                if(!fromCells[i][j].isWall() && fromCells[i][j].particleCount() > 1 ) {
                    if (rand.nextInt() % 2 == 0) {
                        //Moves everything clockwise
                        if (fromCells[i][j].isA()) {
                            toCells[i][j].setB(true);
                        }
                        if (fromCells[i][j].isB()) {
                            toCells[i][j].setC(true);
                        }
                        if (fromCells[i][j].isC()) {
                            toCells[i][j].setD(true);
                        }
                        if (fromCells[i][j].isD()) {
                            toCells[i][j].setE(true);
                        }
                        if (fromCells[i][j].isE()) {
                            toCells[i][j].setF(true);
                        }
                        if (fromCells[i][j].isF()) {
                            toCells[i][j].setA(true);
                        }
                    } else {
                        //Moves everything anti clockwise
                        if (fromCells[i][j].isA()) {
                            toCells[i][j].setF(true);
                        }
                        if (fromCells[i][j].isB()) {
                            toCells[i][j].setA(true);
                        }
                        if (fromCells[i][j].isC()) {
                            toCells[i][j].setB(true);
                        }
                        if (fromCells[i][j].isD()) {
                            toCells[i][j].setC(true);
                        }
                        if (fromCells[i][j].isE()) {
                            toCells[i][j].setD(true);
                        }
                        if (fromCells[i][j].isF()) {
                            toCells[i][j].setE(true);
                        }
                    }
                }
                else{
                    if (fromCells[i][j].isA()) {
                        toCells[i][j].setA(true);
                    }
                    if (fromCells[i][j].isB()) {
                        toCells[i][j].setB(true);
                    }
                    if (fromCells[i][j].isC()) {
                        toCells[i][j].setC(true);
                    }
                    if (fromCells[i][j].isD()) {
                        toCells[i][j].setD(true);
                    }
                    if (fromCells[i][j].isE()) {
                        toCells[i][j].setE(true);
                    }
                    if (fromCells[i][j].isF()) {
                        toCells[i][j].setF(true);
                    }
                }
                //Resets cell for next iteration
                fromCells[i][j].resetCell();
            }
        }
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
