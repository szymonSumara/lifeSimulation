package agh.cs.lab;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements IEngine, ActionListener {


    private final EvolutionMap map;
    private final Statistic statistic;

    private final List<Animal> animals = new LinkedList<>();

    private final int grassEnergy;
    private final int dayEnergyCost;
    private final int grassPerDay;
    private final SimulationWindow simulationWindow;
    private int daysCounter = -1;
    private final Timer timer = new Timer(20, this);
    private Animal selectedAnimal = null;

    public SimulationEngine(EvolutionMap map, Statistic statistic, int grassEnergy, int dayEnergyCost, int grassPerDay, int startAnimalNumber) {
        this.map = map;


        this.statistic = statistic;
        this.grassEnergy = grassEnergy;
        this.dayEnergyCost = dayEnergyCost;
        this.grassPerDay = grassPerDay;
        this.simulationWindow = new SimulationWindow(this, map.getVisualisation(), statistic.getPlotRender(), this.statistic);


        Random random = new Random();
        for (int i = 0; i < startAnimalNumber; i++) {
            int randomX = random.nextInt(map.getNorthEastCorner().x);
            int randomY = random.nextInt(map.getNorthEastCorner().y);
            Vector2d newAnimalPosition = new Vector2d(randomX, randomY);

            if (map.getObjectAt(newAnimalPosition) == null){
                System.out.println(newAnimalPosition);
            animals.add(new Animal(map, newAnimalPosition));
        }else
                i -= 1;
        }


        for (Animal animal : animals)
            statistic.changeObjectState(animal, StatisticEvent.ADD_ELEMENT);
    }


    public void run() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {

        if (daysCounter == 0) {
            this.stop();
            this.selectedAnimal.isFocused = false;
            this.selectedAnimal.showAnimalDetail();
        }
        if (daysCounter != -1)
            this.daysCounter -= 1;
        else
            this.daysCounter = this.getDaysOfSelectedAnimal();


        this.removeDeadAnimals();
        this.moveAllAnimals();
        this.animalEatGrass();
        this.multiplicationAnimals();
        this.growGrass();
        this.statistic.updatePlot();
        this.simulationWindow.updateContent();
        this.statistic.archiveStats();

    }

    public void moveAllAnimals() {
        for (Animal animal : animals) {
            animal.reduceEnergy(this.dayEnergyCost);
            animal.move();
        }
    }

    public int getDaysOfSelectedAnimal() {
        for (Animal animal : this.animals) {
            if (animal.isFocused) {
                this.selectedAnimal = animal;
                return animal.getDaysToNextStop();
            }
        }
        return -1;
    }

    public void growGrass() {
        for (int i = 0; i < this.grassPerDay; i++) {
            Grass newGrass = this.map.placeGrassInJungle();
            if (newGrass == null)
                break;
            this.statistic.changeObjectState(newGrass, StatisticEvent.ADD_ELEMENT);
        }

        for (int i = 0; i < this.grassPerDay; i++) {
            Grass newGrass = map.placeGrassOutJungle();
            if (newGrass == null)
                break;
            this.statistic.changeObjectState(newGrass, StatisticEvent.ADD_ELEMENT);
        }

    }

    public void animalEatGrass() {
        for (int i = 0; i <=this.map.getNorthEastCorner().x; i++)
            for (int j = 0; j <= this.map.getNorthEastCorner().y; j++) {
                if (this.map.getAnimalsAt(new Vector2d(i, j)).isEmpty() || this.map.getGrassAt(new Vector2d(i, j)) == null)
                    continue;

                List<Animal> animalsAt = this.map.getAnimalsAt(new Vector2d(i, j));
                Animal firstAnimal = animalsAt.get(0);

                int strongestAnimalsCounter = 0;

                for (Animal animal : animalsAt)
                    if (animal.compareTo(firstAnimal) == 0)
                        strongestAnimalsCounter += 1;
                    else
                        break;

                if (((float) (this.grassEnergy) / strongestAnimalsCounter) * strongestAnimalsCounter != this.grassEnergy)
                    firstAnimal.improveEnergy(1);

                for (Animal animal : animalsAt)
                    if (animal.compareTo(firstAnimal) == 0)
                        firstAnimal.improveEnergy(this.grassEnergy / strongestAnimalsCounter);
                    else
                        break;

                this.statistic.changeObjectState(this.map.getGrassAt(new Vector2d(i, j)), StatisticEvent.REMOVE_ELEMENT);
                this.map.removeGrass(new Vector2d(i, j));


            }
    }


    public void removeDeadAnimals() {
        for (int i = 0; i < this.animals.size(); i++)
            if (this.animals.get(i).isDead()) {
                if (this.animals.get(i).equals(this.selectedAnimal)) {
                    this.timer.stop();
                    this.simulationWindow.stop();
                    this.animals.get(i).showAnimalDetail();
                    this.daysCounter=-1;
                }
                this.map.removeAnimal(this.animals.get(i));
                this.statistic.changeObjectState(this.animals.get(i), StatisticEvent.REMOVE_ELEMENT);
                this.animals.remove(i);

            }
    }

    public void multiplicationAnimals() {
        for (int i = 0; i <= this.map.getNorthEastCorner().x; i++)
            for (int j = 0; j <= this.map.getNorthEastCorner().y; j++)
                if (this.map.getAnimalsAt(new Vector2d(i, j)).size() > 1) {
                    List<Animal> animalsAt = this.map.getAnimalsAt(new Vector2d(i, j));
                    if (animalsAt.get(0).canReproduce() && animalsAt.get(1).canReproduce()) {
                        Animal newAnimal = animalsAt.get(0).reproduce(animalsAt.get(1));

                        this.animals.add(newAnimal);
                        this.statistic.changeObjectState(newAnimal, StatisticEvent.ADD_ELEMENT);
                    }
                }
    }
}
