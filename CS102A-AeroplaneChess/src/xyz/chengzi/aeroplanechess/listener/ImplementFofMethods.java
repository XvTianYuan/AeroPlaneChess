package xyz.chengzi.aeroplanechess.listener;

import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;

public class ImplementFofMethods implements MethodsForPlaying {


    @Override
    public boolean CheckForGoOut(int numberOfDiceOne, int numberOfDiceTwo) {
        if (numberOfDiceOne == 6 || numberOfDiceTwo == 6) {
            return true;
        }
        return false;
    }

    @Override
    public int[] NumberOfMove(int numberOfDiceOne, int numberOfDiceTwo) {
        int add = 0, sub = 0, mul = 0, div = 0;
        add = numberOfDiceOne + numberOfDiceTwo;
        sub = Math.abs(numberOfDiceOne - numberOfDiceTwo);
        if (numberOfDiceOne * numberOfDiceTwo <= 12)
            mul = numberOfDiceOne * numberOfDiceTwo;
        if (numberOfDiceOne >= numberOfDiceTwo && numberOfDiceOne % numberOfDiceTwo == 0)
            div = numberOfDiceOne / numberOfDiceTwo;
        if (numberOfDiceTwo >= numberOfDiceOne && numberOfDiceTwo % numberOfDiceOne == 0)
            div = numberOfDiceTwo / numberOfDiceOne;
        int[] num = {add, sub, mul, div};
        return num;
    }

    @Override
    public boolean EatOthersPiece(ChessBoardLocation locationOne, ChessBoardLocation locationTwo) {
        if (locationOne == locationTwo) {
            return true;
        }
        return false;
    }
    //color?require a color judge

    @Override
    public int FlyingFewGrids(ChessBoardLocation ChessLocation, ChessBoardLocation BoardLocation) {
        if (ChessLocation.getColor() == BoardLocation.getColor())
            return 4;
        else return 0;
    }

    @Override
    public void EatOtherPiecesWhenFlying(ChessBoardLocation locationStart, ChessPiece localPiece, ChessBoard board) {
        if (locationStart.getIndex() == 4 && localPiece.getPlayer() == locationStart.getColor()) {
            board.moveChessPiece(locationStart, 3, localPiece);
            if (locationStart.getColor() == 0 && board.getChessPieceAt(new ChessBoardLocation(2, 15)).getPlayer() == 2) {
                board.moveChessPiece(new ChessBoardLocation(2, 15), 8, board.getChessPieceAt(new ChessBoardLocation(2, 15)));
            } else if (locationStart.getColor() == 1 && board.getChessPieceAt(new ChessBoardLocation(3, 15)).getPlayer() == 3) {
                board.moveChessPiece(new ChessBoardLocation(3, 15), 8, board.getChessPieceAt(new ChessBoardLocation(3, 15)));
            } else if (locationStart.getColor() == 2 && board.getChessPieceAt(new ChessBoardLocation(0, 15)).getPlayer() == 0) {
                board.moveChessPiece(new ChessBoardLocation(0, 15), 8, board.getChessPieceAt(new ChessBoardLocation(0, 15)));
            }else if (locationStart.getColor()==3 && board.getChessPieceAt(new ChessBoardLocation(1,15)).getPlayer()==1){
                board.moveChessPiece(new ChessBoardLocation(1,15),8, board.getChessPieceAt(new ChessBoardLocation(1,15)));
        }
    }

}

    @Override
    public void TooLuckyTooUnlucky() {

    }

    @Override
    public void ChooseToStack(ChessBoardLocation locationOne, ChessBoardLocation locationTwo) {

    }

    @Override
    public void CompeteForEatingPiece(int DiceOne, int DiceTwo) {

    }
}
