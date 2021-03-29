package agh.cs.lab;

public class Grass {

    private final Vector2d position;
    private   final EvolutionMap map;
    public Grass(EvolutionMap map, Vector2d initialPosition){
        this.position = initialPosition;
        this.map = map;
    }

    public Vector2d getPosition() {
        return position;
    }
}
