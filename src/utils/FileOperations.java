package utils;

import entities.DataPoint;
import exceptions.NotEnoughDataException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileOperations {
    public static List<DataPoint> readStockData(File file) {
        List<DataPoint> dataPoints = new ArrayList<>();
        try(Stream<String> lines = Files.lines(Paths.get(file.getPath()))) {
            dataPoints  = lines.map(line -> Arrays.asList(line.split(Constants.COMMA_DELIMITER)))
                    .map(
                            list -> new DataPoint(
                                    list.get(0),
                                    LocalDate.parse(list.get(1), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                                    Double.parseDouble(list.get(2)) )
                    ).collect(Collectors.toList());
            if(dataPoints.size() < 10) {
                throw new NotEnoughDataException("less than 10 entries in this file");
            }
        } catch(IOException e) {
            System.out.println("Problem reading file" + ": " + e.getMessage());
        } catch (NotEnoughDataException e) {
            System.out.println("Less than 10 rows in this file: " + file.getName());
        }
        return dataPoints;
    }

    public static void writeOutputFile(String filename, List<DataPoint> data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("Stock-ID, Timestamp, stock-price\n");
        for(DataPoint entry : data) {
            writer.write(
                    entry.stockId() + "," +
                            entry.timestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "," +
                            entry.price() + "\n"
            );
        }
        writer.flush();
    }
}
