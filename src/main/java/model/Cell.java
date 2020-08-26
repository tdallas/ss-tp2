package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Cell {
    private boolean wall;
    private boolean A;
    private boolean B;
    private boolean C;
    private boolean D;
    private boolean E;
    private boolean F;

    public Cell(Cell cell){
        this.wall = cell.wall;
        this.A = cell.A;
        this.B = cell.B;
        this.C = cell.C;
        this.D = cell.D;
        this.E = cell.E;
        this.F = cell.F;
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

    public void resetCell(){
        A = false;
        B = false;
        C = false;
        D = false;
        E = false;
        F = false;
    }
}
