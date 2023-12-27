package learn.ldt.importexcelmultithread.log;

import lombok.Getter;

@Getter
public enum TypeLog {

    INFO("info"),
    ERROR("error"),
    REQUEST("request"),
    RESPONSE("response");

    private final String type;

    TypeLog(String type) {
        this.type = type;
    }

}
