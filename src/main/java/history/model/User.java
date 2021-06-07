package history.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;

    @NotBlank(message = "Name shouldn't be blank!")
    @Size(max = 64, message = "Max name size: 64")
    @Column(length = 64, nullable = false)
    private String name;

//    @NotBlank(message = "Lastname shouldn't be  blank")
//    @Size(max = 64, message = "Max lastname size: 64")
//    @Column(length = 64)
//    private String lastname;

    @NotBlank(message = "Email shouldn't be blank!")
    @Size(min = 5, max = 100, message = "")
    @Column(nullable = false, length = 100)
    private String email;

    @Size(min = 8, max = 64, message = "Password should contains from 8 to 64 symbols")
    @Column(nullable = false, length = 64)
    private String password;

    private boolean enabled;

    @Column(length = 64)
    private String verificationCode;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;


    @OneToMany(mappedBy = "author")
    private List<News> news;

    @OneToMany(mappedBy = "author")
    private List<Article> articles;

    @OneToMany(mappedBy = "sender")
    private List<Messages> sent;

    @Column(nullable = false)
    private String photo = "users.jpg";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Messages> getSent() {
        return sent;
    }

    public void setSent(List<Messages> sent) {
        this.sent = sent;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
