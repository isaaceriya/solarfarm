package learn.solar.domain;

import learn.solar.models.Solar;

import java.util.ArrayList;
import java.util.List;

public class SolarResult {

    private ArrayList<String> messages = new ArrayList<>();
    private Solar payload;

    public void addErrorMessage(String message){
        messages.add(message);
    }

    public boolean isSuccess(){
        return messages.size() == 0;
    }

    public Solar getPayload() {
        return payload;
    }

    public void setPayload(Solar payload) {
        this.payload = payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
}
