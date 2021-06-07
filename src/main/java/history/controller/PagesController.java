package history.controller;

import history.model.User;
import history.service.NewsService;
import history.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PagesController {

    private final UserService userService;
    private final NewsService newsService;

    @Autowired
    public PagesController(UserService userService, NewsService newsService) {
        this.userService = userService;
        this.newsService = newsService;
    }


    @GetMapping("/home")
    public String homePage(HttpServletRequest req, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("currentUser", req.getSession().getAttribute("currentUser"));
        model.addAttribute("news", newsService.getAll());
        return "news";
    }

    @GetMapping("/account")
    public String accountPage(HttpServletRequest req, Model model) {
        model.addAttribute("user", req.getSession().getAttribute("user"));
        model.addAttribute("currentUser", req.getSession().getAttribute("currentUser"));
        User accountUser = userService.getByName(req.getParameter("user"));
        model.addAttribute("accountUser", accountUser);
        return "account";
    }

    @GetMapping("/login")
    public void loginRedirect(HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/home");
    }
}
