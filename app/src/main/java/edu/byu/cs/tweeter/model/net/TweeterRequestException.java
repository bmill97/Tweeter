package edu.byu.cs.tweeter.model.net;
import java.util.List;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;

public class TweeterRequestException extends TweeterRemoteException {

    public TweeterRequestException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}