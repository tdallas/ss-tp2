package engine;

import model.Cell;

import java.util.Random;

public class FHPSimulation {
    private FileGenerator fileGenerator;
    private final int numberOfParticles;
    private Cell[][] propagatedCells;
    private Cell[][] collisionCells;
    private int particlesOnLeft;
    private int particlesOnRight;
    private Random rand;

    public static int BALANCE_LIMIT = 100;

    public FHPSimulation(int numberOfParticles, Cell[][] cells, String outputFilename, Random rand){
        this.numberOfParticles = numberOfParticles;
        this.fileGenerator = new FileGenerator(outputFilename);
        this.collisionCells = cloneCells(cells);
        this.propagatedCells = cloneCells(cells);
        cleanCells(this.propagatedCells);
        this.particlesOnRight = 0;
        this.particlesOnLeft = numberOfParticles;
        this.rand = rand;
    }

    public void simulate(){
        long startTime = System.currentTimeMillis();
        long endTime, startCycleTime;
        while(!isBalanced()){
            startCycleTime = System.currentTimeMillis();
            propagateParticles();
            collisionParticles();
            endTime = System.currentTimeMillis();
            particlesOnLeft = getParticlesOnLeft();
            particlesOnRight = getParticlesOnRight();
            fileGenerator.addCells(collisionCells, numberOfParticles, endTime - startCycleTime, particlesOnLeft, particlesOnRight, endTime - startTime);
        }
        fileGenerator.closeFile();
    }

    private void propagateParticles(){
        for(int i = 0; i < collisionCells.length; i++){
            for(int j = 0; j < collisionCells[0].length; j++){
                int toI;
                int toJ;
                if(collisionCells[i][j].isA()){
                    toI = i;
                    toJ = j + 1;
                    if(!collisionCells[toI][toJ].isWall()){
                        propagatedCells[toI][toJ].setA(true);
                    }
                    else{
                        propagatedCells[i][j].setD(true);
                    }
                }
                if(collisionCells[i][j].isB()){
                    toI = i + 1;
                    toJ = j - 1;
                    if(!collisionCells[toI][toJ].isWall()){
                        propagatedCells[toI][toJ].setB(true);
                    }
                    else{
                        propagatedCells[i][j].setE(true);
                    }
                }
                if(collisionCells[i][j].isC()){
                    toI = i - 1;
                    toJ = j - 1;
                    if(!collisionCells[toI][toJ].isWall()){
                        propagatedCells[toI][toJ].setC(true);
                    }
                    else{
                        propagatedCells[i][j].setF(true);
                    }
                }
                if(collisionCells[i][j].isD()){
                    toI = i;
                    toJ = j - 1;
                    if(!collisionCells[toI][toJ].isWall()){
                        propagatedCells[toI][toJ].setD(true);
                    }
                    else{
                        propagatedCells[i][j].setA(true);
                    }
                }
                if(collisionCells[i][j].isE()){
                    toI = i - 1;
                    toJ = j + 1;
                    if(!collisionCells[toI][toJ].isWall()){
                        propagatedCells[toI][toJ].setE(true);
                    }
                    else{
                        propagatedCells[i][j].setB(true);
                    }
                }
                if(collisionCells[i][j].isF()){
                    toI = i + 1;
                    toJ = j + 1;
                    if(!collisionCells[toI][toJ].isWall()){
                        propagatedCells[toI][toJ].setF(true);
                    }
                    else{
                        propagatedCells[i][j].setC(true);
                    }
                }
                //Resets cell for next iteration
                collisionCells[i][j].resetCell();
            }
        }
    }

    private void collisionParticles(){
        for(int i = 0; i < propagatedCells.length; i++){
            for(int j = 0; j < propagatedCells[0].length; j++){
                if(rand.nextInt()%2 == 0){
                    //Moves everything clockwise
                    if(propagatedCells[i][j].isA()){
                        collisionCells[i][j].setB(true);
                    }
                    if(propagatedCells[i][j].isB()){
                        collisionCells[i][j].setC(true);
                    }
                    if(propagatedCells[i][j].isC()){
                        collisionCells[i][j].setD(true);
                    }
                    if(propagatedCells[i][j].isD()){
                        collisionCells[i][j].setE(true);
                    }
                    if(propagatedCells[i][j].isE()){
                        collisionCells[i][j].setF(true);
                    }
                    if(propagatedCells[i][j].isF()){
                        collisionCells[i][j].setA(true);
                    }
                }
                else{
                    //Moves everything anti clockwise
                    if(propagatedCells[i][j].isA()){
                        collisionCells[i][j].setF(true);
                    }
                    if(propagatedCells[i][j].isB()){
                        collisionCells[i][j].setA(true);
                    }
                    if(propagatedCells[i][j].isC()){
                        collisionCells[i][j].setB(true);
                    }
                    if(propagatedCells[i][j].isD()){
                        collisionCells[i][j].setC(true);
                    }
                    if(propagatedCells[i][j].isE()){
                        collisionCells[i][j].setD(true);
                    }
                    if(propagatedCells[i][j].isF()){
                        collisionCells[i][j].setE(true);
                    }
                }
                //Resets cell for next iteration
                propagatedCells[i][j].resetCell();
            }
        }
    }

    private void cleanCells(Cell[][] cells){
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++){
                cells[i][j].resetCell();
            }
        }
    }

    private boolean isBalanced(){
        return Math.abs(particlesOnLeft - particlesOnRight) < BALANCE_LIMIT;
    }

    private int getParticlesOnLeft(){
        int particlesOnLeft = 0;
        for(int i = 1 ; i < 201; i++){
            for(int j = 1; j < 101; j++){
                particlesOnLeft += collisionCells[i][j].particleCount();
            }
        }
        return particlesOnLeft;
    }

    private int getParticlesOnRight(){
        int particlesOnRight = 0;
        for(int i = 1 ; i < 201; i++){
            for(int j = 102; j < 202; j++){
                particlesOnRight += collisionCells[i][j].particleCount();
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
