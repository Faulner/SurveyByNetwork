package surveybynetwork;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class QuestionModel extends AbstractTableModel
{
    ArrayList<SurveyRecord> al;

    // the headers
    String[] header;

    // to hold the column index for the Sent column
    int col;

    // constructor
    QuestionModel(ArrayList<SurveyRecord> obj, String[] header)
    {
        // save the header
        this.header = header;
        // and the data
        al = obj;
        // get the column index for the Sent column
        col = this.findColumn("Sent");
    }

    private QuestionModel(ArrayList<Object[]> dataValues) {
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
    public String getValueAt(int rowIndex, int columnIndex)
    {
        SurveyRecord surveyRecord = al.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return surveyRecord.getNumber();
            case 1:
                return surveyRecord.getTopic();
            case 2:
                return surveyRecord.getQuestion();
            case 3:
                return surveyRecord.getAnswerA();
            case 4:
                return surveyRecord.getAnswerB();
            case 5:
                return surveyRecord.getAnswerC();
            case 6:
                return surveyRecord.getAnswerD();
            case 7:
                return surveyRecord.getAnswerE();
            default:
                return null;
        }
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
        /*
        Object[] item = new Object[3];
        item[0] = word1;
        item[1] = word2;
        item[2] = sent;
        al.add(item);
        */
        // inform the GUI that I have change
        fireTableDataChanged();
    }

    public void add(String text, String text0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
