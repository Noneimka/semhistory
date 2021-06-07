package history.repository;

import history.model.News;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NewsRepository extends CrudRepository<News, Integer> {

    @Override
    <S extends News> S save(S entity);

    @Override
    Optional<News> findById(Integer integer);

    @Override
    Iterable<News> findAll();

    @Override
    void deleteById(Integer integer);

    @Override
    void delete(News entity);
}
