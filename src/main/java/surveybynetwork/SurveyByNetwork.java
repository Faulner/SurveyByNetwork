package surveybynetwork;

import java.awt.*;
import java.awt.event.*;
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

    ArrayList<SurveyRecord> surveyRecords = new ArrayList();
    Color localGreen = Color.decode("#267F00");
    Color localYellow = Color.decode("#FFFEEB");
    Color localGrey = Color.decode("#EFF2F7");
    Color localBlue = Color.decode("#1F497D");
    Color localEcruWhite = Color.decode("#F7F9F1");
    Dimension frameSize = new Dimension(1000, 700);
    JButton btnSortByNumber, btnSortByTopic, btnSortByQuestion, btnExit, btnSend, btnDisplayBinaryTree,
            btnPreOrderDisplay, btnInOrderDisplay, btnPostOrderDisplay, btnPreOrderSave, btnInOrderSave, btnPostOrderSave;
    JLabel lblTopic, lblQn, lblA, lblB, lblC, lblD, lblE, lblSort, lblCorrectAnswer, lblLinkedList, lblBinaryTree,
            lblPreOrder, lblInOrder, lblPostOrder;
    JTextArea txtWord1, txtWord2, txtTopic, txtQn, txtA, txtB, txtC, txtD, txtE, txtCorrectAnswer, txtLinkedList,
            txtBinaryTree;
    JPanel basePanel, questionTablePanel, questionFooterPanel, answerTablePanel, sendPanel, displayPanel;
    JTable questionTable;
    QuestionModel questionModel;


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

        readFile("SurveyByNetwork_SurveyQuestions.txt");

        renderBase(baseLayout);
        renderAnswerTable(baseLayout);
        renderQuestionTable(baseLayout);
        renderSendPanel(baseLayout);
        renderDisplayPanel(baseLayout);

        this.addWindowListener(this);
    }

//</editor-fold>

    
//<editor-fold defaultstate="collapsed" desc="GUI Construction">

    //---------------------------------------------------------------------------------------------------
    //  Set up GUI Components
    //---------------------------------------------------------------------------------------------------

    public JLabel locateALabel(JPanel parentPanel, SpringLayout parentLayout, JLabel myLabel, String  LabelCaption, int x, int y)
    {
        myLabel = new JLabel(LabelCaption);
        myLabel.setForeground(localBlue);
        parentPanel.add(myLabel);
        parentLayout.putConstraint(SpringLayout.WEST, myLabel, x, SpringLayout.WEST, this);
        parentLayout.putConstraint(SpringLayout.NORTH, myLabel, y, SpringLayout.NORTH, this);
        return myLabel;
    }

    public void locateSortButtons(JPanel panel, SpringLayout myButtonLayout)
    {
        btnSortByNumber = locateSortButton(panel, myButtonLayout, btnSortByNumber, "Qn #", 235, -25, 100, 25);
        btnSortByTopic = locateSortButton(panel, myButtonLayout, btnSortByTopic, "Topic", 335, -25, 100, 25);
        btnSortByQuestion = locateSortButton(panel, myButtonLayout, btnSortByQuestion, "Answer", 435, -25, 100, 25);
    }

    public void locateSortLabel(JPanel panel, SpringLayout myButtonLayout)
    {
        lblSort = locateALabel(panel, myButtonLayout, lblSort, "Sort by:", 175,10);
    }
    
    public void locateAnswerPanelTextAreas(SpringLayout myTextAreaLayout)
    {
        txtTopic = locateATextArea(answerTablePanel, myTextAreaLayout, txtTopic, 75, 15, 1, 25 );
        txtQn = locateATextArea(answerTablePanel, myTextAreaLayout, txtQn, 75, 40, 4, 25 );
        txtQn.setLineWrap(true);
        txtQn.setWrapStyleWord(true);
        txtA = locateATextArea(answerTablePanel, myTextAreaLayout, txtA, 75, 115, 1, 25 );
        txtB = locateATextArea(answerTablePanel, myTextAreaLayout, txtB, 75, 140, 1, 25 );
        txtC = locateATextArea(answerTablePanel, myTextAreaLayout, txtC, 75, 165, 1, 25 );
        txtD = locateATextArea(answerTablePanel, myTextAreaLayout, txtD, 75, 190, 1, 25 );
        txtE = locateATextArea(answerTablePanel, myTextAreaLayout, txtE, 75, 215, 1, 25 );
    }
    
    public void locateAnswerPanelLabels(JPanel parentPanel, SpringLayout parentLayout)
    {
        lblTopic = locateALabel(parentPanel, parentLayout, lblTopic, "Topic:", 15,15);
        lblQn = locateALabel(parentPanel, parentLayout, lblQn, "Qn:", 15,40);
        lblA = locateALabel(parentPanel, parentLayout, lblA, "A:", 15,115);
        lblB = locateALabel(parentPanel, parentLayout, lblB, "B:", 15,140);
        lblC = locateALabel(parentPanel, parentLayout, lblC, "C:", 15,165);
        lblD = locateALabel(parentPanel, parentLayout, lblD, "D:", 15,190);
        lblE = locateALabel(parentPanel, parentLayout, lblE, "E:", 15,215);
    }

    public JTextArea locateATextArea(JPanel parentPanel, SpringLayout parentLayout, JTextArea myTextArea, int x, int y, int w, int h)
    {    
        myTextArea = new JTextArea(w,h);
        parentPanel.add(myTextArea);
        parentLayout.putConstraint(SpringLayout.WEST, myTextArea, x, SpringLayout.WEST, this);
        parentLayout.putConstraint(SpringLayout.NORTH, myTextArea, y, SpringLayout.NORTH, this);
        myTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        return myTextArea;
    }

    public JButton locateAButton(SpringLayout myButtonLayout, JButton myButton, String  ButtonCaption, int x, int y, int w, int h)
    {    
        myButton = new JButton(ButtonCaption);
        basePanel.add(myButton);
        myButton.addActionListener(this);
        myButtonLayout.putConstraint(SpringLayout.WEST, myButton, x, SpringLayout.WEST, this);
        myButtonLayout.putConstraint(SpringLayout.NORTH, myButton, y, SpringLayout.NORTH, this);
        myButton.setPreferredSize(new Dimension(w,h));
        return myButton;
    }

    public JButton locateSortButton(JPanel panel, SpringLayout myButtonLayout, JButton myButton, String  ButtonCaption, int x, int y, int w, int h)
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

    public static void bubbleSort(ArrayList<SurveyRecord> arr)
    {
        for(int j=0; j<arr.size(); j++)
        {
            for(int i=j+1; i<arr.size(); i++)
            {
                if((arr.get(i)).toString().compareToIgnoreCase(arr.get(j).toString())<0)
                {
                    SurveyRecord surveyRecord = arr.get(j);
                    arr.set(j, arr.get(i));
                    arr.set(i, surveyRecord);
                }
            }
        }
    }

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

    public void renderDisplayPanel(SpringLayout parentLayout)
    {
        // Create a panel to hold all other components
        displayPanel = new JPanel();
        parentLayout.putConstraint(SpringLayout.NORTH, displayPanel, 10, SpringLayout.SOUTH, sendPanel);
        parentLayout.putConstraint(SpringLayout.WEST, displayPanel, 10, SpringLayout.WEST, this);
        basePanel.add(displayPanel);

        displayPanel.setBackground(localYellow);
        displayPanel.setPreferredSize(new Dimension(965, 300));
        SpringLayout displayPanelLayout = new SpringLayout();
        displayPanel.setLayout(displayPanelLayout);

        lblLinkedList = new JLabel("Linked List:");
        lblLinkedList.setPreferredSize(new Dimension(961, 25));
        lblLinkedList.setForeground(localBlue);
        lblLinkedList.setOpaque(true);
        lblLinkedList.setBackground(localEcruWhite);
        displayPanel.add(lblLinkedList);
        displayPanelLayout.putConstraint(SpringLayout.WEST, lblLinkedList, 0, SpringLayout.WEST, displayPanel);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, lblLinkedList, 3, SpringLayout.NORTH, displayPanel);

        txtLinkedList = new JTextArea(3, 87);
        displayPanel.add(txtLinkedList);
        displayPanelLayout.putConstraint(SpringLayout.WEST, txtLinkedList, 0, SpringLayout.WEST, displayPanel);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, txtLinkedList, 0, SpringLayout.SOUTH, lblLinkedList);
        txtLinkedList.setBorder(BorderFactory.createLineBorder(Color.black));

        lblBinaryTree = new JLabel("Binary Tree:");
        lblBinaryTree.setPreferredSize(new Dimension(840, 25));
        lblBinaryTree.setForeground(localBlue);
        lblBinaryTree.setOpaque(true);
        lblBinaryTree.setBackground(localEcruWhite);
        displayPanel.add(lblBinaryTree);
        displayPanelLayout.putConstraint(SpringLayout.WEST, lblBinaryTree, 0, SpringLayout.WEST, displayPanel);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, lblBinaryTree, 100, SpringLayout.NORTH, displayPanel);

        txtBinaryTree = new JTextArea(3, 87);
        displayPanel.add(txtBinaryTree);
        displayPanelLayout.putConstraint(SpringLayout.WEST, txtBinaryTree, 0, SpringLayout.WEST, displayPanel);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, txtBinaryTree, 0, SpringLayout.SOUTH, lblBinaryTree);
        txtBinaryTree.setBorder(BorderFactory.createLineBorder(Color.black));

        btnDisplayBinaryTree = new JButton("Display");
        btnDisplayBinaryTree.addActionListener(this);
        displayPanelLayout.putConstraint(SpringLayout.WEST, btnDisplayBinaryTree, 0, SpringLayout.EAST, lblBinaryTree);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, btnDisplayBinaryTree, 100, SpringLayout.NORTH, displayPanel);
        btnDisplayBinaryTree.setPreferredSize(new Dimension(120,25));
        displayPanel.add(btnDisplayBinaryTree);

        //, btnInOrderDisplay, btnPostOrderDisplay, , btnInOrderSave, btnPostOrderSave;
        //, , lblPostOrder;
        // pre-order
        lblPreOrder = new JLabel("Pre-Order");
        lblPreOrder.setPreferredSize(new Dimension(200, 25));
        lblPreOrder.setForeground(Color.WHITE);
        lblPreOrder.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreOrder.setOpaque(true);
        lblPreOrder.setBackground(localBlue);
        displayPanel.add(lblPreOrder);
        displayPanelLayout.putConstraint(SpringLayout.WEST, lblPreOrder, 30, SpringLayout.WEST, displayPanel);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, lblPreOrder, 200, SpringLayout.NORTH, displayPanel);

        btnPreOrderDisplay = new JButton("Display");
        btnPreOrderDisplay.addActionListener(this);
        displayPanelLayout.putConstraint(SpringLayout.WEST, btnPreOrderDisplay, 30, SpringLayout.WEST, displayPanel);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, btnPreOrderDisplay, 0, SpringLayout.SOUTH, lblPreOrder);
        btnPreOrderDisplay.setPreferredSize(new Dimension(100,25));
        displayPanel.add(btnPreOrderDisplay);

        btnPreOrderSave = new JButton("Save");
        btnPreOrderSave.addActionListener(this);
        displayPanelLayout.putConstraint(SpringLayout.WEST, btnPreOrderSave, 0, SpringLayout.EAST, btnPreOrderDisplay);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, btnPreOrderSave, 0, SpringLayout.SOUTH, lblPreOrder);
        btnPreOrderSave.setPreferredSize(new Dimension(100,25));
        displayPanel.add(btnPreOrderSave);

        // in-order
        lblInOrder = new JLabel("In-Order");
        lblInOrder.setPreferredSize(new Dimension(200, 25));
        lblInOrder.setForeground(Color.WHITE);
        lblInOrder.setHorizontalAlignment(SwingConstants.CENTER);
        lblInOrder.setOpaque(true);
        lblInOrder.setBackground(localBlue);
        displayPanel.add(lblInOrder);
        displayPanelLayout.putConstraint(SpringLayout.WEST, lblInOrder, 380, SpringLayout.WEST, displayPanel);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, lblInOrder, 200, SpringLayout.NORTH, displayPanel);

        btnInOrderDisplay = new JButton("Display");
        btnInOrderDisplay.addActionListener(this);
        displayPanelLayout.putConstraint(SpringLayout.WEST, btnInOrderDisplay, 380, SpringLayout.WEST, displayPanel);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, btnInOrderDisplay, 0, SpringLayout.SOUTH, lblInOrder);
        btnInOrderDisplay.setPreferredSize(new Dimension(100,25));
        displayPanel.add(btnInOrderDisplay);

        btnInOrderSave = new JButton("Save");
        btnInOrderSave.addActionListener(this);
        displayPanelLayout.putConstraint(SpringLayout.WEST, btnInOrderSave, 0, SpringLayout.EAST, btnInOrderDisplay);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, btnInOrderSave, 0, SpringLayout.SOUTH, lblInOrder);
        btnInOrderSave.setPreferredSize(new Dimension(100,25));
        displayPanel.add(btnInOrderSave);

        // post-order
        lblPostOrder = new JLabel("Post-Order");
        lblPostOrder.setPreferredSize(new Dimension(200, 25));
        lblPostOrder.setForeground(Color.WHITE);
        lblPostOrder.setHorizontalAlignment(SwingConstants.CENTER);
        lblPostOrder.setOpaque(true);
        lblPostOrder.setBackground(localBlue);
        displayPanel.add(lblPostOrder);
        displayPanelLayout.putConstraint(SpringLayout.WEST, lblPostOrder, 730, SpringLayout.WEST, displayPanel);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, lblPostOrder, 200, SpringLayout.NORTH, displayPanel);

        btnPostOrderDisplay = new JButton("Display");
        btnPostOrderDisplay.addActionListener(this);
        displayPanelLayout.putConstraint(SpringLayout.WEST, btnPostOrderDisplay, 730, SpringLayout.WEST, displayPanel);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, btnPostOrderDisplay, 0, SpringLayout.SOUTH, lblPostOrder);
        btnPostOrderDisplay.setPreferredSize(new Dimension(100,25));
        displayPanel.add(btnPostOrderDisplay);

        btnPostOrderSave = new JButton("Save");
        btnPostOrderSave.addActionListener(this);
        displayPanelLayout.putConstraint(SpringLayout.WEST, btnPostOrderSave, 0, SpringLayout.EAST, btnPostOrderDisplay);
        displayPanelLayout.putConstraint(SpringLayout.NORTH, btnPostOrderSave, 0, SpringLayout.SOUTH, lblPostOrder);
        btnPostOrderSave.setPreferredSize(new Dimension(100,25));
        displayPanel.add(btnPostOrderSave);
    }


    public void renderSendPanel(SpringLayout parentLayout)
    {
        // Create a panel to hold all other components
        sendPanel = new JPanel();
        parentLayout.putConstraint(SpringLayout.NORTH, sendPanel, 10, SpringLayout.SOUTH, questionTablePanel);
        parentLayout.putConstraint(SpringLayout.WEST, sendPanel, 10, SpringLayout.WEST, this);
        basePanel.add(sendPanel);


        sendPanel.setBackground(localYellow);
        sendPanel.setPreferredSize(new Dimension(965, 45));
        SpringLayout sendPanelLayout = new SpringLayout();
        sendPanel.setLayout(sendPanelLayout);

        btnExit = new JButton("Exit");
        btnExit.setMnemonic(KeyEvent.VK_E);
        btnExit.addActionListener(this);
        sendPanelLayout.putConstraint(SpringLayout.WEST, btnExit, 0, SpringLayout.WEST, sendPanel);
        sendPanelLayout.putConstraint(SpringLayout.NORTH, btnExit, 10, SpringLayout.NORTH, sendPanel);
        btnExit.setPreferredSize(new Dimension(250,25));
        sendPanel.add(btnExit);

        btnSend = new JButton("Send");
        btnSend.setMnemonic(KeyEvent.VK_S);
        btnSend.addActionListener(this);
        sendPanelLayout.putConstraint(SpringLayout.EAST, btnSend, 0, SpringLayout.EAST, sendPanel);
        sendPanelLayout.putConstraint(SpringLayout.NORTH, btnSend, 10, SpringLayout.NORTH, sendPanel);
        btnSend.setPreferredSize(new Dimension(250,25));
        sendPanel.add(btnSend);

        txtCorrectAnswer = new JTextArea(1, 25);
        sendPanel.add(txtCorrectAnswer);
        sendPanelLayout.putConstraint(SpringLayout.EAST, txtCorrectAnswer, -5, SpringLayout.WEST, btnSend);
        sendPanelLayout.putConstraint(SpringLayout.NORTH, txtCorrectAnswer, 14, SpringLayout.NORTH, sendPanel);
        txtCorrectAnswer.setBorder(BorderFactory.createLineBorder(Color.black));

        lblCorrectAnswer = new JLabel("Correct Answer:");
        lblCorrectAnswer.setForeground(localBlue);
        sendPanel.add(lblCorrectAnswer);
        sendPanelLayout.putConstraint(SpringLayout.EAST, lblCorrectAnswer, -5, SpringLayout.WEST, txtCorrectAnswer);
        sendPanelLayout.putConstraint(SpringLayout.NORTH, lblCorrectAnswer, 14, SpringLayout.NORTH, sendPanel);
    }

    public void renderQuestionTable(SpringLayout parentLayout)
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
                    displayQuestion(questionTable.getSelectedRow());
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
        questionTable.getTableHeader().setOpaque(false);
        questionTable.getTableHeader().setBackground(localEcruWhite);

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
        locateSortLabel(questionFooterPanel, questionFooterPanelLayout);
        locateSortButtons(questionFooterPanel, questionFooterPanelLayout);

    }

    public void renderAnswerTable(SpringLayout parentLayout)
    {
        answerTablePanel = new JPanel();
        answerTablePanel.setBackground(localEcruWhite);
        answerTablePanel.setBorder(BorderFactory.createLineBorder(localGreen));

        SpringLayout answerTableLayout = new SpringLayout();
        answerTablePanel.setLayout(answerTableLayout);
        basePanel.add(answerTablePanel);

        locateAnswerPanelTextAreas(answerTableLayout);
        locateAnswerPanelLabels(answerTablePanel, answerTableLayout);

        //Size and locate panel
        answerTablePanel.setPreferredSize(new Dimension(375, 250));
        parentLayout.putConstraint(SpringLayout.WEST, answerTablePanel, 600, SpringLayout.WEST, this);
        parentLayout.putConstraint(SpringLayout.NORTH, answerTablePanel, 10, SpringLayout.NORTH, this);
    }

    public void renderBase(SpringLayout baseLayout)
    {
        basePanel = new JPanel();
        basePanel.setLayout(baseLayout);
        basePanel.setBackground(localYellow);
        basePanel.setPreferredSize(getFrameSize());
        this.add(basePanel);
        setBackground(localYellow);
    }

    public void displayQuestion(int questionNumber)
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
        //System.out.println(e.getSource());
        //, btnExit, btnSend, btnDisplayBinaryTree,
        //btnPreOrderDisplay, btnInOrderDisplay, btnPostOrderDisplay, btnPreOrderSave, btnInOrderSave, btnPostOrderSave;

        if(e.getSource() == btnSortByNumber)
        {
            //questionModel.add();
            //questionTable.repaint();
        } else if (e.getSource() == btnSortByTopic) {
            bubbleSort(surveyRecords);
            questionTable.repaint();
        } else if (e.getSource() == btnSortByQuestion) {
            bubbleSort(surveyRecords);
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
