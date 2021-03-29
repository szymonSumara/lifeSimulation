package agh.cs.lab;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlotRender extends JPanel {
    private final Statistic statistic;

    private final Plot plot;
    private final StatisticLabels statisticLabels;

    public PlotRender(Statistic statistic){

        this.statistic = statistic;
        this.plot = new Plot(this.statistic);
        this.statisticLabels = new StatisticLabels();
        add(this.plot);
        add(this.statisticLabels);

    }

    public void updateLabels(){
        this.statisticLabels.updateLabels();
    }

    @Override
    public void paintComponent(Graphics g){
        Vector2d panelSize = new Vector2d(getSize().width, getSize().height);
        g.clearRect(0,0,panelSize.x,panelSize.y);
        this.plot.paintComponent(g);
    }

    private class StatisticLabels extends JPanel{
        private final JLabel actualAnimalsCount;
        private final JLabel actualAnimalsGrass;
        private final JLabel animalEnergySum;
        private final JLabel averageLiveDays;
        private final JLabel averageChildrenForLivingAnimals;
        private final JLabel dominantGeneticCode;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600,300);
        }

        private StatisticLabels() {

            setBorder(new EmptyBorder(20, 60, 10, 10));

            this.actualAnimalsCount = new JLabel();
            this.actualAnimalsGrass = new JLabel();
            this.animalEnergySum = new JLabel();
            this.averageLiveDays = new JLabel();
            this.averageChildrenForLivingAnimals = new JLabel();
            this.dominantGeneticCode = new JLabel();
            setSize(new Dimension(300,600));

            Font font = new Font(Font.SERIF, Font.PLAIN,  20);
            this.actualAnimalsCount.setFont(font);
            this.actualAnimalsGrass.setFont(font);
            this.animalEnergySum.setFont(font);
            this.averageLiveDays.setFont(font);
            this.averageChildrenForLivingAnimals.setFont(font);
            this.dominantGeneticCode.setFont(font);

            add(actualAnimalsCount);
            add(actualAnimalsGrass);
            add(animalEnergySum);
            add(averageLiveDays);
            add(averageChildrenForLivingAnimals);
            add(dominantGeneticCode);
            setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));

        }


        public void updateLabels(){
            this.actualAnimalsCount.setText( "Actual Animals Count                                    " + statistic.getAnimalsNumber());
            this.actualAnimalsGrass.setText( "Actual Grass Count                                        " + statistic.getGrasCounter());
            this.animalEnergySum.setText(    "Average Animals Energy                               " + statistic.getAverageAnimalsEnergy());
            this.averageLiveDays.setText(    "Average Child Number For Living Animals  " + statistic.getAverageChildNumberForLivingAnimals());
            this.averageChildrenForLivingAnimals.setText(
                                            "Average Life Time                                         " + statistic.getAverageLifeTime());
            this.dominantGeneticCode.setText(
                    "Dominant Genetic Code " + statistic.getDominantGeneticCode());
        }


    }

    private static class Plot extends JPanel{
        private final Statistic statistic;


        private Plot(Statistic statistic){
            this.statistic = statistic;


        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 700);
        }

        public void paintComponent(Graphics g) {
            Vector2d panelSize = new Vector2d(getSize().width, getSize().height);
            g.clearRect(0,0,panelSize.x,panelSize.y);


            float maxValue = -1;
            for(Integer animalNum :statistic.animalsDataToPlot)
                maxValue = Math.max(maxValue,animalNum);

            for(Integer grassNum :statistic.grassDataToPlot)
                maxValue = Math.max(maxValue,grassNum);

            int marginPlot = 50;
            Vector2d plotSize = new Vector2d(panelSize.x  - marginPlot*2 , panelSize.y - marginPlot*2 );

            for(int i=0;i*100 <maxValue ;i++){
                int lineY = (int) (plotSize.y + marginPlot - plotSize.y/maxValue*(i*100));
                g.setColor(Color.lightGray);
                g.drawLine(2*marginPlot, lineY,
                                plotSize.x,  lineY);
            }

            g.setColor(Color.black);
            g.drawLine(2*marginPlot,plotSize.y+marginPlot,plotSize.x,plotSize.y+marginPlot);
            g.drawLine(2*marginPlot,plotSize.y+marginPlot,2*marginPlot,marginPlot);
            g.drawLine(2*marginPlot,marginPlot,plotSize.x,marginPlot);
            g.drawLine(plotSize.x,marginPlot,plotSize.x,plotSize.y+marginPlot);



            for(int i=0;i<this.statistic.animalsDataToPlot.size()-1;i++) {
                g.setColor(Color.black);
                g.drawLine((plotSize.x-2*marginPlot)/statistic.animalsDataToPlot.size() * i     + 100,(int)(plotSize.y+marginPlot - plotSize.y/maxValue * statistic.animalsDataToPlot.get(i)),
                           (plotSize.x-2*marginPlot)/statistic.animalsDataToPlot.size() * (i+1) + 100,(int)(plotSize.y+marginPlot - plotSize.y/maxValue * statistic.animalsDataToPlot.get(i + 1)));
            }

            for(int i=0;i<this.statistic.grassDataToPlot.size()-1;i++) {
                g.setColor(Color.green);
                g.drawLine((plotSize.x-2*marginPlot)/statistic.animalsDataToPlot.size() * i      + 100, (int)(plotSize.y+marginPlot - plotSize.y/maxValue * statistic.grassDataToPlot.get(i)),
                           (plotSize.x-2*marginPlot)/statistic.animalsDataToPlot.size() * (i +1) + 100, (int)(plotSize.y+marginPlot - plotSize.y/maxValue * statistic.grassDataToPlot.get(i + 1)));
            }
        }
    }
}
