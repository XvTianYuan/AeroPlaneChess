package xyz.chengzi.aeroplanechess.model;

public class ChessLocation {
    private final int color;
    private final int index;
    private final int player;
    private final int number;

    public ChessLocation(int color,int index,int player,int number){
        this.color=color;
        this.index=index;
        this.player=player;
        this.number=number;
    }
    public int getColor() {
        return color;
    }

    public int getIndex() {
        return index;
    }
    public int getPlayer() {
        return player;
    }
    public int getNumber() {
        return number;
    }




}
