package edu.umn.d.cs1632;

import java.io.IOException;
import com.opencsv.exceptions.CsvException;

public class Main {
		
    public static void main(String[] args) throws IOException, CsvException {
       // Create new session for CSVFunctions
       CSVFunctions session = new CSVFunctions();
       
       // Start by initializing the CSV file
       session.read();
       
       // Begin query
       session.query();
    }
}