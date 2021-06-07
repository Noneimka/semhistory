package history.controller;

import history.dto.MessageDto;
import history.dto.UserDto;
import history.model.Messages;
import history.model.User;
import history.service.MessagesService;
import history.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Controller
public class MessageController {

    private final MessagesService messagesService;
    private final UserService userService;

    @Autowired
    public MessageController(MessagesService messagesService, UserService userService) {
        this.messagesService = messagesService;
        this.userService = userService;
    }

    @MessageMapping("/messages")
    @SendTo("/topic/messages")
    public MessageDto message(MessageDto messageDto) {
        messageDto.setDate(new Timestamp(System.currentTimeMillis()));
        Messages messages = new Messages();
        messages.setText(messageDto.getText());
        messages.setDate(new Timestamp(System.currentTimeMillis()));
        messages.setSender(userService.getById(messageDto.getSender()));
        messageDto.setUserDto(new UserDto(messages.getSender()));
        messagesService.saveMessage(messages);
        return messageDto;
    }

    @GetMapping("/chat")
    public String getChatPage(Model model, HttpServletRequest req) {
        model.addAttribute("user", new User());
        model.addAttribute("currentUser", req.getSession().getAttribute("currentUser"));
        model.addAttribute("lastMessages", messagesService.getAllLimit());
        return "message";
    }
}

