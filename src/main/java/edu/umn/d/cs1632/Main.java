package edu.umn.d.cs1632;


import java.io.IOException;
import com.opencsv.exceptions.CsvException;

public class Main {
		
    public static void main(String[] args) throws IOException, CsvException {
       
       CSVFunctions session = new CSVFunctions();
       session.read();
       session.query();
    }
}