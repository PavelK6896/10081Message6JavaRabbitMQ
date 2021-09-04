package app.web.pavelk.message6.back1.websocker;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    @Value("${server.port:0}")
    private String port;

    @Scheduled(fixedDelay = 5000, initialDelay = 10000)
    public void scheduledCom1() {
        simpMessagingTemplate.convertAndSend("/topic/messages", "server " + port);
    }

    @MessageMapping("/chat")
    public void send(String s) {
        System.out.println(s);
    }


}
