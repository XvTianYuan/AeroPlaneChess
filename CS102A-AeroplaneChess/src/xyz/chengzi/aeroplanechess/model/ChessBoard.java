package xyz.chengzi.aeroplanechess.model;

import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.ChessBoardListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.view.GameFrame;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard implements Listenable<ChessBoardListener> {
    private final List<ChessBoardListener> listenerList = new ArrayList<>();
    private final Square[][] grid;
    private final int dimension, endDimension;

    public ChessBoard(int dimension, int endDimension) {
        this.grid = new Square[4][dimension + endDimension + 4];
        this.dimension = dimension;
        this.endDimension = endDimension;

        initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension + 4; j++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }

    public void placeInitialPieces() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension; j++) {
                grid[i][j].setPiece(null);
            }
        }
        // FIXME: Demo implementation
        grid[0][dimension+endDimension].setPiece(new ChessPiece(0));
        grid[0][dimension+endDimension+1].setPiece(new ChessPiece(0));
        grid[0][dimension+endDimension+2].setPiece(new ChessPiece(0));
        grid[0][dimension+endDimension+3].setPiece(new ChessPiece(0));
        grid[1][dimension+endDimension].setPiece(new ChessPiece(1));
        grid[1][dimension+endDimension+1].setPiece(new ChessPiece(1));
        grid[1][dimension+endDimension+2].setPiece(new ChessPiece(1));
        grid[1][dimension+endDimension+3].setPiece(new ChessPiece(1));
        grid[2][dimension+endDimension].setPiece(new ChessPiece(2));
        grid[2][dimension+endDimension+1].setPiece(new ChessPiece(2));
        grid[2][dimension+endDimension+2].setPiece(new ChessPiece(2));
        grid[2][dimension+endDimension+3].setPiece(new ChessPiece(2));
        grid[3][dimension+endDimension].setPiece(new ChessPiece(3));
        grid[3][dimension+endDimension+1].setPiece(new ChessPiece(3));
        grid[3][dimension+endDimension+2].setPiece(new ChessPiece(3));
        grid[3][dimension+endDimension+3].setPiece(new ChessPiece(3));
        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }

    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getColor()][location.getIndex()];
    }

    public int getDimension() {
        return dimension;
    }
    public int getAllDimension(){
        return dimension+endDimension+4;
    }
    public int getEndDimension() {
        return endDimension;
    }

    public ChessPiece getChessPieceAt(ChessBoardLocation location) {
        return getGridAt(location).getPiece();
    }

    public void setChessPieceAt(ChessBoardLocation location, ChessPiece piece) {
        getGridAt(location).setPiece(piece);
        listenerList.forEach(listener -> listener.onChessPiecePlace(location, piece));
    }

    public ChessPiece removeChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        getGridAt(location).setPiece(null);
        listenerList.forEach(listener -> listener.onChessPieceRemove(location));
        return piece;
    }

    public void moveChessPiece(ChessBoardLocation src, int steps) {
        ChessBoardLocation dest = src;
        System.out.print("sdfhidsfhshfise: ");
        System.out.println(dest.getIndex());
        // FIXME: This just naively move the chess forward without checking anything
        if (dest.getIndex() < 12)
        for (int i = 0; i < steps; i++) {
            dest = nextLocation(dest);
        }
        if (dest.getIndex() >= 12)
            if (dest.getIndex()+steps<=18)
            dest = new ChessBoardLocation(dest.getColor(),dest.getIndex()+steps);
            else dest = new ChessBoardLocation(dest.getColor(),36-dest.getIndex()-steps);
        setChessPieceAt(dest, removeChessPieceAt(src));
    }

    public ChessBoardLocation nextLocation(ChessBoardLocation location) {
        // FIXME: This move the chess to next jump location instead of nearby next location
        int OldColor = location.getColor();
        int OldIndex = location.getIndex();
        int NewColor , NewIndex ;

        if(OldIndex >= dimension+endDimension){
            NewColor = OldColor;
            NewIndex = 0;
        }else{
            if(0<=OldIndex && OldIndex<=2){
                NewIndex = OldIndex+ 10;
            }else{
                NewIndex = OldIndex - 3;
            }
            if(OldColor == 0){
                NewColor = 1 ;
            }else if(OldColor == 1){
                NewColor = 2;
            }else if(OldColor == 2){
                NewColor = 3;
            }else{
                NewColor = 0;
            }
        }
            return new ChessBoardLocation(NewColor,NewIndex);
    }

    @Override
    public void registerListener(ChessBoardListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(ChessBoardListener listener) {
        listenerList.remove(listener);
    }
}
