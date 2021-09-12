package app.web.pavelk.message6.back2;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * web stomp client
 */
public class Back2_2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        System.out.println(webSocketContainer.getDefaultMaxTextMessageBufferSize());
        webSocketContainer.setDefaultMaxTextMessageBufferSize(2 * 1024 * 1024);
        System.out.println(webSocketContainer.getDefaultAsyncSendTimeout());
        webSocketContainer.setAsyncSendTimeout(-1);
        System.out.println(webSocketContainer.getDefaultMaxSessionIdleTimeout());
        webSocketContainer.setDefaultMaxSessionIdleTimeout(-1);
        WebSocketClient webSocketClient = new StandardWebSocketClient(webSocketContainer);

        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);

        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setAwaitTerminationSeconds(59);
        threadPoolTaskScheduler.initialize();
        webSocketStompClient.setTaskScheduler(threadPoolTaskScheduler);

        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new StompSessionHandler() {

            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                System.out.println("getPayloadType " + stompHeaders);
                return Object.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.out.println("handleFrame " + new String((byte[]) o));
            }

            @Override
            public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
                System.out.println("afterConnected " + stompSession);
                System.out.println(stompHeaders);
            }

            @Override
            public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
                System.out.println("handleException" + stompSession);
                System.out.println(stompHeaders);
                System.out.println(stompCommand);
                System.out.println(" " + new String(bytes));
                System.out.println(throwable.getMessage());
            }

            @Override
            public void handleTransportError(StompSession stompSession, Throwable throwable) {
                System.out.println("handleTransportError " + stompSession);
                System.out.println(throwable.getMessage());
            }
        };


        ListenableFuture<StompSession> listenableFuture = webSocketStompClient.connect("ws://127.0.0.1:15674/ws", sessionHandler);
        StompSession stompSession = listenableFuture.get();

        stompSession.subscribe("/topic/WWW", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                System.out.println("getPayloadType " + stompHeaders);
                return Object.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                String s = new String((byte[]) o);
                System.out.println("handleFrame " + s + " " + stompHeaders);
            }
        });

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.nextLine();
            if (s.equals("exit")) {
                break;
            } else if (s.equals("1")) {
                stompSession.send("/topic/WWW", "back2_2");
            }
        }

    }
}
