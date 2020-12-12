package xyz.chengzi.aeroplanechess.model;

import xyz.chengzi.aeroplanechess.listener.ChessBoardListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard implements Listenable<ChessBoardListener> {
    private final List<ChessBoardListener> listenerList = new ArrayList<>();
    private final Square[][] grid;
    private final int dimension, endDimension;

    public ChessBoard(int dimension, int endDimension) {
        this.grid = new Square[4][dimension + endDimension];
        this.dimension = dimension;
        this.endDimension = endDimension;

        initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension; j++) {
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
        grid[0][0].setPiece(new ChessPiece(0));
        grid[1][0].setPiece(new ChessPiece(1));
        grid[2][0].setPiece(new ChessPiece(2));
        grid[3][0].setPiece(new ChessPiece(3));
        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }

    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getColor()][location.getIndex()];
    }

    public int getDimension() {
        return dimension;
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
        int color;
        int index;
        if (location.getColor() == 3)
            color = 0;
        else color = location.getColor()+1;
        switch (location.getIndex()) {
            case  0:index = 10;break;
            case 10:index =  7;break;
            case  7:index =  4;break;
            case  4:index =  1;break;
            case  1:index = 11;break;
            case  8:index =  5;break;
            case  5:index =  2;break;
            case  2:index = 12;break;
            case 12:index =  9;break;
            case  9:index =  6;break;
            case  6:index =  3;break;
            default: index = 0;
        }
            return new ChessBoardLocation(color,index);
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
