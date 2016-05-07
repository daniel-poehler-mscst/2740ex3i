package ex3i;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import java.awt.event.FocusEvent;

public class ExamForm extends JFrame implements ActionListener, ListSelectionListener, FocusListener {

	private JPanel contentPane;
	private JLabel questNumLabel;
	private JList responsesList;
	private DefaultListModel responsesListModel;
	private JButton prevButton;
	private JTextField inputAnswerTextField;
	private JLabel resultLabel;
	private JButton nextButton;
	private JButton calcPassButton;
	private JButton calcCorrectButton;
	private JButton calcIncorrectButton;
	private JButton listIncorrectButton;
	private DriverExam exam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExamForm frame = new ExamForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ExamForm() {
		setTitle("Ex3I Driver Exam");
		setFont(new Font("Segoe UI", Font.PLAIN, 13));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 339, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblResponses = new JLabel("Responses:");
		lblResponses.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblResponses.setBounds(12, 13, 84, 16);
		contentPane.add(lblResponses);
		
		JLabel lbl2 = new JLabel("Result:");
		lbl2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lbl2.setBounds(122, 13, 56, 16);
		lbl2.setBorder(null);
		contentPane.add(lbl2);
		
		questNumLabel = new JLabel("#0:");
		questNumLabel.setBounds(12, 282, 56, 21);
		contentPane.add(questNumLabel);
		
		resultLabel = new JLabel("");
		resultLabel.setBounds(132, 42, 176, 39);
		resultLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(resultLabel);
		
		calcPassButton = new JButton("Pass");
		calcPassButton.addActionListener(this);
		calcPassButton.setBounds(129, 104, 112, 25);
		contentPane.add(calcPassButton);
		
		calcCorrectButton = new JButton("Correct");
		calcCorrectButton.addActionListener(this);
		calcCorrectButton.setBounds(129, 142, 112, 25);
		contentPane.add(calcCorrectButton);
		
		calcIncorrectButton = new JButton("Incorrect");
		calcIncorrectButton.addActionListener(this);
		calcIncorrectButton.setBounds(129, 180, 112, 25);
		contentPane.add(calcIncorrectButton);
		
		listIncorrectButton = new JButton("List Incorrect");
		listIncorrectButton.addActionListener(this);
		listIncorrectButton.setBounds(129, 218, 112, 25);
		contentPane.add(listIncorrectButton);
		
		JList questNumList = new JList();
		questNumList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		questNumList.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		questNumList.setBounds(22, 42, 44, 218);
		questNumList.setBackground(UIManager.getColor("Label.background"));
		contentPane.add(questNumList);
		
		responsesList = new JList();
		responsesList.addListSelectionListener(this);
		responsesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		responsesList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		responsesList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		responsesList.setBounds(66, 42, 44, 224);
		contentPane.add(responsesList);
		
		prevButton = new JButton("Prev");
		prevButton.addActionListener(this);
		prevButton.setEnabled(false);
		prevButton.setBounds(130, 266, 77, 25);
		contentPane.add(prevButton);
		
		nextButton = new JButton("Next");
		nextButton.addActionListener(this);
		nextButton.setBounds(130, 294, 77, 25);
		contentPane.add(nextButton);
		
		inputAnswerTextField = new JTextField();
		inputAnswerTextField.addFocusListener(this);
		inputAnswerTextField.setBounds(62, 279, 48, 24);
		contentPane.add(inputAnswerTextField);
		inputAnswerTextField.setColumns(10);
		
		DriverExamObjMapper mapper = new DriverExamObjMapper("DriverExam.txt");
		this.exam = mapper.getDriverExam();
		this.responsesListModel = exam.getAnswers();
		responsesList.setModel(this.responsesListModel);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nextButton) {
			do_nextButton_actionPerformed(e);
		}
		if (e.getSource() == prevButton) {
			do_prevButton_actionPerformed(e);
		}
		if (e.getSource() == listIncorrectButton) {
			do_listIncorrectButton_actionPerformed(e);
		}
		if (e.getSource() == calcIncorrectButton) {
			do_calcIncorrectButton_actionPerformed(e);
		}
		if (e.getSource() == calcCorrectButton) {
			do_calcCorrectButton_actionPerformed(e);
		}
		if (e.getSource() == calcPassButton) {
			do_calcPassButton_actionPerformed(e);
		}		
	}
	protected void do_calcPassButton_actionPerformed(ActionEvent e) {
		//go to driverexam object and calculate results
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0) {
			resultLabel.setText("Invalid response #" + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		} else {
			if (exam.passed()) resultLabel.setText("You passed");
			else resultLabel.setText("You failed");
		}
	}
	protected void do_calcCorrectButton_actionPerformed(ActionEvent e) {
		//call appropriate function and display as integer
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0) {
			resultLabel.setText("Invalid response #" + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		} else {
			String str = String.format("%.0f", exam.totalCorrect());
			resultLabel.setText("Total correct: " + str);
		}		
	}
	protected void do_calcIncorrectButton_actionPerformed(ActionEvent e) {
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0) {
			resultLabel.setText("Invalid response #" + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		} else {
			resultLabel.setText("Total incorrect: " + Integer.toString(exam.totalIncorrect()));
		}				
	}
	protected void do_listIncorrectButton_actionPerformed(ActionEvent e) {
		// return array, loop and add to display
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0) {
			resultLabel.setText("Invalid response #" + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		} else {
			String wrong = new String();
			int [] missed = exam.questionsMissed();
			int i = 0;
			while (i < missed.length && missed[i] > 0) {
				int a = missed[i];
				i++;
				wrong += (Integer.toString(a) + ", ");
			}
			resultLabel.setText("Incorrect: #" + wrong);
		}						
	}	
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == responsesList) {
			do_responsesList_valueChanged(e);
		}
	}
	protected void do_responsesList_valueChanged(ListSelectionEvent e) {
        questNumLabel.setText("#" + Integer.toString((responsesList.getSelectedIndex() + 1)));
        inputAnswerTextField.setText((String)responsesList.getSelectedValue());    

        prevButton.setEnabled(true);
        nextButton.setEnabled(true);
        if (responsesList.getSelectedIndex() == responsesListModel.getSize() - 1)
            nextButton.setEnabled(false);
        if (responsesList.getSelectedIndex() == 0) 
            prevButton.setEnabled(false);
        inputAnswerTextField.requestFocus();        
	}
	public void focusGained(FocusEvent arg0) {
		if (arg0.getSource() == inputAnswerTextField) {
			do_textField_focusGained(arg0);
		}
	}
	public void focusLost(FocusEvent arg0) {
	}

	protected void do_prevButton_actionPerformed(ActionEvent e) {
        this.responsesListModel.setElementAt(
                inputAnswerTextField.getText().toUpperCase(), 
                responsesList.getSelectedIndex());
        responsesList.setSelectedIndex(responsesList.getSelectedIndex() - 1);
        questNumLabel.setText("#" + Integer.toString((responsesList.getSelectedIndex() + 1)));
        inputAnswerTextField.setText((String)responsesList.getSelectedValue());    

        nextButton.setEnabled(true);
        if (responsesList.getSelectedIndex() == 0) 
            prevButton.setEnabled(false);
        inputAnswerTextField.requestFocus();		
	}
	protected void do_nextButton_actionPerformed(ActionEvent e) {
        this.responsesListModel.setElementAt(
                inputAnswerTextField.getText().toUpperCase(), 
                responsesList.getSelectedIndex());
        responsesList.setSelectedIndex(responsesList.getSelectedIndex() + 1);
        questNumLabel.setText("#" + Integer.toString((responsesList.getSelectedIndex() + 1)));
        inputAnswerTextField.setText((String)responsesList.getSelectedValue());
        
        prevButton.setEnabled(true);
        if (responsesList.getSelectedIndex() == responsesListModel.getSize() - 1)
            nextButton.setEnabled(false);
        inputAnswerTextField.requestFocus();
	}
	protected void do_textField_focusGained(FocusEvent arg0) {
		inputAnswerTextField.selectAll();
	}
}
