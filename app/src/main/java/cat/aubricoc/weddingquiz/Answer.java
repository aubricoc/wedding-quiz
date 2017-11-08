package cat.aubricoc.weddingquiz;

import java.io.Serializable;

public class Answer implements Serializable {

    private Integer id;

    private String answer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
