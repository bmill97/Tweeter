package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.ServerFacade_Old;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class LogoutServiceTest {

    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private Response successResponse;
    private Response failureResponse;

    private LogoutServiceProxy followServiceSpy;

    /**
     * Create a LogoutService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        // Setup request objects to use in the tests
        validRequest = new LogoutRequest("Test_User", new AuthToken("test-token"));
        invalidRequest = new LogoutRequest(null, null);
        String url = "/logout";

        // Setup a mock ServerFacade that will return known responses
        successResponse = new Response(true, "logout successful");
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.logout(validRequest, url)).thenReturn(successResponse);

        failureResponse = new Response(false, "An exception occurred");
        Mockito.when(mockServerFacade.logout(invalidRequest, url)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followServiceSpy = Mockito.spy(new LogoutServiceProxy());
        Mockito.when(followServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link LogoutService#logout(LogoutRequest)}
     * method returns the same result as the {@link ServerFacade_Old}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogout_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Response response = followServiceSpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link FollowService#getFollows(FollowRequest)}
     * method returns the same result as the {@link ServerFacade_Old}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogout_invalidRequest_returnsNoUser() throws IOException, TweeterRemoteException {
        Response response = followServiceSpy.logout(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}