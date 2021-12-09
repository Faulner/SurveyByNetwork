package surveyquestions;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;

public class SurveyQuestions extends JFrame implements WindowListener, ActionListener
{
    
//<editor-fold defaultstate="collapsed" desc="Declarations, main and Constructor">

    //CHAT RELATED ---------------------------
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private SurveyQuestionClientThread client = null;
    private String serverName = "localhost";
    private int serverPort = 4444;

    // ui elements
    Color localGreen = Color.decode("#267F00");
    Color localYellow = Color.decode("#FFFEEB");
    Color localGrey = Color.decode("#EFF2F7");
    Color localBlue = Color.decode("#1F497D");
    Color localEcruWhite = Color.decode("#F7F9F1");
    Dimension frameSize = new Dimension(500, 350);
    JButton btnExit, btnSubmit;
    JLabel lblTopic, lblQn, lblA, lblB, lblC, lblD, lblE, lblSort, lblAnswer;
    JTextArea txtTopic, txtQn, txtA, txtB, txtC, txtD, txtE, txtAnswer;
    JPanel basePanel, answerPanel, submitPanel;

    public Dimension getFrameSize()
    {
        return frameSize;
    }

    public SurveyQuestions()
    {
        setTitle("Survey Questions");
        SpringLayout appLayout = new SpringLayout();
        setLayout(appLayout);

        SpringLayout baseLayout = new SpringLayout();

        renderBase(baseLayout);
        renderAnswerPanel(baseLayout);
        renderSubmitPanel(baseLayout);

        this.addWindowListener(this);

        //CHAT RELATED ---------------------------
        getParameters();
        //----------------------------------------
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

    public void locateSortLabel(JPanel panel, SpringLayout myButtonLayout)
    {
        lblSort = locateALabel(panel, myButtonLayout, lblSort, "Sort by:", 175,10);
    }
    
    public void locateAnswerPanelTextAreas(SpringLayout myTextAreaLayout)
    {
        txtTopic = locateATextArea(answerPanel, myTextAreaLayout, txtTopic, 75, 15, 1, 25 );
        txtQn = locateATextArea(answerPanel, myTextAreaLayout, txtQn, 75, 40, 4, 25 );
        txtQn.setLineWrap(true);
        txtQn.setWrapStyleWord(true);
        txtA = locateATextArea(answerPanel, myTextAreaLayout, txtA, 75, 115, 1, 25 );
        txtB = locateATextArea(answerPanel, myTextAreaLayout, txtB, 75, 140, 1, 25 );
        txtC = locateATextArea(answerPanel, myTextAreaLayout, txtC, 75, 165, 1, 25 );
        txtD = locateATextArea(answerPanel, myTextAreaLayout, txtD, 75, 190, 1, 25 );
        txtE = locateATextArea(answerPanel, myTextAreaLayout, txtE, 75, 215, 1, 25 );
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

    

//</editor-fold>

    public void renderSubmitPanel(SpringLayout parentLayout)
    {
        // Create a panel to hold all other components
        submitPanel = new JPanel();
        parentLayout.putConstraint(SpringLayout.NORTH, submitPanel, 10, SpringLayout.SOUTH, answerPanel);
        parentLayout.putConstraint(SpringLayout.WEST, submitPanel, 10, SpringLayout.WEST, this);
        basePanel.add(submitPanel);


        submitPanel.setBackground(localYellow);
        submitPanel.setPreferredSize(new Dimension(465, 45));
        SpringLayout sendPanelLayout = new SpringLayout();
        submitPanel.setLayout(sendPanelLayout);

        btnExit = new JButton("Exit");
        btnExit.setMnemonic(KeyEvent.VK_E);
        btnExit.addActionListener(this);
        sendPanelLayout.putConstraint(SpringLayout.WEST, btnExit, 0, SpringLayout.WEST, submitPanel);
        sendPanelLayout.putConstraint(SpringLayout.NORTH, btnExit, 10, SpringLayout.NORTH, submitPanel);
        btnExit.setPreferredSize(new Dimension(150,25));
        submitPanel.add(btnExit);

        btnSubmit = new JButton("Submit");
        btnSubmit.setMnemonic(KeyEvent.VK_S);
        btnSubmit.addActionListener(this);
        sendPanelLayout.putConstraint(SpringLayout.EAST, btnSubmit, 0, SpringLayout.EAST, submitPanel);
        sendPanelLayout.putConstraint(SpringLayout.NORTH, btnSubmit, 10, SpringLayout.NORTH, submitPanel);
        btnSubmit.setPreferredSize(new Dimension(250,25));
        submitPanel.add(btnSubmit);

        txtAnswer = new JTextArea(1, 25);
        submitPanel.add(txtAnswer);
        sendPanelLayout.putConstraint(SpringLayout.EAST, txtAnswer, -5, SpringLayout.WEST, submitPanel);
        sendPanelLayout.putConstraint(SpringLayout.NORTH, txtAnswer, 14, SpringLayout.NORTH, submitPanel);
        txtAnswer.setBorder(BorderFactory.createLineBorder(Color.black));

        lblAnswer = new JLabel("Your Answer:");
        lblAnswer.setForeground(localBlue);
        submitPanel.add(lblAnswer);
        sendPanelLayout.putConstraint(SpringLayout.EAST, lblAnswer, -5, SpringLayout.WEST, txtAnswer);
        sendPanelLayout.putConstraint(SpringLayout.NORTH, lblAnswer, 14, SpringLayout.NORTH, submitPanel);
    }

    public void renderAnswerPanel(SpringLayout parentLayout)
    {
        answerPanel = new JPanel();
        answerPanel.setBackground(localEcruWhite);
        answerPanel.setBorder(BorderFactory.createLineBorder(localGreen));

        SpringLayout answerTableLayout = new SpringLayout();
        answerPanel.setLayout(answerTableLayout);
        basePanel.add(answerPanel);

        locateAnswerPanelTextAreas(answerTableLayout);
        locateAnswerPanelLabels(answerPanel, answerTableLayout);

        //Size and locate panel
        answerPanel.setPreferredSize(new Dimension(375, 250));
        parentLayout.putConstraint(SpringLayout.WEST, answerPanel, 10, SpringLayout.WEST, this);
        parentLayout.putConstraint(SpringLayout.NORTH, answerPanel, 35, SpringLayout.NORTH, this);
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

    public void displayQuestion(String[] question)
    {
        txtTopic.setText(question[0]);
        txtQn.setText(question[1]);
        txtA.setText(question[2]);
        txtB.setText(question[3]);
        txtC.setText(question[4]);
        txtD.setText(question[5]);
        txtE.setText(question[6]);
    }

    public void getParameters()
    {
        serverName = "localhost";
        serverPort = 4444;
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

        if(e.getSource() == btnExit)
        {
            //
        } else if (e.getSource() == btnSubmit) {
            //
        }
    }

    public void connect(String serverName, int serverPort)
    {
        println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe)
        {
            println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    private void send()
    {
        try
        {
            streamOut.writeUTF(txtAnswer.getText());
            streamOut.flush();
            txtAnswer.setText("");
        }
        catch (IOException ioe)
        {
            println("Sending error: " + ioe.getMessage());
            close();
        }
    }

    public void handle(String msg)
    {
        if (msg.equals(".bye"))
        {
            println("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
            println(msg);
        }
    }

    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            client = new SurveyQuestionClientThread(this, socket);
        }
        catch (IOException ioe)
        {
            println("Error opening output stream: " + ioe);
        }
    }

    public void close()
    {
        try
        {
            if (streamOut != null)
            {
                streamOut.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ioe)
        {
            println("Error closing ...");
        }
        client.close();
        client.stop();
    }

    void println(String msg)
    {
        //display.appendText(msg + "\n");
        txtTopic.setText(msg);
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
