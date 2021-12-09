package surveybynetwork;

public class SurveyRecord {

    private String Number = new String();
    private String Topic = new String();
    private String Question = new String();
    private String AnswerA = new String();
    private String AnswerB = new String();
    private String AnswerC = new String();
    private String AnswerD = new String();
    private String AnswerE = new String();

    public SurveyRecord()
    {
        Number = "";
        Topic = "";
        Question = "";
        AnswerA = "";
        AnswerB = "";
        AnswerC = "";
        AnswerD = "";
        AnswerE = "";
    }

    public void setSurveyRecordDetails(String[] surveyRecordDetails)
    {
        Number = surveyRecordDetails[0];
        Topic = surveyRecordDetails[1];
        Question = surveyRecordDetails[2];
        AnswerA = surveyRecordDetails[3];
        AnswerB = surveyRecordDetails[4];
        AnswerC = surveyRecordDetails[5];
        AnswerD = surveyRecordDetails[6];
        AnswerE = surveyRecordDetails[7];
    }

    public String[] getSurveyRecordDetails()
    {
        String[] surveyRecordDetails = new String[8];
        surveyRecordDetails[0] = Number;
        surveyRecordDetails[1] = Topic;
        surveyRecordDetails[2] = Question;
        surveyRecordDetails[3] = AnswerA;
        surveyRecordDetails[4] = AnswerB;
        surveyRecordDetails[5] = AnswerC;
        surveyRecordDetails[6] = AnswerD;
        surveyRecordDetails[7] = AnswerE;

        return surveyRecordDetails;
    }

    //<editor-fold defaultstate="collapsed" desc="Setters">

    public void setNumber(String Number)
    {
        this.Number = Number;
    }

    public void setTopic(String Topic)
    {
        this.Topic = Topic;
    }

    public void setQuestion(String Question)
    {
        this.Question = Question;
    }

    public void setAnswerA(String AnswerA)
    {
        this.AnswerA = AnswerA;
    }

    public void setAnswerB(String AnswerB)
    {
        this.AnswerB = AnswerB;
    }

    public void setAnswerC(String AnswerC)
    {
        this.AnswerC = AnswerC;
    }

    public void setAnswerD(String AnswerD)
    {
        this.AnswerD = AnswerD;
    }

    public void setAnswerE(String AnswerE)
    {
        this.AnswerE = AnswerE;
    }

    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Getters">

    public String getNumber()
    {
        return Number;
    }

    public String getTopic()
    {
        return Topic;
    }

    public String getQuestion()
    {
        return Question;
    }

    public String getAnswerA()
    {
        return AnswerA;
    }

    public String getAnswerB()
    {
        return AnswerB;
    }

    public String getAnswerC()
    {
        return AnswerC;
    }

    public String getAnswerD()
    {
        return AnswerD;
    }

    public String getAnswerE()
    {
        return AnswerE;
    }

    //</editor-fold>



}
