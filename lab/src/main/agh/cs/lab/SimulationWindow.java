package agh.cs.lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationWindow extends JFrame implements ActionListener {


    private final MapVisualizer mapVisualisation;
    private final PlotRender plotRender;
    private final Statistic statistic;
    private final SimulationEngine simulationEngine;
    private final JMenuBar simulationWindowMenuBar;
    private final JMenuItem menuItemStopSimulation;
    private final JMenuItem menuItemStartSimulation;
    private final JMenuItem menuItemWriteStatisticToFile;
    private final JMenuItem menuItemDaysCounter;
    private final StatisticFileWriter statisticFileWriter;


    public SimulationWindow(SimulationEngine simulationEngine, MapVisualizer mapVisualisation, PlotRender plotRender, Statistic statistic) {

        this.statistic = statistic;
        this.simulationEngine = simulationEngine;
        this.statisticFileWriter = new StatisticFileWriter(this.statistic);

        this.setTitle("Evolution Simulator");
        this.plotRender = plotRender;
        this.setResizable(false);
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        simulationWindowMenuBar = new JMenuBar();
        menuItemStopSimulation = new JMenuItem("Stop Simulation");
        menuItemStopSimulation.addActionListener(this);
        menuItemStopSimulation.setActionCommand("stopSimulation");
        simulationWindowMenuBar.add(menuItemStopSimulation);

        menuItemStartSimulation = new JMenuItem("Start Simulation");
        menuItemStartSimulation.addActionListener(this);
        menuItemStartSimulation.setActionCommand("startSimulation");
        simulationWindowMenuBar.add(menuItemStartSimulation);

        menuItemWriteStatisticToFile = new JMenuItem("Write statistic to file");
        menuItemWriteStatisticToFile.addActionListener(this);
        menuItemWriteStatisticToFile.setActionCommand("statisticToFile");
        simulationWindowMenuBar.add(menuItemWriteStatisticToFile);

        menuItemDaysCounter = new JMenuItem("0");
        simulationWindowMenuBar.add(menuItemDaysCounter);

        this.setJMenuBar(simulationWindowMenuBar);


        this.setLayout(new GridLayout(1, 2));
        this.mapVisualisation = mapVisualisation;
        this.add(mapVisualisation);
        this.add(plotRender);


        this.setVisible(true);

    }

    public void updateContent() {
        menuItemDaysCounter.setText(statistic.day.toString());
        mapVisualisation.repaint();
        plotRender.repaint();
    }

    public void stop() {
        mapVisualisation.stop();
    }

    public void run() {
        mapVisualisation.run();
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "stopSimulation":
                simulationEngine.stop();
                this.stop();
                break;
            case "startSimulation":
                simulationEngine.run();
                this.run();
                break;
            case "statisticToFile":
                this.statisticFileWriter.writeStatisticToFile("archiveStats.txt");
        }

    }

}
