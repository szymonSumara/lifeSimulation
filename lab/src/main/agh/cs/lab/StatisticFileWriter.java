package agh.cs.lab;

import java.io.FileWriter;
import java.io.IOException;

public class StatisticFileWriter {

    private final Statistic statistic;

    StatisticFileWriter(Statistic statistic) {
        this.statistic = statistic;
    }

    public void writeStatisticToFile(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            for (String line : statistic.archieStats)
                fileWriter.write(line);

            fileWriter.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
