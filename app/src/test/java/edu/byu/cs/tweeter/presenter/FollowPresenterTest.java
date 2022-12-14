package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.FollowServiceProxy;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowPresenterTest {

    private FollowRequest request;
    private FollowResponse response;
    private FollowServiceProxy mockFollowServiceProxy;
    private FollowPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Not sure if this should be true or false?
        request = new FollowRequest(currentUser.getAlias(), 3, null, true);
        response = new FollowResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        // Create a mock FollowingService
        mockFollowServiceProxy = Mockito.mock(FollowServiceProxy.class);
        Mockito.when(mockFollowServiceProxy.getFollows(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FollowPresenter(new FollowPresenter.View() {}));
        Mockito.when(presenter.getFollowService()).thenReturn(mockFollowServiceProxy);
    }

    @Test
    public void testGetFollowing_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowServiceProxy.getFollows(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getFollows(request));
    }

    @Test
    public void testGetFollowing_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowServiceProxy.getFollows(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.getFollows(request));
    }
}
