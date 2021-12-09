import surveybynetwork.SurveyByNetwork;
import surveyquestions.SurveyQuestions;

public class Main
{
    public static void main(String[] args)
    {
        SurveyByNetwork questionFrame = new SurveyByNetwork();
        questionFrame.setSize(questionFrame.getFrameSize());
        questionFrame.setLocation(100, 100);
        questionFrame.setResizable(true);
        questionFrame.setVisible(true);

        SurveyQuestions answerFrame = new SurveyQuestions();
        answerFrame.setSize(answerFrame.getFrameSize());
        answerFrame.setLocation(400, 200);
        answerFrame.setResizable(true);
        answerFrame.setVisible(true);
    }


}
