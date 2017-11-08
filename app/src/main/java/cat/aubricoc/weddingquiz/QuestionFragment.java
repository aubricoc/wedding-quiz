package cat.aubricoc.weddingquiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class QuestionFragment extends Fragment {

    private static final String QUESTION_ARG = "question";

    private Question question;

    private QuestionAnsweredListener questionAnsweredListener;

    public static Fragment newInstance(Question question, QuestionAnsweredListener questionAnsweredListener) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(QUESTION_ARG, question);
        fragment.setArguments(args);
        fragment.questionAnsweredListener = questionAnsweredListener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = (Question) getArguments().getSerializable(QUESTION_ARG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        TextView counterView = (TextView) view.findViewById(R.id.counter);
        String counter = question.getNumber() + " / 15";
        counterView.setText(counter);
        TextView questionView = (TextView) view.findViewById(R.id.question);
        questionView.setText(question.getQuestion());
        List<Answer> answers = question.getAnswers();
        Collections.shuffle(answers);
        unselectAllAnswers(view);
        setAnswer(view, R.id.answer_1, answers.get(0));
        setAnswer(view, R.id.answer_2, answers.get(1));
        setAnswer(view, R.id.answer_3, answers.get(2));
        setAnswer(view, R.id.answer_4, answers.get(3));
        return view;
    }

    private void setAnswer(final View view, int viewId, final Answer answer) {
        TextView textView = (TextView) view.findViewById(viewId);
        textView.setText(answer.getAnswer());
        if (answer.getId().equals(question.getAnswer())) {
            textView.setSelected(true);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAllAnswers(view);
                markAnswer(answer);
                v.setSelected(true);
            }
        });
    }

    private void unselectAllAnswers(View view) {
        view.findViewById(R.id.answer_1).setSelected(false);
        view.findViewById(R.id.answer_2).setSelected(false);
        view.findViewById(R.id.answer_3).setSelected(false);
        view.findViewById(R.id.answer_4).setSelected(false);
    }

    private void markAnswer(Answer answer) {
        Integer answerId = answer.getId();
        question.setAnswer(answerId);
        questionAnsweredListener.onQuestionAnwered(question.getId(), answerId);
    }

    public interface QuestionAnsweredListener {
        void onQuestionAnwered(Integer questionId, Integer answerId);
    }
}
