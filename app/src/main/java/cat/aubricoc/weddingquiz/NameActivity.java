package cat.aubricoc.weddingquiz;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class NameActivity extends AppCompatActivity {

    private Competitor competitor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        competitor = (Competitor) getIntent().getSerializableExtra(Constants.COMPETITOR_EXTRA);

        findViewById(R.id.name_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuiz();
            }
        });
    }

    private void goToQuiz() {
        EditText editor = (EditText) findViewById(R.id.name_input);
        String name = editor.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            return;
        }
        competitor.setName(name);
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra(Constants.COMPETITOR_EXTRA, competitor);
        startActivity(intent);
    }
}
