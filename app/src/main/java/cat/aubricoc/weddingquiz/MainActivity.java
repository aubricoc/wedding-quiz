package cat.aubricoc.weddingquiz;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Competitor competitor;

    private View startButton;

    private View languageEsButton;

    private View languageCaButton;

    private View languageSelectorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start);
        languageEsButton = findViewById(R.id.language_es);
        languageCaButton = findViewById(R.id.language_ca);
        languageSelectorView = findViewById(R.id.language_selector);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageSelection();
            }
        });
        languageEsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuiz("es");
            }
        });
        languageCaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuiz("ca");
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showStartButton();
            }
        }, 2000);
    }

    private void showStartButton() {
        languageEsButton.setVisibility(View.GONE);
        languageCaButton.setVisibility(View.GONE);
        languageSelectorView.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_back);
        startButton.startAnimation(animation);
        startButton.setVisibility(View.VISIBLE);
    }

    private void showLanguageSelection() {
        competitor = new Competitor();
        startButton.setVisibility(View.GONE);
        languageSelectorView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_back);
        languageEsButton.startAnimation(animation);
        languageCaButton.startAnimation(animation);
        languageEsButton.setVisibility(View.VISIBLE);
        languageCaButton.setVisibility(View.VISIBLE);
    }

    private void goToQuiz(String language) {
        competitor.setLanguage(language);
        setLocale(language);
        Intent intent = new Intent(this, NameActivity.class);
        intent.putExtra(Constants.COMPETITOR_EXTRA, competitor);
        startActivity(intent);
    }

    public void setLocale(String language) {
        Locale locale = new Locale(language);
        Configuration conf = getApplicationContext().getResources().getConfiguration();
        conf.locale = locale;
        getApplicationContext().getResources().updateConfiguration(conf, null);
    }

    @Override
    public void onBackPressed() {
        if (languageSelectorView.getVisibility() == View.VISIBLE) {
            showStartButton();
        } else {
            super.onBackPressed();
        }
    }
}
