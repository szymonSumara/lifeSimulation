package agh.cs.lab;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class StartDataFileReader implements ISimulationDataCollector {
    private JSONObject jsonObject;
    private boolean JSONFileWasLoad = true;
    StartDataFileReader(String filePath) {


        try {
            String contents = new String((Files.readAllBytes(Paths.get(filePath))));
            this.jsonObject = new JSONObject(contents);
        } catch (IOException e) {
           this.JSONFileWasLoad = false;
        }

    }

    public int getGrassEnergy() {


        if(!this.JSONFileWasLoad){
            return 0;
        }
        return Integer.parseInt(jsonObject.get("plantEnergy").toString());
    }

    public int getDailyEnergyCost() {
        if(!this.JSONFileWasLoad){
            return 0;
        }
        return Integer.parseInt(jsonObject.get("moveEnergy").toString());
    }

    public int getGrassPerDayValue() {
        if(!this.JSONFileWasLoad){
            return 0;
        }
        return 20;
    }

    public int getMapWidth() {
        if(!this.JSONFileWasLoad){
            return 0;
        }
        return Integer.parseInt(jsonObject.get("width").toString());
    }

    public int getMapHeight() {
        if(!this.JSONFileWasLoad){
            return 0;
        }
        return Integer.parseInt(jsonObject.get("height").toString());
    }

    public int getJungleRatio() {
        if(!this.JSONFileWasLoad){
            return 0;
        }
        return Integer.parseInt((jsonObject.get("jungleRatio").toString()));
    }

    public int getStartEnergy() {
        if(!this.JSONFileWasLoad){
            return 0;
        }
        return Integer.parseInt((jsonObject.get("startEnergy").toString()));
    }

    public int getStartAnimalNum() {
        if(!this.JSONFileWasLoad){
            return 0;
        }
        return Integer.parseInt(jsonObject.get("startAnimalNum").toString());
    }

}
