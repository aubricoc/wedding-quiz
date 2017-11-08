package cat.aubricoc.weddingquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizActivity extends AppCompatActivity implements QuestionFragment.QuestionAnsweredListener {

    private Competitor competitor;

    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        competitor = (Competitor) getIntent().getSerializableExtra(Constants.COMPETITOR_EXTRA);

        List<Question> questions = getQuestions(competitor.getLanguage());
        competitor.setQuestions(questions);
        Collections.shuffle(questions);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                Question question = competitor.getQuestions().get(position);
                question.setNumber(position + 1);
                return QuestionFragment.newInstance(question, QuizActivity.this);
            }

            @Override
            public int getCount() {
                return competitor.getQuestions().size();
            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private List<Question> getQuestions(String language) {
        int fileId = R.raw.questions_es;
        if (language.equals("ca")) {
            fileId = R.raw.questions_ca;
        }
        InputStream inputStream = getResources().openRawResource(fileId);
        Reader reader = new InputStreamReader(inputStream);
        Type listType = new TypeToken<ArrayList<Question>>() {
        }.getType();
        return new Gson().fromJson(reader, listType);
    }

    @Override
    public void onQuestionAnwered(Integer questionId, Integer answerId) {
        List<Question> questions = competitor.getQuestions();
        for (Question question : questions) {
            if (question.getId().equals(questionId)) {
                question.setAnswer(answerId);
            }
        }
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < questions.size() - 1) {
            viewPager.setCurrentItem(currentItem + 1, true);
        } else {
            goToFinish();
        }
    }

    private void goToFinish() {
        Intent intent = new Intent(this, FinishActivity.class);
        intent.putExtra(Constants.COMPETITOR_EXTRA, competitor);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(currentItem - 1, true);
        }
    }
}
