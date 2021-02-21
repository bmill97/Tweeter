package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.model.service.response.UserFollowResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.UserPresenter;

public class GetUserTask extends AsyncTask<GetUserRequest, Void, GetUserResponse> {

    private final UserPresenter presenter;
    private final GetUserTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void loadUser(GetUserResponse getUserResponse);
        void handleException(Exception exception);
    }

    public GetUserTask(UserPresenter presenter, GetUserTask.Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetUserResponse doInBackground(GetUserRequest... userRequests) {

        GetUserResponse response = null;

        try {
            response = presenter.getUser(userRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GetUserResponse getUserResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.loadUser(getUserResponse);
        }
    }
}

