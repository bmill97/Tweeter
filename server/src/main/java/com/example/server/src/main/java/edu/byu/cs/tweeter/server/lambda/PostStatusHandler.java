package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.MainServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import java.util.HashMap;
import java.util.Map;

public class PostStatusHandler implements RequestHandler<PostStatusRequest, Response> {
    @Override
    public Response handleRequest(PostStatusRequest input, Context context) {
        String queueUrl = "https://sqs.us-west-2.amazonaws.com/797774218094/PostsQ";
        Map<String, MessageAttributeValue> map = new HashMap<>();

        MessageAttributeValue alias = new MessageAttributeValue();
        alias.setStringValue(input.getAlias());
        alias.setDataType("String");

        MessageAttributeValue tweet = new MessageAttributeValue();
        tweet.setStringValue(input.getTweet());
        tweet.setDataType("String");

        MessageAttributeValue timeStampString = new MessageAttributeValue();
        timeStampString.setStringValue(input.getTimeStampString());
        timeStampString.setDataType("String");

        map.put("alias", alias);
        map.put("tweet", tweet);
        map.put("timeStampString", timeStampString);

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageAttributes(map)
                .withMessageBody("Test MessageBody - Post Status Handler")
                .withDelaySeconds(5);

        System.out.println("ALIAS");
        System.out.println(map.get("alias"));
        System.out.println(input.getAlias());
        System.out.println(alias.toString());

        System.out.println("TWEET");
        System.out.println(map.get("tweet"));
        System.out.println(input.getTweet());
        System.out.println(tweet.toString());

        System.out.println("TIMESTAMP");
        System.out.println(map.get("timeStampString"));
        System.out.println(input.getTimeStampString());
        System.out.println(timeStampString.toString());

        System.out.println(send_msg_request.toString());

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
        String msgId = send_msg_result.getMessageId();
        System.out.println("Message ID: " + msgId);

        return new Response(true, "PostStatusHandler has sent the message to FollowFetcher");
       /* MainServiceImpl service = new MainServiceImpl();
        return service.postStatus(input);*/
    }
}
