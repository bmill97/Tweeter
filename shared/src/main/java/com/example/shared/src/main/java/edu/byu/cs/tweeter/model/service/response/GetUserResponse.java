package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class GetUserResponse extends Response {

    private User viewedUser;

    public GetUserResponse(){}

    public GetUserResponse(boolean success, User viewedUser) {
        super(success);
        this.viewedUser = viewedUser;
    }

    GetUserResponse(boolean success, String message, User viewedUser) {
        super(success, message);
        this.viewedUser = viewedUser;
    }

    public void setViewedUser(User viewedUser) {
        this.viewedUser = viewedUser;
    }

    public User getViewedUser() {
        return viewedUser;
    }
}
