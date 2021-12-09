package surveybynetwork;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;


public class SurveyByNetwork extends JFrame implements WindowListener, ActionListener
{
    
//<editor-fold defaultstate="collapsed" desc="Declarations, main and Constructor">

    JLabel lblWord1, lblWord2, lblSent, lblTopic, lblQn, lblA, lblB, lblC, lblD, lblE, lblSort;
    JTextArea txtWord1, txtWord2, txtTopic, txtQn, txtA, txtB, txtC, txtD, txtE;
    JButton btnAdd;
    JButton btnSortByNumber;
    JButton btnSortByTopic;
    JButton btnSortByQuestion;
    ArrayList<SurveyRecord> surveyRecords = new ArrayList();
    JPanel basePanel, questionTablePanel, questionFooterPanel, answerTablePanel;
    JTable questionTable;
    QuestionModel questionModel;
    Dimension frameSize = new Dimension(1000, 700);
    Color localGreen = Color.decode("#267F00");
    Color localYellow = Color.decode("#FFFEEB");
    Color localGrey = Color.decode("#EFF2F7");
    Color localBlue = Color.decode("#1F497D");


    public static void main(String[] args)
    {
        SurveyByNetwork myFrame = new SurveyByNetwork();
        myFrame.setSize(myFrame.getFrameSize());
        myFrame.setLocation(400, 200);
        myFrame.setResizable(true);
        myFrame.setVisible(true);
    }

    public Dimension getFrameSize()
    {
        return frameSize;
    }

    public SurveyByNetwork()
    {
        setTitle("Survey by Network");
        SpringLayout appLayout = new SpringLayout();
        setLayout(appLayout);

        SpringLayout baseLayout = new SpringLayout();
        basePanel = new JPanel();
        basePanel.setLayout(baseLayout);
        basePanel.setBackground(localYellow);
        basePanel.setPreferredSize(getFrameSize());
        this.add(basePanel);
        //setBackground(Color.yellow);
        setBackground(localYellow);

        SpringLayout answerTableLayout = new SpringLayout();
        AnswerTable(baseLayout, answerTableLayout);
                
        LocateLabels(baseLayout);
        LocateTextAreas(baseLayout);
        LocateButtons(baseLayout);
        LocateAnswerPanelTextAreas(answerTableLayout);
        LocateAnswerPanelLabels(answerTablePanel, answerTableLayout);
        readFile("SurveyByNetwork_SurveyQuestions.txt");
        
        
        QuestionTable(baseLayout);
        
        
        this.addWindowListener(this);
    }

//</editor-fold>

    
//<editor-fold defaultstate="collapsed" desc="GUI Construction">

    //---------------------------------------------------------------------------------------------------
    //  Set up GUI Components
    //---------------------------------------------------------------------------------------------------

    public void LocateLabels(SpringLayout myLabelLayout)
    {
        //lblWord1  = LocateALabel(myLabelLayout, lblWord1, "Word 1:", 650, 500);
        //lblWord2 = LocateALabel(myLabelLayout, lblWord2, "Word 2:", 650, 650);
        //lblSent = LocateALabel(myLabelLayout, lblSent, "Sent:", 650, 750);
    }

    public JLabel LocateALabel(JPanel parentPanel, SpringLayout parentLayout, JLabel myLabel, String  LabelCaption, int x, int y)
    {
        myLabel = new JLabel(LabelCaption);
        myLabel.setForeground(localBlue);
        parentPanel.add(myLabel);
        parentLayout.putConstraint(SpringLayout.WEST, myLabel, x, SpringLayout.WEST, this);
        parentLayout.putConstraint(SpringLayout.NORTH, myLabel, y, SpringLayout.NORTH, this);
        return myLabel;
    }
   
    public void LocateTextAreas(SpringLayout myTextAreaLayout)
    {
        //txtWord1  = LocateATextArea(myTextAreaLayout, txtWord1, 100, 200, 1, 10);
        //txtWord2 = LocateATextArea(myTextAreaLayout, txtWord2, 100, 200, 1, 10);
    }

    public void LocateButtons(SpringLayout myButtonLayout)
    {
        //btnAdd = LocateAButton(myButtonLayout, btnAdd, "Add", 700, 500, 80, 25);
    }

    public void LocateSortButtons(JPanel panel, SpringLayout myButtonLayout)
    {
        btnSortByNumber = LocateSortButton(panel, myButtonLayout, btnSortByNumber, "Qn #", 235, -25, 100, 25);
        btnSortByTopic = LocateSortButton(panel, myButtonLayout, btnSortByTopic, "Topic", 335, -25, 100, 25);
        btnSortByQuestion = LocateSortButton(panel, myButtonLayout, btnSortByQuestion, "Answer", 435, -25, 100, 25);
    }

    public void LocateSortLabel(JPanel panel, SpringLayout myButtonLayout)
    {
        lblSort = LocateALabel(panel, myButtonLayout, lblSort, "Sort by:", 175,10);
    }
    
    public void LocateAnswerPanelTextAreas(SpringLayout myTextAreaLayout)
    {
        txtTopic = LocateATextArea(answerTablePanel, myTextAreaLayout, txtTopic, 75, 15, 1, 25 );
        txtQn = LocateATextArea(answerTablePanel, myTextAreaLayout, txtQn, 75, 40, 4, 25 );
        txtQn.setLineWrap(true);
        txtA = LocateATextArea(answerTablePanel, myTextAreaLayout, txtA, 75, 115, 1, 25 );
        txtB = LocateATextArea(answerTablePanel, myTextAreaLayout, txtB, 75, 140, 1, 25 );
        txtC = LocateATextArea(answerTablePanel, myTextAreaLayout, txtC, 75, 165, 1, 25 );
        txtD = LocateATextArea(answerTablePanel, myTextAreaLayout, txtD, 75, 190, 1, 25 );
        txtE = LocateATextArea(answerTablePanel, myTextAreaLayout, txtE, 75, 215, 1, 25 );
    }
    
    public void LocateAnswerPanelLabels(JPanel parentPanel, SpringLayout parentLayout)
    {
        lblTopic = LocateALabel(parentPanel, parentLayout, lblTopic, "Topic:", 15,15);
        lblQn = LocateALabel(parentPanel, parentLayout, lblQn, "Qn:", 15,40);
        lblA = LocateALabel(parentPanel, parentLayout, lblA, "A:", 15,115);
        lblB = LocateALabel(parentPanel, parentLayout, lblB, "B:", 15,140);
        lblC = LocateALabel(parentPanel, parentLayout, lblC, "C:", 15,165);
        lblD = LocateALabel(parentPanel, parentLayout, lblD, "D:", 15,190);
        lblE = LocateALabel(parentPanel, parentLayout, lblE, "E:", 15,215);
    }

    public JTextArea LocateATextArea(JPanel parentPanel, SpringLayout parentLayout, JTextArea myTextArea, int x, int y, int w, int h)
    {    
        myTextArea = new JTextArea(w,h);
        parentPanel.add(myTextArea);
        parentLayout.putConstraint(SpringLayout.WEST, myTextArea, x, SpringLayout.WEST, this);
        parentLayout.putConstraint(SpringLayout.NORTH, myTextArea, y, SpringLayout.NORTH, this);
        myTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        return myTextArea;
    }

    public JButton LocateAButton(SpringLayout myButtonLayout, JButton myButton, String  ButtonCaption, int x, int y, int w, int h)
    {    
        myButton = new JButton(ButtonCaption);
        basePanel.add(myButton);
        myButton.addActionListener(this);
        myButtonLayout.putConstraint(SpringLayout.WEST, myButton, x, SpringLayout.WEST, this);
        myButtonLayout.putConstraint(SpringLayout.NORTH, myButton, y, SpringLayout.NORTH, this);
        myButton.setPreferredSize(new Dimension(w,h));
        return myButton;
    }

    public JButton LocateSortButton(JPanel panel, SpringLayout myButtonLayout, JButton myButton, String  ButtonCaption, int x, int y, int w, int h)
    {
        myButton = new JButton(ButtonCaption);
        myButton.addActionListener(this);
        myButtonLayout.putConstraint(SpringLayout.WEST, myButton, x, SpringLayout.EAST, this);
        myButtonLayout.putConstraint(SpringLayout.NORTH, myButton, y, SpringLayout.SOUTH, this);
        myButton.setPreferredSize(new Dimension(w,h));
        questionFooterPanel.add(myButton);
        return myButton;
    }
    
    

//</editor-fold>
    
   
    
     public void readFile(String fileName)
    {
        // Try to read in the data and if an exception occurs go to the Catch section
        try
        {
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            int counter = 0;
            int objectarraycount = 0;
            String[] tempArray = new String[8];
            String line;
            
            while ((line = br.readLine()) != null)
            {
                tempArray[counter] = line;
                
                
                if(counter ==7)
                {
                    counter = 0;
                    if (objectarraycount > 0)
                    {
                        SurveyRecord surveyRecord = new SurveyRecord();
                        surveyRecord.setSurveyRecordDetails(tempArray);
                        surveyRecords.add(surveyRecord);
                    }
                    tempArray = new String[8];
                    objectarraycount++;
                }
                
                
                else
                {
                    counter++;
                }
                
            }
            
            br.close();            // Close the BufferedReader
            in.close();            // Close the DataInputStream
            fstream.close();       // Close the FileInputStream
        }
        catch (Exception e)
        {
            System.err.println("Error Reading File: " + e.getMessage());
        }
    }

    public void QuestionTable(SpringLayout parentLayout)
    { 
        // Create a panel to hold all other components
        questionTablePanel = new JPanel();
        questionTablePanel.setBackground(localYellow);
        questionTablePanel.setPreferredSize(new Dimension(550, 250));
        questionTablePanel.setBorder(BorderFactory.createLineBorder(localGreen));
        parentLayout.putConstraint(SpringLayout.WEST, questionTablePanel, 10, SpringLayout.WEST, this);
        parentLayout.putConstraint(SpringLayout.NORTH, questionTablePanel, 10, SpringLayout.NORTH, this);

        BorderLayout questionTableLayout = new BorderLayout();
        questionTablePanel.setLayout(questionTableLayout);
        basePanel.add(questionTablePanel);
        
        // Create column names
        String columnNames[] =
        { "#", "Topic", "Question"};
     

        // constructor of JTable model
	    questionModel = new QuestionModel(surveyRecords, columnNames);
        
        // Create a new table instance
        questionTable = new JTable(questionModel);
        questionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (questionTable.getSelectedRow() > -1) {
                    DisplayQuestion(questionTable.getSelectedRow());
                }
            }
        });
        questionTable.getColumnModel().getColumn(0).setPreferredWidth(15);
        questionTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        questionTable.getColumnModel().getColumn(2).setPreferredWidth(300);

        // Configure some of JTable's parameters
        questionTable.isForegroundSet();
        questionTable.setShowHorizontalLines(true);
        questionTable.setRowSelectionAllowed(true);
        questionTable.setColumnSelectionAllowed(false);
        questionTable.getTableHeader().setForeground(localBlue);

        // Change the text and background colours
        questionTable.setSelectionForeground(Color.white);
        questionTable.setSelectionBackground(localGreen);

        // Add the table to a scrolling pane, size and locate
        JScrollPane scrollPane = new JScrollPane(questionTable);
        scrollPane.setBackground(localGrey);
        TitledBorder titledBorder = new TitledBorder(""); //
        titledBorder = BorderFactory.createTitledBorder(titledBorder, "Survey Questions", TitledBorder.CENTER, TitledBorder.ABOVE_TOP);
        titledBorder.setTitleColor(localBlue);
        scrollPane.setBorder(titledBorder);

        //scrollPane.add();

        questionTablePanel.add(scrollPane, BorderLayout.CENTER);

        questionFooterPanel = new JPanel();
        questionTablePanel.add(questionFooterPanel, BorderLayout.SOUTH);
        questionFooterPanel.setBackground(localGrey);
        questionFooterPanel.setPreferredSize(new Dimension(550, 35));
        SpringLayout questionFooterPanelLayout = new SpringLayout();
        questionFooterPanel.setLayout(questionFooterPanelLayout);
        LocateSortLabel(questionFooterPanel, questionFooterPanelLayout);
        LocateSortButtons(questionFooterPanel, questionFooterPanelLayout);

    }

    public void AnswerTable(SpringLayout parentLayout, SpringLayout thisLayout)
    {
        answerTablePanel = new JPanel();
        answerTablePanel.setBackground(localGrey);
        answerTablePanel.setBorder(BorderFactory.createLineBorder(localGreen));
        answerTablePanel.setLayout(thisLayout);
        basePanel.add(answerTablePanel);

        //Size and locate panel
        answerTablePanel.setPreferredSize(new Dimension(375, 250));
        parentLayout.putConstraint(SpringLayout.WEST, answerTablePanel, 600, SpringLayout.WEST, this);
        parentLayout.putConstraint(SpringLayout.NORTH, answerTablePanel, 10, SpringLayout.NORTH, this);
    }

    public void DisplayQuestion(int questionNumber)
    {
        txtTopic.setText(questionModel.getValueAt(questionNumber, 1));
        txtQn.setText(questionModel.getValueAt(questionNumber, 2));
        txtA.setText(questionModel.getValueAt(questionNumber, 3));
        txtB.setText(questionModel.getValueAt(questionNumber, 4));
        txtC.setText(questionModel.getValueAt(questionNumber, 5));
        txtD.setText(questionModel.getValueAt(questionNumber, 6));
        txtE.setText(questionModel.getValueAt(questionNumber, 7));
    }


//<editor-fold defaultstate="collapsed" desc="Listeners">
    
    //---------------------------------------------------------------------------------------------------
    // Action and Window Listener
    //---------------------------------------------------------------------------------------------------

    public void actionPerformed(ActionEvent e)
    {
        System.out.println(e.getSource());
        if(e.getSource() == btnAdd)
        {
            questionModel.add(txtWord1.getText(),txtWord2.getText());
            questionTable.repaint();
        }
    }

    
    public void windowOpened(WindowEvent e)  {  }
    public void windowClosing(WindowEvent e)  { System.exit(0); }
    public void windowClosed(WindowEvent e)  {  }
    public void windowIconified(WindowEvent e)  {  }
    public void windowDeiconified(WindowEvent e)  {  }
    public void windowActivated(WindowEvent e)  {  }
    public void windowDeactivated(WindowEvent e)  {  }

//</editor-fold>
    
}
