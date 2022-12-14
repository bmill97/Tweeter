package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.ServerFacade_Old;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class StatusService {

    public StatusResponse getStatuses(StatusRequest request) throws IOException {
        StatusResponse response = getServerFacade().getStatuses(request);
        if(response.isSuccess()) {
            loadImages(response);
        }
        return response;
    }

    private void loadImages(StatusResponse response) throws IOException {
        for(Status status : response.getStatuses()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.getUser().setImageBytes(bytes);
        }
    }

    ServerFacade_Old getServerFacade() {
        return new ServerFacade_Old();
    }
}
