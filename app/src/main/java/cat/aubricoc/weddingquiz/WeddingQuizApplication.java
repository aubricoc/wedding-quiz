package cat.aubricoc.weddingquiz;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class WeddingQuizApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);
    }
}
