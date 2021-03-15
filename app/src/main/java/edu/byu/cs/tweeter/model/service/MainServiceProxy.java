package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import edu.byu.cs.tweeter.model.net.ServerFacade;

public class MainServiceProxy {

    private static final String URL_PATH = "/poststatus";


    public Response postStatus(PostStatusRequest request) {
        ServerFacade serverFacade = getServerFacade();
        Response response = serverFacade.postStatus(request);
//        Response response = serverFacade.postStatus(request,URL_PATH);
        return response;
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
