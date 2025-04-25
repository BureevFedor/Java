package communication;

import java.io.Serializable;

public class Response implements Serializable{
    private ResponseCode code;
    private String text;

    public Response(ResponseCode code, String text) {
        this.code = code;
        this.text = text;
    }

    public ResponseCode getCode() {
        return code;
    }
    
    public String getText() {
        return text;
    }
}
