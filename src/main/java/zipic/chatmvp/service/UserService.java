package zipic.chatmvp.service;

import org.springframework.stereotype.Service;
import zipic.chatmvp.model.User;

@Service
public interface UserService {
    User save(User user);
}
