package engine;

import model.Node;

public class ParticlesPropagator {

    public static void propagateParticles(Node[][] fromNodes, Node[][] toNodes){
        for(int i = 0; i < fromNodes.length; i++){
            for(int j = 0; j < fromNodes[i].length; j++){
                if(!fromNodes[i][j].isWall()) {
                    // 0 degrees movement
                    if (fromNodes[i][j].isA()) {
                        if (!fromNodes[i][j + 1].isWall()) {
                            toNodes[i][j + 1].setA(true);
                        } else {
                            // opposite direction
                            toNodes[i][j].setD(true);
                        }
                    }
                    // 60 degrees movement
                    if (fromNodes[i][j].isB()) {
                        if (!fromNodes[i - 1][j + 1].isWall()) {
                            toNodes[i - 1][j + 1].setB(true);
                        } else {
                            if(fromNodes[i - 1][j].isWall() && fromNodes[i][j + 1].isWall()){
                                toNodes[i][j].setE(true);
                            }
                            else if(fromNodes[i][j + 1].isWall()){
                                toNodes[i - 1][j].setC(true);
                            }
                            else if(fromNodes[i - 1][j].isWall()){
                                toNodes[i][j + 1].setF(true);
                            }
                            else{
                                toNodes[i][j].setE(true);
                            }
                        }
                    }
                    // 120 degrees movement
                    if (fromNodes[i][j].isC()) {
                        if (!fromNodes[i - 1][j - 1].isWall()) {
                            toNodes[i - 1][j - 1].setC(true);
                        } else {
                            if(fromNodes[i - 1][j].isWall() && fromNodes[i][j - 1].isWall()){
                                toNodes[i][j].setF(true);
                            }
                            else if(fromNodes[i][j - 1].isWall()){
                                toNodes[i - 1][j].setB(true);
                            }
                            else if(fromNodes[i - 1][j].isWall()){
                                toNodes[i][j - 1].setE(true);
                            }
                            else{
                                toNodes[i][j].setF(true);
                            }
                        }
                    }
                    // 180 degrees movement
                    if (fromNodes[i][j].isD()) {
                        if (!fromNodes[i][j - 1].isWall()) {
                            toNodes[i][j - 1].setD(true);
                        } else {
                            toNodes[i][j].setA(true);
                        }
                    }
                    // 240 degrees movement
                    if (fromNodes[i][j].isE()) {
                        if (!fromNodes[i + 1][j - 1].isWall()) {
                            toNodes[i + 1][j - 1].setE(true);
                        } else {
                            if(fromNodes[i + 1][j].isWall() && fromNodes[i][j - 1].isWall()){
                                toNodes[i][j].setB(true);
                            }
                            else if(fromNodes[i][j - 1].isWall()){
                                toNodes[i + 1][j].setF(true);
                            }
                            else if(fromNodes[i + 1][j].isWall()){
                                toNodes[i][j - 1].setC(true);
                            }
                            else{
                                toNodes[i][j].setB(true);
                            }
                        }
                    }
                    // 300 degrees movement
                    if (fromNodes[i][j].isF()) {
                        if (!fromNodes[i + 1][j + 1].isWall()) {
                            toNodes[i + 1][j + 1].setF(true);
                        } else {
                            if(fromNodes[i + 1][j].isWall() && fromNodes[i][j + 1].isWall()){
                                toNodes[i][j].setC(true);
                            }
                            else if(fromNodes[i][j + 1].isWall()){
                                toNodes[i + 1][j].setE(true);
                            }
                            else if(fromNodes[i + 1][j].isWall()){
                                toNodes[i][j + 1].setB(true);
                            }
                            else{
                                toNodes[i][j].setC(true);
                            }
                        }
                    }
                    //Resets cell for next iteration
                    fromNodes[i][j].resetNode();
                }
            }
        }
    }
}
