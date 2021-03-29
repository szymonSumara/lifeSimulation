package agh.cs.lab;

import java.util.Random;

public enum  MapDirection {
    NORTH("O",new Vector2d(0,1)),
    NORTHEAST("O",new Vector2d(1,1)),
    EAST("O",new Vector2d(1,0)),
    SOUTHEAST("O",new Vector2d(1,-1)),
    SOUTH("O",new Vector2d(0,-1)),
    SOUTHWEST("O",new Vector2d(-1,-1)),
    WEST("O",new Vector2d(-1,0)),
    NORTHWEST("O",new Vector2d(-1,1));


    private final Vector2d unitVector;
    private final String   stringRepresentation;

    MapDirection(String stringRepresentation , Vector2d unitVector){
        this.unitVector = unitVector;
        this.stringRepresentation = stringRepresentation;
    }

    public String toString() {
        return stringRepresentation;
    }

    public MapDirection rotate(int angle){return values()[(this.ordinal() + angle) % values().length];}

    public MapDirection next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public MapDirection previous() {
        return values()[(this.ordinal() - 1 + values().length) % values().length];
    }

    public  Vector2d toUnitVector(){
        return unitVector;
    }

    public static MapDirection getRandomDirection(){
        return values()[new Random().nextInt(values().length)];
    }

}