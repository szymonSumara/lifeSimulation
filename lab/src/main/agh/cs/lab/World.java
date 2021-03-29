package agh.cs.lab;

public class World {
    public static void main(String[] args) {

        //Uruchamia symulacje pomijając interfejs użytkownika. Pobiera parametry z pliku i tworzy jedno okno symulacji
        //new SimulationApplication(new StartDataFileReader("parameters.jsonc"));


        //Wcześniej uruchamia interfejs urzytkownika z wprowadzonymi danymi pobranymi z pliku.
        new SimulationApplication();
    }
}
