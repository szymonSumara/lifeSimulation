package agh.cs.lab;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SimulationUserInterface extends JFrame implements ActionListener,ISimulationDataCollector{

    private final TextFieldWithLabel GrassPerDay;
    private final TextFieldWithLabel DailyEnergyCost;
    private final TextFieldWithLabel GrassEnergy;
    private final TextFieldWithLabel StartEnergy;
    private final TextFieldWithLabel mapWidth;
    private final TextFieldWithLabel mapHeight;
    private final TextFieldWithLabel jungleRatio;
    private final TextFieldWithLabel startAnimalNum;
    private final SimulationApplication interfaceBelongTo;

    SimulationUserInterface(SimulationApplication simulationApplication){
        this.interfaceBelongTo = simulationApplication;
        this.setTitle("Evolution simulation ");
        this.setSize(400,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ISimulationDataCollector fileReader = new StartDataFileReader("parameters.jsonc");


        JLabel headerText = new JLabel("Enter start parameters");

        this.GrassPerDay     = new TextFieldWithLabel("Grass Per Day" , fileReader.getGrassPerDayValue());
        this.DailyEnergyCost = new TextFieldWithLabel("Daily Energy Cost ",fileReader.getDailyEnergyCost());
        this.GrassEnergy     = new TextFieldWithLabel("Grass Energy ",fileReader.getGrassEnergy());
        this.StartEnergy     = new TextFieldWithLabel("Start energy ",fileReader.getStartEnergy());
        this.mapWidth        = new TextFieldWithLabel("mapWidth ",fileReader.getMapWidth());
        this.mapHeight       = new TextFieldWithLabel("mapHeight ",fileReader.getMapHeight());
        this.jungleRatio     = new TextFieldWithLabel("jungleRatio ",fileReader.getJungleRatio());
        this.startAnimalNum  = new TextFieldWithLabel("StartAnimalNumber ",fileReader.getStartAnimalNum());

        this.add(headerText);
        this.add(this.GrassPerDay);
        this.add(this.DailyEnergyCost);
        this.add(this.GrassEnergy);
        this.add(this.StartEnergy);
        this.add(this.mapWidth);
        this.add(this.mapHeight);
        this.add(this.jungleRatio);
        this.add(this.startAnimalNum);

        JButton buttonStartSimulationOnOneBoard  = new JButton("Start Simulation On One Board");
        buttonStartSimulationOnOneBoard.setActionCommand("one");
        buttonStartSimulationOnOneBoard.addActionListener(this);

        JButton buttonStartSimulationOnTwoBoards = new JButton("Start Simulation On Two Boards");
        buttonStartSimulationOnTwoBoards.addActionListener(this);
        buttonStartSimulationOnTwoBoards.setActionCommand("two");

        this.add(buttonStartSimulationOnOneBoard);
        this.add(buttonStartSimulationOnTwoBoards);
        this.setLayout(new GridLayout(11,1));
        this.setSize(400,672);

        this.setVisible(true);
    }


    public int getGrassPerDayValue(){

        return this.GrassPerDay.getValue();
    }

    public int getGrassEnergy(){
        return this.GrassEnergy.getValue();
    }
    public int getDailyEnergyCost(){
        return this.DailyEnergyCost.getValue();
    }
    public int getStartEnergy(){
        return this.StartEnergy.getValue();
    }

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        switch(e.getActionCommand()){
            case "one":
                this.interfaceBelongTo.oneSimulationStart();
                break;
            case "two":
                this.interfaceBelongTo.twoSimulationStart();
                break;
        }

    }

    public int getMapWidth(){
        return this.mapWidth.getValue();
    }
    public int getMapHeight(){
        return this.mapHeight.getValue();
    }
    public int getJungleRatio(){
        return this.jungleRatio.getValue();
    }
    public int getStartAnimalNum(){return this.startAnimalNum.getValue();}

    private class TextFieldWithLabel extends JPanel{
        private final JLabel label;
        private final JTextField textField;

        private TextFieldWithLabel(String labelText, Integer textFieldDefault){

            this.label = new JLabel(labelText);
            this.label.setSize(100,20);
            this.textField = new JTextField();
            this.textField.setFont(new Font(Font.SERIF, Font.PLAIN,  20));
            this.textField.setSize(100,20);
            this.textField.setText(textFieldDefault.toString());
            this.setLayout(new GridLayout(1,2));
            this.add(this.label);
            this.add(this.textField);

        }

        public int getValue(){
            return Integer.parseInt(this.textField.getText());
        }

    }

}
