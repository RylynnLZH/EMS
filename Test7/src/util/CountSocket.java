package util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.sun.net.httpserver.HttpContext;

// 注解 入口
@ServerEndpoint("/websocket")
public class CountSocket {

	public static Set<CountSocket> set = new HashSet<CountSocket>();
	private Session session;

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		set.add(this); // 加入set中
		
	}

	
	
	public static void sendMessageAll(String message) {
		
		for(CountSocket item:set) {
			item.sendMessage(message);
			
		}
	}
	
	
	public void sendMessage(String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void onClose() {
		set.remove(this); // 从set中删除
		
	}

	 
	@OnMessage
	public void onMessage(String message, Session session) {
//		ServletContext application=new 
//		sendMessageAll();
	}
}
