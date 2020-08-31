package engine;

import model.Cell;

public class ParticlesPropagator {

    public static void propagateParticles(Cell[][] fromCells, Cell[][] toCells){
        for(int i = 0; i < fromCells.length; i++){
            for(int j = 0; j < fromCells[i].length; j++){
                if(!fromCells[i][j].isWall()) {
                    // 0 degrees movement
                    if (fromCells[i][j].isA()) {
                        if (!fromCells[i][j + 1].isWall()) {
                            toCells[i][j + 1].setA(true);
                        } else {
                            // opposite direction
                            toCells[i][j].setD(true);
                        }
                    }
                    // 60 degrees movement
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
                    // 120 degrees movement
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
                    // 180 degrees movement
                    if (fromCells[i][j].isD()) {
                        if (!fromCells[i][j - 1].isWall()) {
                            toCells[i][j - 1].setD(true);
                        } else {
                            toCells[i][j].setA(true);
                        }
                    }
                    // 240 degrees movement
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
                    // 300 degrees movement
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
}
