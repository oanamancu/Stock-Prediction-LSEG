import entities.DataPoint;
import stocksMagic.StockProcessor;
import utils.Constants;
import utils.FileOperations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        File rootDir = new File(Constants.INPUT_PATH);
        File[] dataDirs = rootDir.listFiles(File::isDirectory);
        int numFiles = getSampleSize();

        if(dataDirs == null || dataDirs.length == 0) {
            System.out.println("no stock directories found");
            return;
        }

        for(File dir : dataDirs) {
            File[] files = dir.listFiles((file, name) -> name.endsWith(".csv"));
            if(files == null || files.length == 0) {
                System.out.println("No CSV files found in " + dir.getName());
                continue;
            }

            int howManny = Math.min(files.length, numFiles);

            for(int i = 0; i < howManny; i++) {
                File file = files[i];
                try {
                    List<DataPoint> dataPoints = StockProcessor.getTenDataPoints(file);
                    List<DataPoint> predictions = StockProcessor.predictNext3Values(dataPoints);

                    dataPoints.addAll(predictions);

                    String dirName = Constants.OUTPUT_PATH + file.getParentFile().getName();
                    Files.createDirectories(Path.of(dirName));
                    String outputFilename = Constants.OUTPUT_PATH + file.getParentFile().getName() + "//" + file.getName().replace(".csv", "_output.csv");
                    FileOperations.writeOutputFile(outputFilename, dataPoints);
                } catch (IOException e) {
                    System.out.println("Error processing file " + file.getName() + ": " + e.getMessage());
                }
            }

        }
    }

    private static int getSampleSize() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of files to be sampled for each Stock Exchange (1 or 2): ");
        int numFiles = scanner.nextInt();
        scanner.close();
        if(numFiles != 1 && numFiles != 2) {
            getSampleSize();
        }
        return numFiles;
    }
}