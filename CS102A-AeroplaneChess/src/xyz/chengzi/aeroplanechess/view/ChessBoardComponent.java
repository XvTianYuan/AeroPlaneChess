package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.listener.ChessBoardListener;
import xyz.chengzi.aeroplanechess.listener.InputListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardComponent extends JComponent implements Listenable<InputListener>, ChessBoardListener {
    private static final Color[] BOARD_COLORS = {Color.YELLOW, Color.BLUE, Color.GREEN, Color.RED};
    private static final Color[] PIECE_COLORS = {Color.YELLOW.darker(), Color.BLUE.darker(),
            Color.GREEN.darker(), Color.RED.darker()};

    private final List<InputListener> listenerList = new ArrayList<>();
    private final SquareComponent[][] gridComponents;
    private final int dimension, endDimension;
    private final int gridSize;

    public ChessBoardComponent(int size, int dimension, int endDimension) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLayout(null); // Use absolute layout
        setSize(size * 2, size * 2);

        this.gridComponents = new SquareComponent[4][dimension + endDimension + 4];
        this.dimension = dimension;
        this.endDimension = endDimension;
        this.gridSize = size / (dimension + 1);
        initGridComponents();
    }

    private int gridLocation(int player, int index) {
        // FIXME: Calculate proper location for each grid
        int boardIndex = (1 + 13 * player + 4 * (index)) % (4 * dimension);
        int x = 0, y = 0;
        int temp = boardIndex % dimension;

//        if (index <= dimension + endDimension + 3 && index >= dimension + endDimension) {
//            if (player == 0) {
//                if (index == dimension + endDimension) {
//                    x = gridSize;
//                    y = gridSize;
//                } else if (index == dimension + endDimension + 1) {
//                    x = gridSize;
//                    y = gridSize * 2;
//                } else if (index == dimension + endDimension + 2) {
//                    x = gridSize * 2;
//                    y = gridSize;
//                } else {
//                    x = gridSize * 2;
//                    y = gridSize * 2;
//                }
//            } else if (player == 1) {
//                if (index == dimension + endDimension) {
//                    x = gridSize * 12;
//                    y = gridSize;
//                } else if (index == dimension + endDimension + 1) {
//                    x = gridSize * 13;
//                    y = gridSize * 2;
//                } else if (index == dimension + endDimension + 2) {
//                    x = gridSize * 13;
//                    y = gridSize;
//                } else {
//                    x = gridSize * 12;
//                    y = gridSize * 2;
//                }
//            } else if (player == 2) {
//                if (index == dimension + endDimension) {
//                    x = gridSize * 12;
//                    y = gridSize * 12;
//                } else if (index == dimension + endDimension + 1) {
//                    x = gridSize * 13;
//                    y = gridSize * 12;
//                } else if (index == dimension + endDimension + 2) {
//                    x = gridSize * 13;
//                    y = gridSize * 13;
//                } else {
//                    x = gridSize * 12;
//                    y = gridSize * 13;
//                }
//            } else {
//                if (index == dimension + endDimension) {
//                    x = gridSize * 2;
//                    y = gridSize * 12;
//                } else if (index == dimension + endDimension + 1) {
//                    x = gridSize * 2;
//                    y = gridSize * 13;
//                } else if (index == dimension + endDimension + 2) {
//                    x = gridSize;
//                    y = gridSize * 13;
//                } else {
//                    x = gridSize;
//                    y = gridSize * 12;
//                }
//            }
//        }

        if (0 <= temp && temp <= 2) {
            if (boardIndex < dimension) {
                x = 4 * gridSize;
                y = (3 - temp) * gridSize;
            } else if (boardIndex < 2 * dimension) {
                x = (temp + 11) * gridSize;
                y = (4) * gridSize;
            } else if (boardIndex < 3 * dimension) {
                x = 10 * gridSize;
                y = (temp + 11) * gridSize;
            } else {
                y = 10 * gridSize;
                x = (3 - temp) * gridSize;
            }
        } else if (10 <= temp && temp <= 12) {
            if (boardIndex < dimension) {
                x = 10 * gridSize;
                y = (temp - 9) * gridSize;
            } else if (boardIndex < 2 * dimension) {
                x = (23 - temp) * gridSize;
                y = (10) * gridSize;
            } else if (boardIndex < 3 * dimension) {
                x = 4 * gridSize;
                y = (23 - temp) * gridSize;
            } else {
                y = 4 * gridSize;
                x = (temp - 9) * gridSize;
            }
        } else {
            if (boardIndex < dimension) {
                x = (boardIndex + 1) * gridSize;
                y = 0;
            } else if (boardIndex < 2 * dimension) {
                x = (dimension + 1) * gridSize;
                y = (boardIndex - dimension + 1) * gridSize;
            } else if (boardIndex < 3 * dimension) {
                x = (3 * dimension - boardIndex) * gridSize;
                y = (dimension + 1) * gridSize;
            } else {
                x = 0;
                y = (4 * dimension - boardIndex) * gridSize;
            }
        }
        if (x == -78) {
            x = gridSize;
            y = 4 * gridSize;
        }

        return x << 16 | y;
    }

    private int endGridLocation(int player, int index) {
        // FIXME: Calculate proper location for each end grid
        int beforeEndGridLocation = gridLocation(player, dimension - 1);
        int x = beforeEndGridLocation >> 16, y = beforeEndGridLocation & 0xffff;
//        if (y == 0) {
//            y += (index + 1) * gridSize;
//            x = (int) (gridSize * 0.5 * (dimension+1));
//        } else if (x == 0) {
//            x += (index + 1) * gridSize;
//            y = (int) (gridSize * 0.5 * (dimension+1));
//        } else if (y == dimension * gridSize) {
//            y -= (index ) * gridSize;
//            x = (int) (gridSize * 0.5 * (dimension+1));
//        } else {
//            x -= (index) * gridSize;
//            y = (int) (gridSize * 0.5 * (dimension+1));
//        }
        if (x == gridSize) {
            x += (index) * gridSize;
            y += 3 * gridSize;
        } else if (y == gridSize) {
            y += (index) * gridSize;
            x -= 3 * gridSize;
        } else if (y == 13 * gridSize) {
            y -= (index) * gridSize;
            x += 3 * gridSize;
        } else {
            x -= (index) * gridSize;
            y -= 3 * gridSize;
        }
        return x << 16 | y;
    }

    private void initGridComponents() {
        for (int player = 0; player < 4; player++) {
            for (int index = 0; index < dimension; index++) {
                int gridLocation = gridLocation(player, index - 1);
                gridComponents[player][index] = new SquareComponent(gridSize, BOARD_COLORS[player], player, index);
                gridComponents[player][index].setLocation(gridLocation >> 16, gridLocation & 0xffff);
                add(gridComponents[player][index]);
            }
            for (int index = dimension; index < dimension + endDimension; index++) {
                int gridLocation = endGridLocation(player, index - dimension);
                gridComponents[player][index] = new SquareComponent(gridSize, BOARD_COLORS[player], player, index);
                gridComponents[player][index].setLocation(gridLocation >> 16, gridLocation & 0xffff);
                add(gridComponents[player][index]);
            }
            for (int index = dimension + endDimension; index < dimension + endDimension + 4; index++) {
                int gridLocation = gridLocation(player, index - 1);
                gridComponents[player][index] = new SquareComponent(gridSize, BOARD_COLORS[player], player, index);
//                gridComponents[player][index].setLocation(gridLocation >> 16, gridLocation & 0xffff);
//                add(gridComponents[player][index]);
            }

        }
        gridComponents[0][dimension + endDimension].setLocation(gridSize, gridSize);
        gridComponents[0][dimension + endDimension + 1].setLocation(gridSize, gridSize * 2);
        gridComponents[0][dimension + endDimension + 2].setLocation(gridSize * 2, gridSize);
        gridComponents[0][dimension + endDimension + 3].setLocation(gridSize * 2, gridSize * 2);
        gridComponents[1][dimension + endDimension].setLocation(gridSize * 12, gridSize * 1);
        gridComponents[1][dimension + endDimension + 1].setLocation(gridSize * 12, gridSize * 2);
        gridComponents[1][dimension + endDimension + 2].setLocation(gridSize * 13, gridSize * 2);
        gridComponents[1][dimension + endDimension + 3].setLocation(gridSize * 13, gridSize * 1);
        gridComponents[2][dimension + endDimension].setLocation(gridSize * 12, gridSize * 12);
        gridComponents[2][dimension + endDimension + 1].setLocation(gridSize * 12, gridSize * 13);
        gridComponents[2][dimension + endDimension + 2].setLocation(gridSize * 13, gridSize * 12);
        gridComponents[2][dimension + endDimension + 3].setLocation(gridSize * 13, gridSize * 13);
        gridComponents[3][dimension + endDimension].setLocation(gridSize * 2, gridSize * 12);
        gridComponents[3][dimension + endDimension + 1].setLocation(gridSize * 2, gridSize * 13);
        gridComponents[3][dimension + endDimension + 2].setLocation(gridSize * 1, gridSize * 12);
        gridComponents[3][dimension + endDimension + 3].setLocation(gridSize * 1, gridSize * 13);
        for (int player = 0; player < 4; player++) {
            for (int index = dimension+endDimension;index<dimension+endDimension+4;index++){
                add(gridComponents[player][index]);
            }
        }
    }


    public SquareComponent getGridAt(ChessBoardLocation location) {
        return gridComponents[location.getColor()][location.getIndex()];
    }

    public void setChessAtGrid(ChessBoardLocation location, Color color) {
        removeChessAtGrid(location);
        getGridAt(location).add(new ChessComponent(color));
    }

    public void removeChessAtGrid(ChessBoardLocation location) {
        // Note: re-validation is required after remove / removeAll
        getGridAt(location).removeAll();
        getGridAt(location).revalidate();
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent instanceof SquareComponent) {
                SquareComponent square = (SquareComponent) clickedComponent;
                ChessBoardLocation location = new ChessBoardLocation(square.getPlayer(), square.getIndex());
//                System.out.println("     "+location.getIndex());
//                System.out.println("     "+location.getColor());
                for (InputListener listener : listenerList) {
                    if (clickedComponent.getComponentCount() == 0) {
                        listener.onPlayerClickSquare(location, square);
                    } else {
                        listener.onPlayerClickChessPiece(location, (ChessComponent) square.getComponent(0));
                    }
                }
            }
        }
    }

    @Override
    public void onChessPiecePlace(ChessBoardLocation location, ChessPiece piece) {
        setChessAtGrid(location, PIECE_COLORS[piece.getPlayer()]);
        repaint();
    }

    @Override
    public void onChessPieceRemove(ChessBoardLocation location) {
        removeChessAtGrid(location);
        repaint();
    }

    @Override
    public void onChessBoardReload(ChessBoard board) {
        for (int color = 0; color < 4; color++) {
            for (int index = 0; index < board.getAllDimension(); index++) {
                ChessBoardLocation location = new ChessBoardLocation(color, index);
                ChessPiece piece = board.getChessPieceAt(location);
                if (piece != null) {
                    setChessAtGrid(location, PIECE_COLORS[piece.getPlayer()]);
                } else {
                    removeChessAtGrid(location);
                }
            }
        }
        repaint();
    }

    @Override
    public void registerListener(InputListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(InputListener listener) {
        listenerList.remove(listener);
    }
}
