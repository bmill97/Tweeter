package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response;

import java.util.List;
import java.util.Objects;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;

public class StatusResponse extends PagedResponse {

    private List<Status> statuses;

    public StatusResponse(){}

    public StatusResponse(String message){
        super(false, message, false);
    }

    public StatusResponse(List<Status> statuses, boolean hasMorePages){
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        StatusResponse that = (StatusResponse) param;

        return (Objects.equals(statuses, that.statuses) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses);
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
