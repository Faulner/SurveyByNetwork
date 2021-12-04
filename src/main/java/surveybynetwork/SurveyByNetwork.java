package mainclass;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;



public class SurveyByNetwork extends JFrame implements WindowListener, ActionListener
{
    
//<editor-fold defaultstate="collapsed" desc="Declarations, main and Constructor">

    JLabel lblWord1, lblWord2, lblSent, lblTopic, lblQn, lblA, lblB, lblC, lblD;
    JTextArea txtWord1, txtWord2, txtTopic, txtQn, txtA, txtB, txtC, txtD;
    JButton btnAdd;
    ArrayList<Object[]> dataValues = new ArrayList();
    JPanel rightPanel = new JPanel();
    JTable table;
    MyModel wordModel;


    public static void main(String[] args)
    {
        JFrame myFrame = new SurveyByNetwork();
        myFrame.setSize(1000,700);
        myFrame.setLocation(400, 200);
        myFrame.setResizable(true);
        myFrame.setVisible(true);        
    }

    public SurveyByNetwork()
    {
        setTitle("Survey by Network");
        setBackground(Color.yellow);

        SpringLayout myLayout = new SpringLayout();
        SpringLayout springpanelLayout = new SpringLayout();
        setLayout(myLayout);
        
        AnswerTable(myLayout, springpanelLayout);
                
        LocateLabels(myLayout);
        LocateTextAreas(myLayout);
        LocateButtons(myLayout);
        LocatePanelTextAreas(springpanelLayout);
        LocatePanelLabels(springpanelLayout);
        readFile("SurveyByNetwork_SurveyQuestions.txt");
        
        
        QuestionTable(myLayout);
        
        
        this.addWindowListener(this);
    }

//</editor-fold>

    
//<editor-fold defaultstate="collapsed" desc="GUI Construction">

    //---------------------------------------------------------------------------------------------------
    //  Set up GUI Components
    //---------------------------------------------------------------------------------------------------

    public void LocateLabels(SpringLayout myLabelLayout)
    {
        lblWord1  = LocateALabel(myLabelLayout, lblWord1, "Word 1:", 650, 500);
        lblWord2 = LocateALabel(myLabelLayout, lblWord2, "Word 2:", 650, 650);
        lblSent = LocateALabel(myLabelLayout, lblSent, "Sent:", 650, 750);
    }

    public JLabel LocateALabel(SpringLayout myLabelLayout, JLabel myLabel, String  LabelCaption, int x, int y)
    {
        myLabel = new JLabel(LabelCaption);
        add(myLabel);        
        myLabelLayout.putConstraint(SpringLayout.WEST, myLabel, x, SpringLayout.WEST, this);
        myLabelLayout.putConstraint(SpringLayout.NORTH, myLabel, y, SpringLayout.NORTH, this);
        return myLabel;
    }
   
    public void LocateTextAreas(SpringLayout myTextAreaLayout)
    {
        txtWord1  = LocateATextArea(myTextAreaLayout, txtWord1, 100, 200, 1, 10);
        txtWord2 = LocateATextArea(myTextAreaLayout, txtWord2, 100, 200, 1, 10);
    }

    public void LocateButtons(SpringLayout myButtonLayout)
    {
        btnAdd = LocateAButton(myButtonLayout, btnAdd, "Add", 700, 500, 80, 25);   
        
    }
    
    public void LocatePanelTextAreas(SpringLayout myTextAreaLayout)
    {
        txtTopic = LocateATextArea(myTextAreaLayout, txtTopic, 45, 5, 1, 15 );
        rightPanel.add(txtTopic);
        
        txtQn = LocateATextArea(myTextAreaLayout, txtQn, 45, 30, 3, 15 );
        rightPanel.add(txtQn);
        
        txtA = LocateATextArea(myTextAreaLayout, txtA, 45, 86, 1, 15 );
        rightPanel.add(txtA);
        
        txtB = LocateATextArea(myTextAreaLayout, txtB, 45, 111, 1, 15 );
        rightPanel.add(txtB);
        
        txtC = LocateATextArea(myTextAreaLayout, txtC, 45, 136, 1, 15 );
        rightPanel.add(txtC);
        
        txtD = LocateATextArea(myTextAreaLayout, txtD, 45, 161, 1, 15 );
        rightPanel.add(txtD);
        
    }
    
    public void LocatePanelLabels(SpringLayout myLabelLayout)
    {
        lblTopic = LocateALabel(myLabelLayout, lblTopic, "Topic:", 5,3);
        rightPanel.add(lblTopic);
        
        lblQn = LocateALabel(myLabelLayout, lblQn, "Qn:", 10,28);
        rightPanel.add(lblQn);
        
        lblA = LocateALabel(myLabelLayout, lblA, "A:", 15,87);
        rightPanel.add(lblA);
        
        lblB = LocateALabel(myLabelLayout, lblB, "B:", 15,112);
        rightPanel.add(lblB);
        
        lblC = LocateALabel(myLabelLayout, lblC, "C:", 15,137);
        rightPanel.add(lblC);
        
        lblD = LocateALabel(myLabelLayout, lblD, "D:", 15,162);
        rightPanel.add(lblD);
    }


    public JTextArea LocateATextArea(SpringLayout myLayout, JTextArea myTextArea, int x, int y, int w, int h)
    {    
        myTextArea = new JTextArea(w,h);
        add(myTextArea);
        myLayout.putConstraint(SpringLayout.WEST, myTextArea, x, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, myTextArea, y, SpringLayout.NORTH, this);
        myTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        
        
        
        return myTextArea;
        
    }

    public JButton LocateAButton(SpringLayout myButtonLayout, JButton myButton, String  ButtonCaption, int x, int y, int w, int h)
    {    
        myButton = new JButton(ButtonCaption);
        add(myButton);
        myButton.addActionListener(this);
        myButtonLayout.putConstraint(SpringLayout.WEST, myButton, x, SpringLayout.WEST, this);
        myButtonLayout.putConstraint(SpringLayout.NORTH, myButton, y, SpringLayout.NORTH, this);
        myButton.setPreferredSize(new Dimension(w,h));
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
            Object[] tempArray = new Object[8];
            String line;
            
            while ((line = br.readLine()) != null)
            {
                tempArray[counter] = line;
                
                
                if(counter ==7)
                {
                    counter = 0;
                    
                    
                    if (objectarraycount > 0)
                    {
                    dataValues.add(tempArray);
                    }
                    
                    tempArray = new Object[8];
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

    public void QuestionTable(SpringLayout myPanelLayout)
    { 
        // Create a panel to hold all other components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);
        
        // Create column names
        String columnNames[] =
        { "Question Number", "Topic", "Question"};
     

        // constructor of JTable model
	wordModel = new MyModel(dataValues, columnNames);
        
        // Create a new table instance
        JTable table = new JTable(wordModel);

        // Configure some of JTable's paramters
        table.isForegroundSet();
        table.setShowHorizontalLines(true);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        //table.putConstraint(SpringLayout.WEST, table, 10, SpringLayout.WEST, this);
        //table.putConstraint(SpringLayout.NORTH, table, 10, SpringLayout.NORTH, this);
        add(table);

        // Change the text and background colours
        table.setSelectionForeground(Color.white);
        table.setSelectionBackground(Color.red);

        // Add the table to a scrolling pane, size and locate
        JScrollPane scrollPane = table.createScrollPaneForTable(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(550, 175));
        myPanelLayout.putConstraint(SpringLayout.WEST, topPanel, 10, SpringLayout.WEST, this);
        myPanelLayout.putConstraint(SpringLayout.NORTH, topPanel, 10, SpringLayout.NORTH, this);
    }

    public void AnswerTable(SpringLayout myPanelLayout, SpringLayout springpanelLayout)
    {
        // Create a panel to hold all other components
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        rightPanel.setLayout(springpanelLayout);
        add(rightPanel);

        
            
        //Size and locate panel
        rightPanel.setPreferredSize(new Dimension(230, 190));
        myPanelLayout.putConstraint(SpringLayout.WEST, rightPanel, 600, SpringLayout.WEST, this);
        myPanelLayout.putConstraint(SpringLayout.NORTH, rightPanel, 10, SpringLayout.NORTH, this);
        
    
    }

    class MyModel extends AbstractTableModel
    {
        ArrayList<Object[]> al;

        // the headers
        String[] header;
        
        // to hold the column index for the Sent column
        int col;

        // constructor 
        MyModel(ArrayList<Object[]> obj, String[] header)
        {
            // save the header
            this.header = header;
            // and the data
            al = obj;
            // get the column index for the Sent column
            col = this.findColumn("Sent");
        }

        private MyModel(ArrayList<Object[]> dataValues) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        // method that needs to be overload. The row count is the size of the ArrayList

        public int getRowCount()
        {
            return al.size();
        }

        // method that needs to be overload. The column count is the size of our header
        public int getColumnCount()
        {
            return header.length;
        }

        // method that needs to be overload. The object is in the arrayList at rowIndex
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return al.get(rowIndex)[columnIndex];
        }

        // a method to return the column name 
        public String getColumnName(int index)
        {
            return header[index];
        }
        
        public Class getColumnClass(int columnIndex) 
        {
            if (columnIndex == col) 
            {
                return Boolean.class; // For every cell in column 7, set its class to Boolean.class
            }
            return super.getColumnClass(columnIndex); // Otherwise, set it to the default class
	}

        // a method to add a new line to the table
        void add(String word1, String word2, boolean sent)
        {
            // make it an array[3] as this is the way it is stored in the ArrayList
            // (not best design but we want simplicity)
            Object[] item = new Object[3];
            item[0] = word1;
            item[1] = word2;
            item[2] = sent;
            al.add(item);
            // inform the GUI that I have change
            fireTableDataChanged();
        }

        private void add(String text, String text0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

//<editor-fold defaultstate="collapsed" desc="Listeners">
    
    //---------------------------------------------------------------------------------------------------
    // Action and Window Listener
    //---------------------------------------------------------------------------------------------------

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == btnAdd)
        {
            wordModel.add(txtWord1.getText(),txtWord2.getText());
            table.repaint();
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
