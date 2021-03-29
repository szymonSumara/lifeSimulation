package agh.cs.lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimalVisualisation extends JFrame implements ActionListener {

    private final Animal animal;
    private final JLabel labelChildrenNum;
    private final JLabel labelSuccessorsNum;
    private final JTextField textFieldDays;
    private final JLabel dayField;
    private final JLabel stateOfAnimal;
    private final JLabel animalEnergy;
    private final JLabel labelTextFieldDescription;
    private final JButton buttonSetFocusAnimal;

    public AnimalVisualisation(Animal animal){

        super("Evolution Simulator");
        this.animal = animal;
        setSize(336,200 );
        add(new JLabel("Gen type:\n " + animal.getGeneticCode()));

        this.labelChildrenNum = new JLabel();
        this.labelSuccessorsNum = new JLabel();
        this.dayField = new JLabel();
        this.stateOfAnimal = new JLabel();
        this.animalEnergy = new JLabel();
        this.textFieldDays = new JTextField();
        this.textFieldDays.setText("100");
        this.labelTextFieldDescription = new JLabel("Entry number of days");

        add(this.labelChildrenNum);
        add(this.labelSuccessorsNum);
        add(this.dayField);
        add(this.stateOfAnimal);
        add(this.animalEnergy);
        add(this.labelTextFieldDescription);
        add(this.textFieldDays);

        this.buttonSetFocusAnimal = new JButton("Follow this animal");
        this.buttonSetFocusAnimal.addActionListener(this);

        add(this.buttonSetFocusAnimal);

        setLayout(new GridLayout(9,1));
        setVisible(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){

       this.setVisible(false);this.animal.isFocused=true;
    }

    public void update(){
        this.labelChildrenNum.setText("Children:\n " + animal.childrenNum);
        this.labelSuccessorsNum.setText("Succesor:\n " + animal.successorNum);
        this.stateOfAnimal.setText("This animal is:\n " + ((this.animal.isDead()) ? " dead" : " live"));
        this.dayField.setText("Day: \n " + animal.getLiveTime());
        this.animalEnergy.setText("Energy: \n " + animal.getEnergy());

        if(this.animal.isDead()){
            this.labelTextFieldDescription.setVisible(false);
            this.textFieldDays.setVisible(false);
            this.buttonSetFocusAnimal.setVisible(false);
        }

    }

    public int getDaysNumber(){
        return Integer.parseInt(this.textFieldDays.getText());
    }

    public void showVisualisation(){
        this.update();
        this.setVisible(true);
    }

}
