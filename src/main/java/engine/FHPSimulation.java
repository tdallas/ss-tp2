package engine;

import model.Node;

import java.util.Random;

public class FHPSimulation {
    private final FileGenerator fileGenerator;
    private final TimeFileGenerator timeFileGenerator;
    private final int numberOfParticles;
    private final Node[][] propagatedNodes;
    private final Node[][] collisionNodes;
    private int particlesOnLeft;
    private int particlesOnRight;
    private final Random rand;
    private final int BALANCE_LIMIT;

    public FHPSimulation(int numberOfParticles, Node[][] nodes, TimeFileGenerator timeFileGenerator, Random rand){
        this.numberOfParticles = numberOfParticles;
        this.fileGenerator = null;
        this.timeFileGenerator = timeFileGenerator;
        this.collisionNodes = cloneNodes(nodes);
        this.propagatedNodes = cloneNodes(nodes);
        cleanNodes(this.propagatedNodes);
        this.particlesOnRight = 0;
        this.particlesOnLeft = numberOfParticles;
        this.rand = rand;
        this.BALANCE_LIMIT = (int)(numberOfParticles * 0.01);
    }

    public FHPSimulation(int numberOfParticles, Node[][] nodes, String outputFilename, Random rand){
        this.numberOfParticles = numberOfParticles;
        this.timeFileGenerator = null;
        this.fileGenerator = new FileGenerator(outputFilename);
        this.collisionNodes = cloneNodes(nodes);
        this.propagatedNodes = cloneNodes(nodes);
        cleanNodes(this.propagatedNodes);
        this.particlesOnRight = 0;
        this.particlesOnLeft = numberOfParticles;
        this.rand = rand;
        this.BALANCE_LIMIT = (int)(numberOfParticles * 0.01);
    }

    public void simulate(){
        long startTime, endTime;
        if(fileGenerator != null) {
            fileGenerator.addToXYZ(collisionNodes, numberOfParticles, 0, particlesOnLeft, particlesOnRight, 0);
            fileGenerator.addToCSV(0, particlesOnLeft, particlesOnRight, numberOfParticles);
            startTime = System.currentTimeMillis();
            long startCycleTime;
            while (!isBalanced()) {
                startCycleTime = System.currentTimeMillis();
                ParticlesPropagator.propagateParticles(collisionNodes, propagatedNodes);
                endTime = System.currentTimeMillis();
                fileGenerator.addToXYZ(propagatedNodes, numberOfParticles, endTime - startCycleTime, particlesOnLeft, particlesOnRight, endTime - startTime);
                startCycleTime = System.currentTimeMillis();
                ParticleCollider.collisionParticles(propagatedNodes, collisionNodes, rand);
                endTime = System.currentTimeMillis();
                particlesOnLeft = getParticlesOnLeft(collisionNodes);
                particlesOnRight = getParticlesOnRight(collisionNodes);
                fileGenerator.addToXYZ(collisionNodes, numberOfParticles, endTime - startCycleTime, particlesOnLeft, particlesOnRight, endTime - startTime);
                fileGenerator.addToCSV(endTime - startTime, particlesOnLeft, particlesOnRight, numberOfParticles);
            }
            fileGenerator.closeFiles();
        }
        else{
            startTime = System.currentTimeMillis();
            while (!isBalanced()) {
                ParticlesPropagator.propagateParticles(collisionNodes, propagatedNodes);
                ParticleCollider.collisionParticles(propagatedNodes, collisionNodes, rand);
                particlesOnLeft = getParticlesOnLeft(collisionNodes);
                particlesOnRight = getParticlesOnRight(collisionNodes);
            }
            endTime = System.currentTimeMillis();
            timeFileGenerator.addToCSV(numberOfParticles, endTime - startTime);
        }
    }

    private void cleanNodes(Node[][] nodes){
        for (Node[] nodeRow : nodes) {
            for (Node nodeValue : nodeRow) {
                nodeValue.resetNode();
            }
        }
    }

    private boolean isBalanced(){
        return Math.abs(particlesOnLeft - particlesOnRight) < BALANCE_LIMIT;
    }

    private int getParticlesOnLeft(Node[][] nodes){
        int particlesOnLeft = 0;
        for (Node[] nodeRow : nodes) {
            for (int j = 0; j < nodeRow.length / 2; j++) {
                particlesOnLeft += nodeRow[j].particleCount();
            }
        }
        return particlesOnLeft;
    }

    private int getParticlesOnRight(Node[][] nodes){
        int particlesOnRight = 0;
        for (Node[] nodeRow : nodes) {
            for (int j = nodeRow.length / 2; j < nodeRow.length; j++) {
                particlesOnRight += nodeRow[j].particleCount();
            }
        }
        return particlesOnRight;
    }

    private Node[][] cloneNodes(Node[][] nodes){
        Node[][] result = new Node[nodes.length][nodes[0].length];
        for(int i = 0; i < nodes.length; i++){
            for(int j = 0; j < nodes[i].length; j++){
                result[i][j] = new Node(nodes[i][j]);
            }
        }
        return result;
    }
}
