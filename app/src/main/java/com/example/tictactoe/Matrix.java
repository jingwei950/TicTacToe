package com.example.tictactoe;

import java.io.Serializable;

public class Matrix implements Serializable {
    private int[][] matrix;

    public Matrix(int rowIndex, int colIndex){
        matrix = new int[rowIndex][colIndex];
    }

    public void set(int rowIndex, int colIndex, int data){
        matrix[rowIndex][colIndex] = data;
    }

    public int get(int rowIndex, int colIndex){
        return matrix[rowIndex][colIndex];
    }

}
