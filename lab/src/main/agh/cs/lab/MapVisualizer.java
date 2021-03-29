package agh.cs.lab;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapVisualizer extends JPanel {

    private final EvolutionMap map;
    private JLabel[][] mapVisualisation;
    private boolean isStopped = false;

    public MapVisualizer(EvolutionMap map) {

        this.map = map;
        this.mapVisualisation = new JLabel[map.getNorthEastCorner().x+1][map.getNorthEastCorner().y+1];

        Border blackLine = BorderFactory.createLineBorder(Color.gray);

        for(int i=0;i<=this.map.getNorthEastCorner().x;i++)
            for(int j=0;j<=this.map.getNorthEastCorner().y;j++) {
                this.mapVisualisation[i][j] = new JLabel();

                if(map.getNorthEastCorner().x <= 150 || map.getNorthEastCorner().y <= 150)
                    this.mapVisualisation[i][j].setBorder(blackLine);

                this.mapVisualisation[i][j].setText(" ");
                this.mapVisualisation[i][j].setOpaque(true);
                mapVisualisation[i][j].setBackground(Color.lightGray);
                add(this.mapVisualisation[i][j]);
            }

        setLayout(new GridLayout(this.map.getNorthEastCorner().x+1,this.map.getNorthEastCorner().y+1));

    }


    public void stop(){
        this.isStopped=true;
    }
    public void run(){
        this.isStopped=false;
    }

    public void updateMapElementAt(Vector2d position) {
        if (this.map.getObjectAt(position) == null) {
            this.mapVisualisation[position.x][position.y].setBackground(Color.LIGHT_GRAY);
            this.mapVisualisation[position.x][position.y].addMouseListener(null);
        } else if (this.map.getObjectAt(position) instanceof Animal) {

            this.mapVisualisation[position.x][position.y].setBackground(((Animal) this.map.getObjectAt(position)).getColorOfAnimal());

            this.mapVisualisation[position.x][position.y].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                if(isStopped) {
                    Animal firstAnimal = (Animal) map.getObjectAt(position);
                    firstAnimal.showAnimalDetail();
                    }
                }
            });

        } else if (this.map.getObjectAt(position) instanceof Grass) {

            this.mapVisualisation[position.x][position.y].setBackground(Color.GREEN);
            this.mapVisualisation[position.x][position.y].addMouseListener(null);
        }
    }
}
