package agh.cs.lab;


import java.awt.*;
import java.util.*;
import java.util.List;

public class Animal implements IPositionChangeObserver, Comparable {

    protected Vector2d position;
    protected final EvolutionMap map;
    private MapDirection orientation = MapDirection.getRandomDirection();
    private Integer energy;
    private Integer daysOfLive = 1;
    private final AnimalVisualisation animalvisualisation;

    public boolean haveDominantGeneticCode;

    private final GeneticCode geneticCode;
    public int childrenNum = 0;
    public int successorNum = 0;
    public final Animal ancestor;
    public boolean isFocused = false;

    private final List<IPositionChangeObserver> observers = new LinkedList<>();

    public Animal(EvolutionMap map, Vector2d initialPosition) {
        this.position = initialPosition;
        this.map = map;
        this.energy = map.getAnimalStartEnergy();
        this.ancestor = null;
        this.map.place(this);
        this.geneticCode = new GeneticCode();
        animalvisualisation = new AnimalVisualisation(this);

    }

    private Animal(EvolutionMap map, Vector2d initialPosition, Animal strongerParent, Animal weakerParent, int energy, Animal ancestor) {

        this.position = initialPosition;
        this.map = map;
        this.energy = energy;
        this.geneticCode = new GeneticCode(strongerParent.geneticCode, weakerParent.geneticCode);
        this.animalvisualisation = new AnimalVisualisation(this);

        this.ancestor = ancestor;

        if (ancestor != null) {
            ancestor.successorNum += 1;
        }

        List<Vector2d> possiblePlaces = this.map.getFreeFieldsNextTo(position);

        System.out.println(this.energy);
        if (possiblePlaces.isEmpty())
            this.move();
        else {
            Vector2d oldPosition = this.position;
            this.position = possiblePlaces.get(new Random().nextInt(possiblePlaces.size()));
            this.positionChanged(oldPosition, this.position, this);
        }

        this.map.place(this);
    }

    public String getGeneticCode() {
        return this.geneticCode.toString();
    }

    public int getLiveTime() {
        return this.daysOfLive;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void improveEnergy(int energy) {
        this.energy = Math.min(this.energy + energy, map.getAnimalStartEnergy());
    }

    public void reduceEnergy(int energy) {
        this.energy -= energy;
    }

    public boolean isDead() {
        return this.energy < 0;
    }

    public boolean canReproduce() {
        return this.energy >= map.getAnimalStartEnergy() / 2;
    }

    public int getDaysToNextStop() {
        return this.animalvisualisation.getDaysNumber();
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public Color getColorOfAnimal() {

        if (this.isFocused)
            return new Color(255, 0, 0);

        if (this.haveDominantGeneticCode) {
            return new Color(255, 255, 255);
        }

        float colorValuePerEnergyUnit = (float) (255.0 / map.getAnimalStartEnergy());
        int animalColorNumber = Math.max(Math.min(254, (int) (colorValuePerEnergyUnit * this.energy)), 0);
        return new Color(0, animalColorNumber, animalColorNumber);
    }

    public Animal reproduce(Animal other) {

        Animal ancestor = null;

        if (isFocused)
            ancestor = this;
        else if (other.isFocused)
            ancestor = other;
        else if (this.ancestor != null)
            ancestor = this.ancestor;
        else if (other.ancestor != null)
            ancestor = other.ancestor;

        this.childrenNum += 1;
        other.childrenNum += 1;

        int gaveEnergyFromThis = this.energy / 4;
        this.reduceEnergy(gaveEnergyFromThis);

        int gaveEnergyFromOther = other.energy / 4;
        other.reduceEnergy(gaveEnergyFromOther);

        return new Animal(this.map, this.position, this, other, gaveEnergyFromThis + gaveEnergyFromOther, ancestor);
    }

    public void move() {
        this.daysOfLive += 1;
        this.orientation = orientation.rotate(this.geneticCode.getRandomGene());
        Vector2d oldPosition = this.position;
        this.position = this.position.add(this.orientation.toUnitVector());
        this.position = this.position.convertToBounds(map.getNorthEastCorner(), map.getSouthWestCorner());
        this.positionChanged(oldPosition, this.position, this);
    }


    @Override
    public int compareTo(Object o) {
        return this.energy.compareTo(((Animal) o).energy);
    }

    public void showAnimalDetail() {
        this.animalvisualisation.showVisualisation();
    }


    @Override
    public String toString() {
        return this.orientation.toString();
    }


    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        for (IPositionChangeObserver observer : observers)
            observer.positionChanged(oldPosition, newPosition, this);
    }

    public boolean removeObserver(IPositionChangeObserver observer) {

        if (observers.contains(observer)) {
            observers.remove(observer);
            return !observers.contains(observer);
        }
        return false;
    }


    public boolean addObserver(IPositionChangeObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            return observers.contains(observer);
        }
        return false;
    }

}
