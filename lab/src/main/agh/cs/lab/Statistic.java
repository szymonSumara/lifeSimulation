package agh.cs.lab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Statistic {
    private final List<Animal> liveAnimals = new LinkedList<>();
    private int deadAnimalsCounter = 0;
    private int grassCounter = 0;
    private float averageLifeTime = 0;
    public List<Integer> animalsDataToPlot;
    public List<Integer> grassDataToPlot;
    private final PlotRender plotRender;
    private final Map<String, Integer> geneticCodeCounters = new HashMap<>();
    private String dominantGeneticCode = null;
    public List<String> archieStats = new LinkedList<>();
    public Integer day = 0;

    public Statistic() {
        this.animalsDataToPlot = new LinkedList<>();
        this.grassDataToPlot = new LinkedList<>();
        this.plotRender = new PlotRender(this);
    }

    public String getDominantGeneticCode() {
        return dominantGeneticCode;
    }

    public PlotRender getPlotRender() {
        return plotRender;
    }

    public int getGrasCounter() {
        return this.grassCounter;
    }


    public float getAverageAnimalsEnergy() {
        float energySum = 0;

        for (Animal animal : liveAnimals)
            energySum += animal.getEnergy();

        return energySum / liveAnimals.size();

    }

    public float getAverageChildNumberForLivingAnimals() {
        float childNumber = 0;
        for (Animal animal : liveAnimals)
            childNumber += animal.childrenNum;
        return childNumber / liveAnimals.size();

    }

    public float getAnimalsNumber() {
        return liveAnimals.size();

    }

    public float getAverageLifeTime() {
        return averageLifeTime;
    }


    private void updateGeneticCodeCounters(Animal animal) {

        if (geneticCodeCounters.containsKey(animal.getGeneticCode()))
            geneticCodeCounters.put(animal.getGeneticCode(), geneticCodeCounters.get(animal.getGeneticCode()) + 1);
        else
            geneticCodeCounters.put(animal.getGeneticCode(), 1);

        if (dominantGeneticCode == null || geneticCodeCounters.get(dominantGeneticCode) < geneticCodeCounters.get(animal.getGeneticCode())) {
            dominantGeneticCode = animal.getGeneticCode();
            this.updateAnimals();
        }

    }

    public void changeObjectState(Object object,StatisticEvent statisticEvent){
        switch(statisticEvent){
            case ADD_ELEMENT:
                if(object instanceof Animal) {
                    liveAnimals.add((Animal) object);
                    updateGeneticCodeCounters((Animal) object);

                }else if(object instanceof Grass)
                    grassCounter+=1;
                break;
            case REMOVE_ELEMENT:
                if(object instanceof Animal) {
                    this.liveAnimals.remove(object);
                    this.updateAnimalLiveTime((Animal)object);
                }else if(object instanceof Grass)
                    grassCounter-=1;
                break;
        }
    }



    private void updateAnimals() {
        for (Animal animal : liveAnimals)
            animal.haveDominantGeneticCode = animal.getGeneticCode().equals(this.dominantGeneticCode);
    }

    private void updateAnimalLiveTime(Animal deadAnimal){
        this.averageLifeTime = (this.averageLifeTime*(this.deadAnimalsCounter ) + deadAnimal.getLiveTime() )/ (this.deadAnimalsCounter + 1);
        this.deadAnimalsCounter+=1;
    }

    public void updatePlot() {
        this.plotRender.updateLabels();

        animalsDataToPlot.add(liveAnimals.size());
        if (animalsDataToPlot.size() > 100) animalsDataToPlot.remove(0);

        grassDataToPlot.add(grassCounter);
        if (grassDataToPlot.size() > 100) grassDataToPlot.remove(0);

    }



    public void archiveStats() {

        this.day += 1;
        archieStats.add(day + " : " + this.toString() + "\n");
    }

    @Override
    public String toString() {
        return "Live animals:" + this.liveAnimals.size() + " Dead animals:" + this.deadAnimalsCounter +
                " averageLifeTime:" + averageLifeTime + " averageEnergy: " + getAverageAnimalsEnergy() +
                " averageChildNum: " + getAverageChildNumberForLivingAnimals() + " grassOnMap: " + getGrasCounter();
    }


}
