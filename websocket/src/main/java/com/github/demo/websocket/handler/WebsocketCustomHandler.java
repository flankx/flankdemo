package com.github.demo.websocket.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebsocketCustomHandler implements WebSocketHandler {
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("接收到新的连接" + session.getId());
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String text = "接收到消息：" + message.toString() + "session = " + session.getId();
		log.info(text);
		session.sendMessage(new TextMessage(text));
		// session.sendMessage(new
		// BinaryMessage(text.getBytes(StandardCharsets.UTF_8)));
		// session.sendMessage(new PingMessage());
		// session.sendMessage(new PongMessage());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.info("连接错误" + exception + "session =" + session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.info("关闭连接 = " + closeStatus.toString() + "session =" + session.getId());
	}

	@Override
	public boolean supportsPartialMessages() {
		// 支持分片
		return false;
	}
}
