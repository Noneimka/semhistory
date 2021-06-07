package history.controller;

import history.model.User;
import history.service.RoleService;
import history.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String userList(Model model, HttpServletRequest req) {
        model.addAttribute("user", new User());
        model.addAttribute("currentUser", req.getSession().getAttribute("currentUser"));
        model.addAttribute("allUsers", userService.getAll());
        model.addAttribute("allRoles", roleService.getAll());
        return "admin";
    }

    @PostMapping("/admin/change")
    public String actionUser(@RequestParam(name = "userId", defaultValue = "") Integer userId,
                             @RequestParam(name = "action", defaultValue = "") String action,
                             @RequestParam(name = "banValue", defaultValue = "true", required = false) Boolean banValue,
                             @RequestParam(name = "role", defaultValue = "", required = false) String role,
                             Model model) throws ServletException, IOException {
        System.out.println("ID: " + userId);
        System.out.println("Action: " + action);
        System.out.println("Ban: " + banValue);
        System.out.println("Role: " + role);
        switch (action) {
            case "delete":
                userService.deleteById(userId);
                break;
            case "ban":
                userService.changeEnabled(userId, banValue);
                break;
            case "addRole":
                userService.addRole(userId, role);
                break;
        }
        return "redirect:/admin";
    }
}
