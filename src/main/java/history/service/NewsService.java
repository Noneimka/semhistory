package history.service;

import history.model.News;
import history.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Iterable<News> getAll() {
        return newsRepository.findAll();
    }

    public void save(News news) {
        newsRepository.save(news);
    }

    public void delete(News news) {
        newsRepository.delete(news);
    }

    public void deleteById(Integer id) {
        newsRepository.deleteById(id);
    }
}
