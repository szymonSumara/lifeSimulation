package agh.cs.lab;


import java.util.*;
import java.util.stream.Collectors;

public class EvolutionMap implements IPositionChangeObserver {


    private final Vector2d southWestCorner = new Vector2d(0,0);
    private final Vector2d northEastCorner;
    private final Vector2d jungleSouthWestCorner;
    private final Vector2d jungleNorthEastCorner;
    private final int animalStartEnergy;


    private final MapVisualizer mapVisualizer;

    protected final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final List<Vector2d> freeFieldsInJungle = new LinkedList<>();
    private final List<Vector2d> freeFieldsOutJungle = new LinkedList<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public EvolutionMap(int width,int height,int jungleRatio,int animalStartEnergy){
        this.animalStartEnergy = animalStartEnergy;

        this.northEastCorner = new Vector2d(width-1,height-1);

        int jungleWidth = northEastCorner.x/jungleRatio;
        int jungleHeight = northEastCorner.y/jungleRatio;

        this.jungleSouthWestCorner = new Vector2d((northEastCorner.x - jungleWidth)/2,(northEastCorner.y - jungleHeight)/2);
        this.jungleNorthEastCorner = new Vector2d(jungleSouthWestCorner.x + jungleWidth,jungleSouthWestCorner.y + jungleHeight);

        this.mapVisualizer  = new MapVisualizer(this);
        for(int i = 0;i<=this.northEastCorner.x;i++)
            for(int j=0;j<=this.northEastCorner.y;j++) {
                this.animals.put(new Vector2d(i, j), new LinkedList<>());

                if( isInJungle(new Vector2d(i,j)) )
                    this.freeFieldsInJungle.add(new Vector2d(i,j));
                else
                    this.freeFieldsOutJungle.add(new Vector2d(i,j));

            }

    }

    public int getAnimalStartEnergy() {
        return animalStartEnergy;
    }

    public Grass placeGrassInJungle(){
        return placeGrass(this.freeFieldsInJungle);
    }

    public Grass placeGrassOutJungle(){
        return placeGrass(this.freeFieldsOutJungle);
    }

    public Object getObjectAt(Vector2d position){

        if(!animals.get(position).isEmpty())
            return animals.get(position).get(0);
        return grasses.get(position);
    }

    public MapVisualizer getVisualisation(){
        return  mapVisualizer;
    }

    public Vector2d getNorthEastCorner(){
        return northEastCorner;
    }

    public Vector2d getSouthWestCorner(){
        return southWestCorner;
    }

    public Grass getGrassAt(Vector2d position){
        return grasses.get(position);
    }

    public List<Animal> getAnimalsAt(Vector2d position){
        return animals.get(position).stream()
                .sorted((o1,o2) -> o1.compareTo(o2))
                .collect(Collectors.toList());
    }

    public List<Vector2d> getFreeFieldsNextTo(Vector2d position){
        List<Vector2d> freeFields = new LinkedList<>();
        for(MapDirection direction : MapDirection.values())
            if(getObjectAt(position.add(direction.toUnitVector()).convertToBounds(this.southWestCorner,this.northEastCorner))==null)
                freeFields.add(position.add(direction.toUnitVector()).convertToBounds(this.southWestCorner,this.northEastCorner));

        return freeFields;
    }

    public void removeGrass(Vector2d position){
        grasses.remove(position);
    }

    public void removeAnimal(Animal animal){
        if(this.animals.get( animal.getPosition()).size() == 1)
            if(this.isInJungle(animal.getPosition()))
                this.freeFieldsInJungle.add(animal.getPosition());
            else
                this.freeFieldsOutJungle.add(animal.getPosition());
        animals.get(animal.getPosition()).remove(animal);
        this.mapVisualizer.updateMapElementAt(animal.getPosition());
    }

    public boolean place(Animal animal){
        animals.get(animal.getPosition()).add(animal);

        animal.addObserver(this);

        if( isInJungle(animal.getPosition()) )
            freeFieldsInJungle.remove(animal.getPosition());
        else
            freeFieldsOutJungle.remove(animal.getPosition());

        return true;

    }

    public void positionChanged(Vector2d oldPosition,Vector2d newPosition,Animal animal){

        List<Animal> animalsListR = animals.get(oldPosition);
        List<Animal> animalsListI = animals.get(newPosition);
        System.out.println(newPosition);
        animalsListI.add((Animal) animal);
        animalsListR.remove(animal);

        if( isInJungle(newPosition) )
            freeFieldsInJungle.remove(newPosition);
        else
            freeFieldsOutJungle.remove(newPosition);

        if(animalsListR.isEmpty())
            if (isInJungle(oldPosition))
                freeFieldsInJungle.add(oldPosition);
            else
                freeFieldsOutJungle.add(oldPosition);

        mapVisualizer.updateMapElementAt(oldPosition);
        mapVisualizer.updateMapElementAt(newPosition);
    }

    private Grass placeGrass(List <Vector2d> freeFields){
        if(freeFields.isEmpty())
            return null;
        int randomIndex = new Random().nextInt(freeFields.size());
        Grass newGrass =  new Grass(this,freeFields.get(randomIndex));

        grasses.put(newGrass.getPosition(), newGrass);
        freeFields.remove(randomIndex);
        this.mapVisualizer.updateMapElementAt(newGrass.getPosition());

        return newGrass;
    }

    private boolean isInJungle(Vector2d position){
        return  position.follows(jungleSouthWestCorner) && position.precedes(jungleNorthEastCorner);
    }


}
