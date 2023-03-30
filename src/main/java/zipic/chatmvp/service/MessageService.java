package zipic.chatmvp.service;

import org.springframework.stereotype.Service;
import zipic.chatmvp.model.Message;

@Service
public interface MessageService {
    Message save(Message message);
}
