package ex3i;

import javax.swing.DefaultListModel;

public class Main {

	public static void main(String[] args) {
//		char[] answers = {'B', 'D', 'A', 'A', 'C', 'A', 'B', 'A', 'C', 'D'};
//		DefaultListModel answersListModel = new DefaultListModel();
//		answersListModel.addElement("B");
//		answersListModel.addElement("D");
//		answersListModel.addElement("A");
//		answersListModel.addElement("A");
//		answersListModel.addElement("C");
//		answersListModel.addElement("A");
//		answersListModel.addElement("B");
//		answersListModel.addElement("A");
//		answersListModel.addElement("C");
//		answersListModel.addElement("D");
//		DriverExam exam = new DriverExam(answersListModel);
		DriverExamObjMapper mapper = new DriverExamObjMapper("DriverExam2.txt");
		DriverExam exam = mapper.getDriverExam();
		
		DefaultListModel responsesListModel = new DefaultListModel();
		responsesListModel.addElement("B");
		responsesListModel.addElement("D");
		responsesListModel.addElement("A");
		responsesListModel.addElement("A");
		responsesListModel.addElement("C");
		responsesListModel.addElement("A");
		responsesListModel.addElement("B");
		responsesListModel.addElement("A");
		responsesListModel.addElement("C");
		responsesListModel.addElement("D");
		exam.setResponses(responsesListModel);
		
//		if (exam.validate() == -1) 
//			System.out.println("All responses valid");
//		else 
		System.out.println("First invalid response: " + exam.validate());
		
		System.out.println("Correct: " + exam.totalCorrect());
		System.out.println("Incorrect: " + exam.totalIncorrect());
		
		if (exam.passed())
			System.out.println("Passed test");
		else
			System.out.println("Try again");
		
		System.out.print("Questions missed: ");
		int [] missed = exam.questionsMissed();
		int i = 0;
		while (i < missed.length && missed[i] > 0) {
			System.out.print(" " + missed[i]);
			i++;
		}
		
		return ;
	}

}
