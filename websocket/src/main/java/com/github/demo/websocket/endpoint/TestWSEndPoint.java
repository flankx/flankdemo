package com.github.demo.websocket.endpoint;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ServerEndpoint("/ws-demo")
@Component
public class TestWSEndPoint {
	private static ConcurrentHashMap<String, Session> map = new ConcurrentHashMap<>();

	@OnOpen
	public void onOpen(Session session) {
		log.info("接受连接: " + session);
		map.put(session.getId(), session);
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		session.getBasicRemote().sendText("返回响应：" + message);
	}

	@OnClose
	public void onClose(Session session) {
		log.info("关闭连接: " + session);
		map.remove(session.getId());
	}

	@OnError
	public void onError(Session session, Throwable throwable) throws Exception {
		log.error("错误连接: " + session + "throwable : " + throwable.getMessage());
	}

}
