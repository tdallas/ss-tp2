package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Node {
    private boolean wall;
    private boolean A;
    private boolean B;
    private boolean C;
    private boolean D;
    private boolean E;
    private boolean F;

    public Node(Node node){
        this.wall = node.wall;
        this.A = node.A;
        this.B = node.B;
        this.C = node.C;
        this.D = node.D;
        this.E = node.E;
        this.F = node.F;
    }

    public int particleCount(){
        int sum = 0;
        if(A){
            sum++;
        }
        if(B){
            sum++;
        }
        if(C){
            sum++;
        }
        if(D){
            sum++;
        }
        if(E){
            sum++;
        }
        if(F){
            sum++;
        }
        return sum;
    }

    public void resetNode(){
        A = false;
        B = false;
        C = false;
        D = false;
        E = false;
        F = false;
    }
}
