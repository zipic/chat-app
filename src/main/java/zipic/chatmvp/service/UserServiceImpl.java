package zipic.chatmvp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zipic.chatmvp.model.User;
import zipic.chatmvp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
   private UserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }
}
