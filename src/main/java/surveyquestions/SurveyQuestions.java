package surveyquestions;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SurveyQuestions extends JFrame implements WindowListener, ActionListener, FocusListener
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
    Dimension frameSize = new Dimension(395, 405);
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
        connect(serverName, serverPort);
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
        lblA = locateALabel(parentPanel, parentLayout, lblA, "1:", 15,115);
        lblB = locateALabel(parentPanel, parentLayout, lblB, "2:", 15,140);
        lblC = locateALabel(parentPanel, parentLayout, lblC, "3:", 15,165);
        lblD = locateALabel(parentPanel, parentLayout, lblD, "4:", 15,190);
        lblE = locateALabel(parentPanel, parentLayout, lblE, "5:", 15,215);
    }

    public JTextArea locateATextArea(JPanel parentPanel, SpringLayout parentLayout, JTextArea myTextArea, int x, int y, int w, int h)
    {    
        myTextArea = new JTextArea(w,h);
        myTextArea.addFocusListener(this);
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
        submitPanel.setPreferredSize(new Dimension(465, 50));
        SpringLayout sendPanelLayout = new SpringLayout();
        submitPanel.setLayout(sendPanelLayout);

        btnSubmit = new JButton("Submit");
        btnSubmit.setMnemonic(KeyEvent.VK_S);
        btnSubmit.addActionListener(this);
        sendPanelLayout.putConstraint(SpringLayout.WEST, btnSubmit, 5, SpringLayout.WEST, submitPanel);
        sendPanelLayout.putConstraint(SpringLayout.SOUTH, btnSubmit, 0, SpringLayout.SOUTH, submitPanel);
        btnSubmit.setPreferredSize(new Dimension(100,25));
        submitPanel.add(btnSubmit);

        btnExit = new JButton("Exit");
        btnExit.setMnemonic(KeyEvent.VK_E);
        btnExit.addActionListener(this);
        sendPanelLayout.putConstraint(SpringLayout.EAST, btnExit, -95, SpringLayout.EAST, submitPanel);
        sendPanelLayout.putConstraint(SpringLayout.SOUTH, btnExit, 0, SpringLayout.SOUTH, submitPanel);
        btnExit.setPreferredSize(new Dimension(100,25));
        submitPanel.add(btnExit);

        txtAnswer = new JTextArea(1, 3);
        submitPanel.add(txtAnswer);
        sendPanelLayout.putConstraint(SpringLayout.EAST, txtAnswer, -325, SpringLayout.EAST, submitPanel);
        sendPanelLayout.putConstraint(SpringLayout.SOUTH, txtAnswer, -30, SpringLayout.SOUTH, submitPanel);
        txtAnswer.setBorder(BorderFactory.createLineBorder(Color.black));

        lblAnswer = new JLabel("Your Answer:");
        lblAnswer.setForeground(localBlue);
        submitPanel.add(lblAnswer);
        sendPanelLayout.putConstraint(SpringLayout.WEST, lblAnswer, 5, SpringLayout.WEST, submitPanel);
        sendPanelLayout.putConstraint(SpringLayout.SOUTH, lblAnswer, -30, SpringLayout.SOUTH, submitPanel);
    }

    public void renderAnswerPanel(SpringLayout parentLayout)
    {
        answerPanel = new JPanel();
        answerPanel.setBackground(localEcruWhite);

        TitledBorder titledBorder = new TitledBorder(""); //
        titledBorder = BorderFactory.createTitledBorder(titledBorder, "Enter your answer and click Submit", TitledBorder.LEFT, TitledBorder.ABOVE_TOP);
        titledBorder.setTitleColor(localBlue);
        answerPanel.setBorder(titledBorder);

        SpringLayout answerTableLayout = new SpringLayout();
        answerPanel.setLayout(answerTableLayout);
        basePanel.add(answerPanel);

        locateAnswerPanelTextAreas(answerTableLayout);
        locateAnswerPanelLabels(answerPanel, answerTableLayout);

        //Size and locate panel
        answerPanel.setPreferredSize(new Dimension(375, 275));
        parentLayout.putConstraint(SpringLayout.WEST, answerPanel, 10, SpringLayout.WEST, this);
        parentLayout.putConstraint(SpringLayout.NORTH, answerPanel, 15, SpringLayout.NORTH, this);
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
        if(e.getSource() == btnExit)
        {
            txtAnswer.setText(".bye");
            send();
            this.hide();
        } else if (e.getSource() == btnSubmit) {
            send();
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
        String topic = txtTopic.getText();
        if(topic.length() > 0) {
            String answer = txtAnswer.getText();
            String[] validAnswers = new String[] { "1", "2", "3", "4", "5"};
            if(answer.length() > 0 && Arrays.asList(validAnswers).contains(answer)) {
                try
                {
                    streamOut.writeUTF(answer);
                    streamOut.flush();
                    txtAnswer.setText("");
                }
                catch (IOException ioe)
                {
                    println("Sending error: " + ioe.getMessage());
                    close();
                }
            } else {
                JOptionPane.showMessageDialog(null,"Please supply an answer number");
            }
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
        if(msg.contains("~~~~~")) {
            String[] arr = msg.split("~~~~~");

            txtTopic.setText(arr[1]);
            txtQn.setText(arr[2]);
            txtA.setText(arr[3]);
            txtB.setText(arr[4]);
            txtC.setText(arr[5]);
            txtD.setText(arr[6]);
            txtE.setText(arr[7]);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == txtA) {
            txtAnswer.setText("1");
        } else if (e.getSource() == txtB) {
            txtAnswer.setText("2");
        } else if (e.getSource() == txtC) {
            txtAnswer.setText("3");
        } else if (e.getSource() == txtD) {
            txtAnswer.setText("4");
        } else if(e.getSource() == txtE) {
            txtAnswer.setText("5");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {

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
