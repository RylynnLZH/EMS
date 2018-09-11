package a;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/CountSocket")
public class CountSocket {
	
	private static Set<CountSocket> set = new HashSet<CountSocket>();
	private Session session;
	
	
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		set.add(this); // 加入set中
		
	}
	
	
	 
	@OnMessage
	public void onMessage(String message, Session session) {
		
		// 群发消息
		for (CountSocket item : set) {
			try {
				item.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
