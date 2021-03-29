package agh.cs.lab;

public class SimulationApplication {

    private final ISimulationDataCollector simulationDataCollector;

    SimulationApplication(ISimulationDataCollector simulationDataCollector){
        this.simulationDataCollector = simulationDataCollector;
        this.runSimulation();
    }

    SimulationApplication(){
        this.simulationDataCollector = new SimulationUserInterface(this);

    }

    public  void oneSimulationStart() {
        this.runSimulation();
    }

    public  void twoSimulationStart() {
        this.runSimulation();
        this.runSimulation();
    }

    private void runSimulation(){

        new SimulationEngine(new EvolutionMap( this.simulationDataCollector.getMapWidth(),
                        this.simulationDataCollector.getMapHeight(),
                        this.simulationDataCollector.getJungleRatio(),
                        this.simulationDataCollector.getStartEnergy()),
                new Statistic(),
                this.simulationDataCollector.getGrassEnergy(),
                this.simulationDataCollector.getDailyEnergyCost(),
                this.simulationDataCollector.getGrassPerDayValue(),
                this.simulationDataCollector.getGrassPerDayValue()).run();
    }

}
