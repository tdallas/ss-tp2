package engine;

import lombok.Getter;
import model.Node;

import java.util.Random;

@Getter
public class ParticlesGenerator {
    private final Random rand;
    private final int numberOfParticles;
    private final Node[][] nodes;

    private static final int ALLOWED_ATTEMPTS = 30;

    public ParticlesGenerator(Random rand, int numberOfParticles){
        this.rand = rand;
        this.numberOfParticles = numberOfParticles;
        this.nodes = new Node[202][203];
        generateParticles();
    }

    public void generateParticles(){
        for(int j = 0; j < 203; j++){
            nodes[0][j]  = new Node(true, false, false, false, false, false, false);
            nodes[201][j]  = new Node(true, false, false, false, false, false, false);
        }
        for(int i = 0; i < 202; i++){
            nodes[i][0]  = new Node(true, false, false, false, false, false, false);
            nodes[i][202]  = new Node(true, false, false, false, false, false, false);
            if(i > 75 && i < 126){
                nodes[i][101]  = new Node(false, false, false, false, false, false, false);
            }
            else{
                nodes[i][101]  = new Node(true, false, false, false, false, false, false);
            }
        }
        for(int i = 1 ; i < 201; i++){
            for(int j = 102; j < 202; j++){
                nodes[i][j]  = new Node(false, false, false, false, false, false, false);
            }
        }
        for(int i = 1 ; i < 201; i++){
            for(int j = 1; j < 101; j++){
                nodes[i][j]  = new Node(false, false, false, false, false, false, false);
            }
        }
        int aux = numberOfParticles;
        while (aux > 0) {
            createParticle();
            aux--;
        }
    }

    public void createParticle() {
        int randomX, randomY;
        int attempts = 0;
        boolean particleOverlaps = true;

        while (particleOverlaps && attempts < ALLOWED_ATTEMPTS) {
            randomX = generateRandomInt(1, 101);
            randomY = generateRandomInt(1, 201);

            if(nodes[randomY][randomX].particleCount() == 0){
                particleOverlaps = false;
                setRandomDirection(nodes[randomY][randomX]);
            }

            attempts++;
        }

        if(particleOverlaps){
            throw new IllegalArgumentException("Could not generate particle in less attempts than allowed.");
        }
    }

    private void setRandomDirection(Node node) {
        int randomDirection = generateRandomInt(0, 6);
        switch (randomDirection){
            case 0:
                node.setA(true);
                break;
            case 1:
                node.setB(true);
                break;
            case 2:
                node.setC(true);
                break;
            case 3:
                node.setD(true);
                break;
            case 4:
                node.setE(true);
                break;
            case 5:
                node.setF(true);
                break;
        }
    }

    private int generateRandomInt(final int min, final int max) {
        return rand.nextInt((max - min)) + min;
    }

}
