package history.controller;

import history.model.User;
import history.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public User getUser(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/user")
    @ResponseBody
    public Iterable<User> getAllUser() {
        return userService.getAll();
    }

    @PostMapping("/user")
    @ResponseBody
    public User createUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/sign_up")
    public String signUp(@ModelAttribute User user, HttpServletRequest request) {
        userService.signUp(user, request.getRequestURL().toString().replace(request.getServletPath(), ""));
        return "sign_up_success";
    }

    @GetMapping("/verification")
    public void verify(@Param("code") String code, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/home?verify=" + userService.verify(code));
    }
}

