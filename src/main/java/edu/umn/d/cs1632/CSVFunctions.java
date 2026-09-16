package edu.umn.d.cs1632;

import java.io.FileNotFoundException;
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
	
	// Generic Invalid Selection Text
	public void invalidSel() {
		System.out.println("Please try again!");
	}
	
	// Print Report After Query
	public void printReport(List<String> index) {
		TypeChecker tc = new TypeChecker();
		
		System.out.print("\nValues: \n---------\n");
		for (int i = 0; i < index.size(); i++) {
			System.out.print(index.get(i) + " (" + tc.getType(index.get(i)) + ")\n");
		}
		
		System.out.println(tc.compareType(index));
	}
	
	// Initialize and Read the CSV File
	public void read() throws IOException, CsvException {
		Scanner sc = new Scanner(System.in);
		
		// Ask for file to open in src/data
		while (true) {
        	System.out.print("What file do you want to view? (place in src/data directory or type 'q' to quit)\n> ");
            workingFile = "src/data/" + sc.nextLine();
            
            if (workingFile.equalsIgnoreCase("src/data/q")) {
            	break;
            }
            
            try {
            	// Print the matrix
	            MArray mArray = new MArray(workingFile);
	            
	            // Create file reader, pipe file reader location into CSV reader
	    		fileReader = new FileReader(workingFile);
	    		csvReader = new CSVReader(fileReader);
	    		
	    		// Begin query function
	    		query();
	    		break;
            } catch (Exception e) {
            	System.out.println("\nFile does not exist!");
            }
		}	
	}
	
	// Query prompt
	public void query() throws IOException, CsvException {
		Scanner sc = new Scanner(System.in);
				
		// Assemble initial 2D List for data
		List<String[]> dataArray = csvReader.readAll();
				
		// Loop repeats for continuous prompting unless closed with Q
		while (true) {
			System.out.println();
			System.out.println("Enter your Query for data types");
	        System.out.print("(FORMAT: [V/H/M] [A] [B] [C] [D (if doing M query)]) or type 'q' to quit\n> ");
	        // Typed user response
	        String userInput = sc.nextLine();
	        // Trimmed user response, creates array of words/numbers/values separated by spaces
	        String[] splitInput = userInput.split("\\s+");       	
		        
	        // Vertical selection
        	if (splitInput[0].equalsIgnoreCase("V") && splitInput.length == 4) {
	        	
        		try {
        			// Request user for desired Column, RowStart, and RowEnd
        			int inputColumn = Integer.parseInt(splitInput[1]);
        			int inputRowStart = Integer.parseInt(splitInput[2]);
        			int inputRowEnd = Integer.parseInt(splitInput[3]);
        			
        			// Check that RowStart <= RowEnd
        			if (inputRowStart > inputRowEnd) {
        				System.out.println("Value for RowStart cannot be greater than RowEnd!");
        				throw new NumberFormatException();
        			}
        			// Check for zero values
        			if (inputRowStart < 0 || inputRowEnd < 0 || inputColumn < 0) {
        				System.out.println("Value(s) cannot be less than 0!");
        				throw new NumberFormatException();
        			}
        			// Check for OOB values for RowStart and RowEnd
        			if (inputRowStart > dataArray.size() || inputRowEnd > dataArray.size()) {
        				System.out.println("Value(s) for RowStart/RowEnd cannot exceed the dataset bounds!");
        				throw new NumberFormatException();
        			}
        			
        			// Initialize colIndex List to store selected cells
        			List<String> colIndex = new ArrayList<String>();

        			// Scan rows; For each row in dataArray starting from RowStart to RowEnd
        			for (String[] row : dataArray.subList(inputRowStart, inputRowEnd + 1)) {
        				
        				// Final check to ensure requested Column is within the bounds of the CSV's data
        			    if (inputColumn > row.length) {
        			    	System.out.println("Value for Column cannot exceed the dataset bounds!");
        			        throw new NumberFormatException(); 
        			    }
        			    
        			    // Add valid selections into colIndex List
        			    colIndex.add(row[inputColumn]);
        			}
        			
		        	// Print results of query
		        	printReport(colIndex);
	        	
        		} catch (NumberFormatException e) {
        			invalidSel();
        		}
        			
        	// Horizontal selection
	        } else if (splitInput[0].equalsIgnoreCase("H") && splitInput.length == 4) {
	        	try {
	        		// Request user for desired Row, ColStart, and ColEnd
        			int inputRow = Integer.parseInt(splitInput[1]);
        			int inputColStart = Integer.parseInt(splitInput[2]);
        			int inputColEnd = Integer.parseInt(splitInput[3]);

        			// Check that ColStart <= ColEnd
        			if (inputColStart > inputColEnd) {
        				System.out.println("Value for ColStart cannot be greater than ColEnd!");
        				throw new NumberFormatException();
        			}
        			
        			// Check for zero values
        			if (inputColStart < 0 || inputColEnd < 0 || inputRow < 0) {
        				System.out.println("Value(s) cannot be less than 0!");
        				throw new NumberFormatException();
        			}
        			
        			// Check for OOB value for Row
        			if (inputRow > dataArray.size()) {
        				System.out.println("Value for Row cannot exceed the dataset bounds!");
        				throw new NumberFormatException();
    				}
        			
        			// Assemble row array of all values in the requested Row
        			String[] row = dataArray.get(inputRow);

        			// Check for OOB values for ColStart and ColEnd
        			if (inputColStart > row.length || inputColEnd > row.length) {
        				System.out.println("Value(s) for ColStart/ColEnd cannot exceed the dataset bounds!");
        				throw new NumberFormatException();
    				}
        			
        			// Initialize rowIndex List to store selected cells
        			List<String> rowIndex = new ArrayList<String>();

        			// Add valid selections into rowIndex List
        			for (int i = inputColStart; i <= inputColEnd; i++) {
        				rowIndex.add(row[i]);
        			}
        			
        			// Print results of query
        			printReport(rowIndex);        			
	        	
        		} catch (NumberFormatException e) {
        			invalidSel();
        		}
	        	
	        // Matrix selection	
	        } else if (splitInput[0].equalsIgnoreCase("M") && splitInput.length == 5) {
	        	try {
	        		// Request user for desired RowStart, RowEnd, ColStart, and ColEnd
        			int inputRowStart = Integer.parseInt(splitInput[1]);
        			int inputRowEnd = Integer.parseInt(splitInput[2]);
        			int inputColStart = Integer.parseInt(splitInput[3]);
        			int inputColEnd = Integer.parseInt(splitInput[4]);
        			
        			// Check that RowStart <= RowEnd
        			if (inputRowStart > inputRowEnd) {
        				System.out.println("RowStart cannot be greater than RowEnd");
        				throw new NumberFormatException();
        			}
        			
        			// Check that ColStart <= ColEnd
        			if (inputColStart > inputColEnd) {
        				System.out.println("ColStart cannot be greater than ColEnd");
        				throw new NumberFormatException();
        			}

        			// Check for zero values
        			if (inputRowStart < 0 || inputRowEnd < 0|| inputColStart < 0 || inputColEnd < 0) {
        				System.out.println("Value(s) cannot be less than 0!");
        				throw new NumberFormatException();
        			}
        			
        			// Initialize matrixIndex List to store selected cells
        			List<String> matrixIndex = new ArrayList<String>();
        			
        			// Scan Rows
        			for (String[] row : dataArray.subList(inputRowStart, inputRowEnd + 1)) {
        				
        				// Check for OOB values for RowStart and RowEnd
        				if (inputRowStart > dataArray.size() || inputRowEnd > dataArray.size()) {
        			        System.out.println("Value for RowStart/RowEnd cannot exceed the dataset bounds!");
        			        throw new NumberFormatException(); 
        			    }
        				
        				for (int i = inputColStart; i <= inputColEnd; i++) {
        					// Check for OOB values for ColStart and ColEnd
        					if (inputColStart > row.length || inputColEnd > row.length) {
            			        System.out.println("Value for ColStart/ColEnd cannot exceed the dataset bounds!");
            			        throw new NumberFormatException(); 
            			    }
        					
        					// Add valid selections into matrixIndex List
    		        		matrixIndex.add(row[i]);
    		        	}
		        	}	        	
        			
        			// Print results of query
        			printReport(matrixIndex);
	        	
        		} catch (NumberFormatException e) {
        			invalidSel();
        		}
	        	
	        // Quit the program
	        } else if (splitInput[0].equalsIgnoreCase("Q")) {
	        	break;
	        	
	        } else {
	        	invalidSel();
	        }
		}
	}
}
