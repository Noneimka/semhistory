package history.service;

import history.model.Role;
import history.model.User;
import history.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender javaMailSender;

    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.roleService = roleService;
    }

    public void signUp(User user, String url) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        String code = RandomString.make(64);
        user.setVerificationCode(code);
        user.setPassword(encodedPassword);
        Set<Role> userRole = new HashSet<>();
        userRole.add(roleService.getById(2));
        user.setRoles(userRole);
        userRepository.save(user);
        sendVerificationMail(user, url);
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        Iterable<User> iterable = userRepository.findAll();
        iterable.forEach(users::add);
        return users;
    }

    public User getById(Integer id) {
        return userRepository.findById(id).get();
    }

    public User getByName(String name) {
        return userRepository.findByName(name).get();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public void changeEnabled(Integer id, boolean value) {
        User user = this.getById(id);
        user.setEnabled(value);
        this.saveUser(user);
    }

    public void addRole(Integer id, String name) {
        User user = this.getById(id);
        user.addRole(roleService.getByName(name));
        this.saveUser(user);
    }

    public User changeEmail(Integer id, String email, String url) {
        String code = RandomString.make(64);
        User user = this.getById(id);
        user.setVerificationCode(code);
        user.setEnabled(false);
        user.setEmail(email);
        sendVerificationMail(user, url);
        return userRepository.save(user);
    }
    public User changePassword(Integer id, String password) {
        User user = this.getById(id);
        user.setPassword(passwordEncoder.encode(password));
        return this.saveUser(user);
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user != null) {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private void sendVerificationMail(User user, String url) {
        String toAddress = user.getEmail();
        String from = "historyprojectsmtp@gmail.com";
        String senderName = "Admin";
        String subject = "Please verify your account";
        String content = "Dear {name},"
                + "Please click the link below for verify your account:"
                + "<a href=\"{url}\">VERIFY</a>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(from, senderName);

            helper.setTo(toAddress);
            helper.setSubject(subject);

            content = content.replace("{name}", user.getName());
            String verifyUrl = url + "/verification?code=" + user.getVerificationCode();
            content = content.replace("{url}", verifyUrl);

            helper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
