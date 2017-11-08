package cat.aubricoc.weddingquiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.Date;
import java.util.List;

public class FinishActivity extends AppCompatActivity {

    private Competitor competitor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        competitor = (Competitor) getIntent().getSerializableExtra(Constants.COMPETITOR_EXTRA);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                sendData();
            }
        }.execute();
    }

    private void sendData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference competitorRef = reference.child("competitors").push();
        competitorRef.child("name").setValue(competitor.getName());
        competitorRef.child("language").setValue(competitor.getLanguage());
        competitorRef.child("date").setValue(ServerValue.TIMESTAMP);
        DatabaseReference answersRef = competitorRef.child("answers");
        for (Question question : competitor.getQuestions()) {
            answersRef.child(question.getId().toString()).setValue(question.getAnswer());
        }
        finishQuiz();
    }

    private void finishQuiz() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
