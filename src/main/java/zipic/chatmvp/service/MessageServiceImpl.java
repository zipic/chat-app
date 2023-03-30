package zipic.chatmvp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zipic.chatmvp.model.Message;
import zipic.chatmvp.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository repository;


    @Override
    public Message save(Message message) {
        return repository.save(message);
    }
}
