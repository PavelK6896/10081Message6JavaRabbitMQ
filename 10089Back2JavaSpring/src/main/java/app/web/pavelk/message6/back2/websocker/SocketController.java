package app.web.pavelk.message6.back2.websocker;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void scheduledCom1() {
//        simpMessagingTemplate.convertAndSend("/topic/WWW", "back2_1");
    }

    @MessageMapping("/action")
    public void handleAction() throws Exception {
        System.out.println("action");
    }

    @MessageMapping("/WWW")
    public void WWW() throws Exception {
        System.out.println("WWW");
    }

    @MessageExceptionHandler
    @SendToUser(destinations = "/queue/errors", broadcast = false)
    public void handleException(String action) {
        System.out.println("/queue/errors");
    }

}
