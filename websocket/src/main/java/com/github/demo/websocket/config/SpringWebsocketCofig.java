package com.github.demo.websocket.config;

import com.github.demo.websocket.handler.WebsocketCustomHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SpringWebsocketCofig implements WebSocketConfigurer {

	@Autowired
	WebsocketCustomHandler websocketCustomHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(websocketCustomHandler, "/spring-ws").setAllowedOrigins("*");
	}
}
