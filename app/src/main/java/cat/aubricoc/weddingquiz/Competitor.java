package cat.aubricoc.weddingquiz;

import java.io.Serializable;
import java.util.List;

public class Competitor implements Serializable {

    private String name;

    private List<Question> questions;

    private String language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
