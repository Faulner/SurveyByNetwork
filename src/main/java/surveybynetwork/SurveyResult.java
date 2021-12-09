package surveybynetwork;

public class SurveyResult {

    private String Topic = new String();
    private String Number = new String();
    private String Average = new String();

    public SurveyResult()
    {
        Topic = "";
        Number = "";
        Average = "";
    }

    public void setSurveyResultDetails(String[] surveyResultDetails)
    {
        Topic = surveyResultDetails[0];
        Number = surveyResultDetails[1];
        Average = surveyResultDetails[2];
    }

    public String[] getSurveyResultDetails()
    {
        String[] surveyResultDetails = new String[3];
        surveyResultDetails[0] = Topic;
        surveyResultDetails[1] = Number;
        surveyResultDetails[2] = Average;

        return surveyResultDetails;
    }

    public String print()
    {
        return Topic + ", Qn " + Number + ", " + Average;
    }

    //<editor-fold defaultstate="collapsed" desc="Setters">

    public void setTopic(String Topic)
    {
        this.Topic = Topic;
    }

    public void setNumber(String Number)
    {
        this.Number = Number;
    }

    public void setAverage(String Average)
    {
        this.Average = Average;
    }

    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Getters">

    public String getTopic()
    {
        return Topic;
    }

    public String getNumber()
    {
        return Number;
    }

    public String getAverage()
    {
        return Average;
    }

    //</editor-fold>

}
