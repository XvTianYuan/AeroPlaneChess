package xyz.chengzi.aeroplanechess.listener;

import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;

public class ImplementFofMethods implements MethodsForPlaying {


    @Override
    public boolean CheckForGoOut(int numberOfDiceOne, int numberOfDiceTwo) {
        if(numberOfDiceOne == 6 || numberOfDiceTwo == 6){
            return true;
        }
        return false;
    }

    @Override
    public int NumberOfMove(int numberOfDiceOne, int numberOfDiceTwo) {
        int num;
        num = numberOfDiceOne + numberOfDiceTwo;
        return num;
    }

    @Override
    public boolean EatOthersPiece(ChessBoardLocation locationOne, ChessBoardLocation locationTwo) {
        if(locationOne == locationTwo){
            return true;
        }
        return false;
    }
    //color?require a color judge

    @Override
    public int FlyingFewGrids(ChessBoardLocation ChessLocation, ChessBoardLocation BoardLocation) {
        return 0;
    }

    @Override
    public void EatOtherPiecesWhenFlying(ChessBoardLocation locationStart) {

    }

    @Override
    public void TooLuckyTooUnlucky() {

    }

    @Override
    public boolean nextRoll(int numberOfDiceOne, int numberOfDiceTwo) {
        return false;
    }

    @Override
    public void ChooseToStack(ChessBoardLocation locationOne, ChessBoardLocation locationTwo) {

    }

    @Override
    public int CompeteForEatingPiece(int SumDiceOne, int SumDiceTwo) {
        return 0;
    }
}
