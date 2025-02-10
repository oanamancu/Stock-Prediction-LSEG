
# Stock Price Prediction

## Overview
This java application predicts the next 3 stock prices for each stock file provided, 
based on 10 consecutive data points starting from a random timestamp. 
For each stock exchange,the number of files should be specified.

## Prediction Logic
- first predicted (n+1) data point is same as the 2nd highest value present in the 10 data points
- n+2 data point has half the difference between n and n +1
- n+3 data point has 1/4th the difference between n+1 and n+2

## Setup Instructions

### **Perequisities**
- JDK 8 or higher

### **Directory Structure**
- **src** - java source code
- **data** - place the stock exchage folders along with the corresponding csv files here.
- **output** - generated output csv files by the program

### **CSV File Format**
Columns:
1. Stock-ID - String
2. Timestamp - dd-MM-yyyy
3. stock-price - double

### **Steps**

1. Clone repo
2. put input data into the data folder: create a folder for each stock exchange inside data and place the csv files accordingly
3. open in ide and run main
4. enter 1 or 2 - number of files for each stock exchange
5. find the output files with predictions in the output folder