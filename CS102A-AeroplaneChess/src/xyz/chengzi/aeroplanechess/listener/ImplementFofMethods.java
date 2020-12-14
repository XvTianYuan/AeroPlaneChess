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
    public int[] NumberOfMove(int numberOfDiceOne, int numberOfDiceTwo) {
        int add=0,sub=0,mul=0,div=0;
        add = numberOfDiceOne + numberOfDiceTwo;
        sub = Math.abs(numberOfDiceOne - numberOfDiceTwo);
        if (numberOfDiceOne * numberOfDiceTwo <= 12)
            mul = numberOfDiceOne * numberOfDiceTwo;
        if (numberOfDiceOne >= numberOfDiceTwo && numberOfDiceOne % numberOfDiceTwo == 0)
            div = numberOfDiceOne / numberOfDiceTwo;
        if (numberOfDiceTwo >= numberOfDiceOne && numberOfDiceTwo % numberOfDiceOne == 0)
            div  = numberOfDiceTwo / numberOfDiceOne;
        int[]num={add,sub,mul,div};
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
        if (ChessLocation.getColor() == BoardLocation.getColor())
        return 4;
        else return 0;
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
