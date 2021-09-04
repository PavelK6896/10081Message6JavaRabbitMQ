package app.web.pavelk.message6.back1.websocker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${rabbit.host:localhost}")
    private String host;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/info").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableStompBrokerRelay("/queue/", "/topic/")
                .setUserDestinationBroadcast("/topic/unresolved.user.dest")
                .setUserRegistryBroadcast("/topic/registry.broadcast")
                .setRelayHost(host)
                .setRelayPort(61613);
        config.setApplicationDestinationPrefixes("/app");
    }
}
