package engine;

import model.Node;

import java.util.Random;

public class ParticleCollider {

    public static void collisionParticles(Node[][] fromNodes, Node[][] toNodes, Random rand){
        for(int i = 0; i < fromNodes.length; i++){
            for(int j = 0; j < fromNodes[i].length; j++){
                if(!fromNodes[i][j].isWall() && fromNodes[i][j].particleCount() > 1 ) {
                    if (rand.nextInt() % 2 == 0) {
                        //Moves everything clockwise
                        if (fromNodes[i][j].isA()) {
                            toNodes[i][j].setB(true);
                        }
                        if (fromNodes[i][j].isB()) {
                            toNodes[i][j].setC(true);
                        }
                        if (fromNodes[i][j].isC()) {
                            toNodes[i][j].setD(true);
                        }
                        if (fromNodes[i][j].isD()) {
                            toNodes[i][j].setE(true);
                        }
                        if (fromNodes[i][j].isE()) {
                            toNodes[i][j].setF(true);
                        }
                        if (fromNodes[i][j].isF()) {
                            toNodes[i][j].setA(true);
                        }
                    } else {
                        //Moves everything anti clockwise
                        if (fromNodes[i][j].isA()) {
                            toNodes[i][j].setF(true);
                        }
                        if (fromNodes[i][j].isB()) {
                            toNodes[i][j].setA(true);
                        }
                        if (fromNodes[i][j].isC()) {
                            toNodes[i][j].setB(true);
                        }
                        if (fromNodes[i][j].isD()) {
                            toNodes[i][j].setC(true);
                        }
                        if (fromNodes[i][j].isE()) {
                            toNodes[i][j].setD(true);
                        }
                        if (fromNodes[i][j].isF()) {
                            toNodes[i][j].setE(true);
                        }
                    }
                }
                else{
                    if (fromNodes[i][j].isA()) {
                        toNodes[i][j].setA(true);
                    }
                    if (fromNodes[i][j].isB()) {
                        toNodes[i][j].setB(true);
                    }
                    if (fromNodes[i][j].isC()) {
                        toNodes[i][j].setC(true);
                    }
                    if (fromNodes[i][j].isD()) {
                        toNodes[i][j].setD(true);
                    }
                    if (fromNodes[i][j].isE()) {
                        toNodes[i][j].setE(true);
                    }
                    if (fromNodes[i][j].isF()) {
                        toNodes[i][j].setF(true);
                    }
                }
                //Resets cell for next iteration
                fromNodes[i][j].resetNode();
            }
        }
    }

}
