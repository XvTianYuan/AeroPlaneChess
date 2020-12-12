package xyz.chengzi.aeroplanechess.listener;

import com.sun.media.jfxmedia.events.PlayerStateEvent;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.view.DiceSelectorComponent;

public interface MethodsForPlaying {
    boolean CheckForGoOut(int numberOfDiceOne, int numberOfDiceTwo);
    //Take a piece out of the hangar onto the board. This can only be done by rolling a 6
    //with either of the dice (e.g., roll a 3-6, or 6-2, or 6-6).
    int NumberOfMove(int numberOfDiceOne, int numberOfDiceTwo);
    //Move a piece that is on the board clockwise around the track. The number of
    //spaces moved is derived from the dices with arithmetic operations, maximum 12.
    //For example, rolling a 2 and a 4 will let you to move any one of your plane by 2, 4,
    //2+4=6, 4-2=2, 4*2=8, or 4/2=2 spaces. Note that a 4 and a 3 cannot make a move
    //of int(4/3) spaces.

    boolean EatOthersPiece(ChessBoardLocation locationOne, ChessBoardLocation locationTwo);

    //When a player lands on an opponent's piece, the opponent returns that piece to
    //its hangar.
    int FlyingFewGrids(ChessBoardLocation ChessLocation, ChessBoardLocation BoardLocation);
    //When a plane lands on a space of its own colour, it immediately jumps to the next
    //space of its own colour. Any opposing planes sitting on these squares are sent
    //back to their hangars.

    //TODO:next:implement methods

    void EatOtherPiecesWhenFlying(ChessBoardLocation locationStart);

    //There are additional shortcut squares. When a plane lands on one of these of its
    //own colour, it may take the shortcut, and any opposing planes in the path of the
    //shortcut are sent back to their hangars.
    void TooLuckyTooUnlucky();

    //If the sum of the two dices is no less than 10, whether they are used to enter or
    //move a piece, gives that player another roll. A second sum no less than 10 gives
    //the player a third roll with enter or move. If the player rolls a third sum no less
    //than 10, any pieces moved by the first two steps must return to their hangar and
    //play passes to the next player.
    boolean nextRoll(int numberOfDiceOne, int numberOfDiceTwo);

    //my supplement to last method.
    //不知道需不需要
    void ChooseToStack(ChessBoardLocation locationOne, ChessBoardLocation locationTwo);
    //When a plane lands on another plane in its own color, the player can choose to stack
    //the pieces and move them as one piece until they reach the centre or are landed on by
    //an opponent. When stacked pieces are sent back to their hangar by an opponent
    //landing on them, they are no longer stacked.
    int CompeteForEatingPiece(int SumDiceOne , int SumDiceTwo);
    //When a plane lands on an opposing plane, players determine which gets sent back to
    //its hangar by rolling one die, with the high roll determining the winner. When one
    //plane attacks a stack of planes, it must battle each one by rolling the die. When a stack
    //attacks another stack, the planes battle each other with a series of successive die rolls
    //until only one player occupies the square. This rule replaces the third basic rule above.


}
