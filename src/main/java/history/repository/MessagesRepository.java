package history.repository;

import history.model.Messages;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MessagesRepository extends CrudRepository<Messages, Integer> {

    @Override
    <S extends Messages> S save(S entity);

    @Override
    Optional<Messages> findById(Integer integer);

    @Override
    boolean existsById(Integer integer);

    @Override
    Iterable<Messages> findAll();

    @Override
    Iterable<Messages> findAllById(Iterable<Integer> integers);

    @Override
    void deleteById(Integer integer);

    @Override
    void delete(Messages entity);

    @Query(nativeQuery = true, value = "SELECT * FROM \"messages\" ORDER BY date ASC LIMIT 100")
    List<Messages> findAllLimit();
}
