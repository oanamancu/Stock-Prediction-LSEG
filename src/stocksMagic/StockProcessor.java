package stocksMagic;

import entities.DataPoint;
import utils.FileOperations;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StockProcessor {

    public static List<DataPoint> getTenDataPoints(File file) {
        List<DataPoint> allData = FileOperations.readStockData(file);

        Random random = new Random();
        int startIndex = random.nextInt(allData.size() - 9);
        return allData.subList(startIndex, startIndex + 10);
    }

    public static List<DataPoint> predictNext3Values(List<DataPoint>dataPoints) {
        List<Double> prices = dataPoints.stream().map(DataPoint::price).collect(Collectors.toList());
        Collections.sort(prices, Collections.reverseOrder());

        double n = dataPoints.get(dataPoints.size() - 1).price();
        double nPlus1 = prices.get(1);
        double nPlus2 = n + (nPlus1 - n) / 2;
        double nPlus3 = nPlus2 + (nPlus2 - nPlus1) / 4;

        LocalDate lastTimeStamp = dataPoints.get(dataPoints.size() - 1).timestamp();
        String stockId = dataPoints.getFirst().stockId();
        List<DataPoint> predictions = new ArrayList<>();

        predictions.add(new DataPoint(stockId, lastTimeStamp.plusDays(1), nPlus1));
        predictions.add(new DataPoint(stockId, lastTimeStamp.plusDays(2), nPlus2));
        predictions.add(new DataPoint(stockId, lastTimeStamp.plusDays(3), nPlus3));

        return predictions;
    }
}
