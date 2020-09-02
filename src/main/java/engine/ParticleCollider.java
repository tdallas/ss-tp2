package engine;

import model.Cell;

import java.util.Random;

public class ParticleCollider {

    public static void collisionParticles(Cell[][] fromCells, Cell[][] toCells, Random rand){
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

}
