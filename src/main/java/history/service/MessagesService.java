package history.service;

import history.model.Messages;
import history.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;

    @Autowired
    public MessagesService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public List<Messages> getAllLimit() {
        return messagesRepository.findAllLimit();
    }

    public void saveMessage(Messages message) {
        messagesRepository.save(message);
    }
}
