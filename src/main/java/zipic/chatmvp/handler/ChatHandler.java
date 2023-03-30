package zipic.chatmvp.handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import zipic.chatmvp.model.Message;
import zipic.chatmvp.model.User;
import zipic.chatmvp.service.MessageService;
import zipic.chatmvp.service.UserService;

@Component
public class ChatHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private int numUsers = 0;
    private int maxUsers = 2;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (numUsers < maxUsers) {
            sessions.add(session);
            numUsers++;

            User user = new User();
            user.setUserName(session.getId());
            userService.save(user);

            Message message = new Message();
            message.setContent(session.getId());
            messageService.save(message);

        } else {
            session.close(CloseStatus.POLICY_VIOLATION.withReason("Maximum of users"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        numUsers--;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (WebSocketSession s : sessions) {
            s.sendMessage(message);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        sessions.remove(session);
        numUsers--;
    }
}
