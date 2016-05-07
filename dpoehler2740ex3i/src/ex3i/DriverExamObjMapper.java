package ex3i;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.DefaultListModel;

public class DriverExamObjMapper {
	private String fileName;
	private Scanner inputFile;
	private File file;
	
	public DriverExamObjMapper(String fileName) {
		super();
		this.fileName = fileName;
	}
	
	public boolean openInputFile() {
		boolean fileOpened = false;
		
	    // Open the file.
	    try {
	    	File file = new File(this.fileName);
			fileOpened = file.exists();

			if (fileOpened)	{			
				// Open the file.
			    this.inputFile = new Scanner(file);
			}
	    }
	    catch (IOException e) {}
		
		return fileOpened;
	}	
	
	public void closeInputFile() {
		if (this.inputFile != null) this.inputFile.close();
	}
	
	public DriverExam getDriverExam() {
		DriverExam exam = null;
		DefaultListModel driverExamCollection = new DefaultListModel();
		
		if (this.openInputFile()) {
			while (this.inputFile.hasNext()) {
				String answer = inputFile.nextLine();
				driverExamCollection.addElement(answer);
			}
			exam = new DriverExam(driverExamCollection);
		}
		
		this.closeInputFile();
		return exam;
	}
}
