package com.example.server.src.test.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FeedDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.StoryDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.StatusServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class StatusServiceImplTest {

    private StatusRequest statusRequest;
    private StatusResponse statusResponse;
    private StoryDAO storyDAO;
    private FeedDAO feedDAO;
    private StatusServiceImpl statusService;
    private StatusRequest invalidRequest;
    private StatusResponse invalidResponse;

    @BeforeEach
    public void setup() {
        statusRequest = new StatusRequest("alias", 10, "timestamp", true);
        statusResponse = new StatusResponse("message");
        invalidRequest = new StatusRequest("", -1, null, false);
        invalidResponse = new StatusResponse("failed message");
        storyDAO = Mockito.mock(StoryDAO.class);
        feedDAO = Mockito.mock(FeedDAO.class);
        statusService = Mockito.spy(StatusServiceImpl.class);
        Mockito.when(statusService.getStoryDAO()).thenReturn(storyDAO);
        Mockito.when(statusService.getFeedDAO()).thenReturn(feedDAO);
        AuthTokenDAO authTokenDAO = Mockito.mock(AuthTokenDAO.class);
        Mockito.when(statusService.getAuthTokenDAO()).thenReturn(authTokenDAO);
        Mockito.when(authTokenDAO.validateSession(statusRequest.getLoggedInUserAlias())).thenReturn(true);
        Mockito.when(authTokenDAO.validateSession(statusRequest.getLoggedInUserAlias())).thenReturn(true);
    }

    @Test
    public void testGetStatuses_validRequest_correctResponse() throws IOException {
        Mockito.when(storyDAO.getStoryPaginated(statusRequest.getUserAlias(), statusRequest.getLimit(), statusRequest.getLastTimeStamp())).thenReturn(statusResponse);
        StatusResponse response = statusService.getStatuses(statusRequest);
        Assertions.assertEquals(response, statusResponse);
    }

    @Test
    public void testGetStatuses_invalidRequest_incorrectResponse() throws IOException {
        Mockito.when(feedDAO.getFeedPaginated(invalidRequest.getUserAlias(), invalidRequest.getLimit(), invalidRequest.getLastTimeStamp())).thenReturn(invalidResponse);
        StatusResponse response = statusService.getStatuses(invalidRequest);
        Assertions.assertEquals(response, invalidResponse);
    }
}
