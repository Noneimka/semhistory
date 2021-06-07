package history.controller;

import history.model.User;
import history.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AjaxController {

    private final UserService userService;

    @Autowired
    public AjaxController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/changedata/account")
    @ResponseBody
    public void changeData(Model model, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean ajax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
        if (ajax) {
            User user = userService.getById(Integer.parseInt(req.getParameter("id")));
            String nickname = req.getParameter("nickname");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            if (nickname != null) {
                if (!nickname.trim().equals("") && !user.getName().equals(nickname)) {
                    user.setName(nickname);
                    user = userService.saveUser(user);
                    model.addAttribute("currentUser", user);
                    req.setAttribute("currentUser", user);
                    resp.getWriter().print(true);
                } else {
                    resp.getWriter().print("0");
                }
            } else if (email != null) {
                if (!email.trim().equals("") && !user.getEmail().equals(email.trim().trim())) {
                    String url = req.getRequestURL().toString().replace(req.getServletPath(), "");
                    userService.changeEmail(user.getId(), email.trim(), url);
                    model.addAttribute("currentUser", userService.getById(user.getId()));
                    req.setAttribute("currentUser", userService.getById(user.getId()));
                    resp.getWriter().print(true);
                } else {
                    resp.getWriter().print("0");
                }
            } else {
                if (password != null) {
                    if (!password.trim().equals("") && !user.getPassword().equals(userService.getPasswordEncoder().encode(password))) {
                        userService.changePassword(user.getId(), password.trim());
                        model.addAttribute("currentUser", userService.getById(user.getId()));
                        req.setAttribute("currentUser", userService.getById(user.getId()));
                        resp.getWriter().print(true);
                    } else {
                        resp.getWriter().print("0");
                    }
                }
            }
        }
    }
}
