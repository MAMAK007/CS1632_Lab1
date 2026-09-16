package edu.umn.d.cs1632;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;

public class CSVFunctions {
	private String workingFile;
	private CSVReader csvReader;
	private FileReader fileReader;
	
	public void invalidSel() {
		System.out.println("Invalid selection!");
		System.out.println("Please try again!");
	}
	
	public void printReport(List<String> index) {
		TypeChecker tc = new TypeChecker();
		System.out.println(tc.compareType(index));
    	
    	System.out.print("Values: ");
		for (int i = 0; i < index.size(); i++) {
			System.out.print(index.get(i) + " (" + tc.getType(index.get(i)) + "), ");
		}
	}
	
	public void read() throws IOException, CsvException {
		Scanner sc = new Scanner(System.in);
		
		while (true) {
        	System.out.print("What file do you want to view? (place in src/data directory)\n> ");
            workingFile = "src/data/" + sc.nextLine();
            try {
	            MArray mArray = new MArray(workingFile);
	            break;
            } catch (Exception e) {
            	;
            }
		}
		
		fileReader = new FileReader(workingFile);
		csvReader = new CSVReader(fileReader);
		
	}
	
	public void query() throws IOException, CsvException {
		Scanner sc = new Scanner(System.in);
				
		List<String[]> dataArray = csvReader.readAll();
		
		while (true) {
			System.out.println();
	        System.out.print("Query?\n> ");
	        String userInput = sc.nextLine();
	        
	        String[] splitInput = userInput.split("\\s+");       	
		        
        	if (splitInput[0].equalsIgnoreCase("V") || splitInput.length == 3) {
	        	
        		try {
        			int inputColumn = Integer.parseInt(splitInput[1]);
        			int inputRowStart = Integer.parseInt(splitInput[2]);
        			int inputRowEnd = Integer.parseInt(splitInput[3]);
        			
		        	List<String> colIndex = new ArrayList<String>();
		        	
		        	for (String[] row : dataArray.subList(inputRowStart, inputRowEnd + 1)) {
		        	    colIndex.add(row[inputColumn]);
		        	}
		        	
		        	printReport(colIndex);
	        	
        		} catch (NumberFormatException e) {
        			invalidSel();
        		}
        			        	
	        } else if (splitInput[0].equalsIgnoreCase("H") || splitInput.length == 3) {
	        	try {
        			int inputRow = Integer.parseInt(splitInput[1]);
        			int inputColStart = Integer.parseInt(splitInput[2]);
        			int inputColEnd = Integer.parseInt(splitInput[3]);
        			
        			String[] row = dataArray.get(inputRow);
        			
        			List<String> rowIndex = new ArrayList<String>();

        			for (int i = inputColStart; i <= inputColEnd; i++) {
        				rowIndex.add(row[i]);
        			}
        			
        			printReport(rowIndex);        			
	        	
        		} catch (NumberFormatException e) {
        			invalidSel();
        		}
	        } else if (splitInput[0].equalsIgnoreCase("M") || splitInput.length == 4) {
	        	try {
        			int inputRowStart = Integer.parseInt(splitInput[1]);
        			int inputRowEnd = Integer.parseInt(splitInput[2]);
        			int inputColStart = Integer.parseInt(splitInput[3]);
        			int inputColEnd = Integer.parseInt(splitInput[4]);
        			
        			List<String> matrixIndex = new ArrayList<String>();
        			
        			for (String[] row : dataArray.subList(inputRowStart, inputRowEnd + 1)) {
        				for (int i = inputColStart; i <= inputColEnd; i++) {
    		        		matrixIndex.add(row[i]);
    		        	}
		        	}	        	
        			
        			printReport(matrixIndex);
	        	
        		} catch (NumberFormatException e) {
        			invalidSel();
        		}
	        	
	        } else if (splitInput[0].equalsIgnoreCase("Q")) {
	        	break;
	        } else {
	        	invalidSel();
	        }
		}
	}
}
