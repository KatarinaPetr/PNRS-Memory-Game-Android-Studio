package katarina.petrovic.memorygame;

import android.widget.Button;

public class Model {
    private String username;
    private int bestResult;
    private String email;
    private int worstResult;
    private Button removeMe;
    private Boolean btn_en_check;
    private Integer points;
    public Model(String username, int bestResult,String email,int worstResult) {
        this.username=username;
        this.bestResult=bestResult;
        this.email=email;
        this.worstResult=worstResult;
        this.btn_en_check = false;
    }
    public Model(String username, String email, Integer points) {
        this.username=username;
        this.email=email;
        this.points=points;
    }
    public Boolean getButtonEnable() {
        return btn_en_check;
    }
    public void setButtonEnable() {
        this.btn_en_check = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBestResult() {
        return bestResult;
    }

    public void setBestResult(int bestResult) {
        this.bestResult = bestResult;
    }

    public int getWorstResult() {
        return worstResult;
    }

    public void setWorstResult(int worstResult) {
        this.worstResult = worstResult;
    }

    public Button getRemoveMe() {
        return removeMe;
    }

    public void setRemoveMe(Button removeMe) {
        this.removeMe = removeMe;
    }
}
