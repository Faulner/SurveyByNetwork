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

        SurveyQuestions answerFrame1 = new SurveyQuestions();
        answerFrame1.setSize(answerFrame1.getFrameSize());
        answerFrame1.setLocation(150, 150);
        answerFrame1.setResizable(true);
        answerFrame1.setVisible(true);

        SurveyQuestions answerFrame2 = new SurveyQuestions();
        answerFrame2.setSize(answerFrame2.getFrameSize());
        answerFrame2.setLocation(250, 250);
        answerFrame2.setResizable(true);
        answerFrame2.setVisible(true);

    }


}
