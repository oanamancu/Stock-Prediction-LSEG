package entities;

import java.time.LocalDate;

public record DataPoint(String stockId, LocalDate timestamp, double price) { }
