package history.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Sections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;

    private String name;

    @Column(columnDefinition = "timestamp", nullable = false, name = "beginning")
    private Timestamp dateFrom;

    @Column(columnDefinition = "timestamp", nullable = false, name = "ending")
    private Timestamp dateTo;

    @OneToMany(mappedBy = "section")
    private List<Article> articles;

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

    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Timestamp getDateTo() {
        return dateTo;
    }

    public void setDateTo(Timestamp dateTo) {
        this.dateTo = dateTo;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
