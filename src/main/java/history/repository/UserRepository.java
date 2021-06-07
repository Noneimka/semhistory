package history.repository;

import history.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findByVerificationCode(String code);

    Optional<User> findByName(String name);

    @Query(value = "select * from users u where name like ?1", nativeQuery = true)
    List<User> findAllByEmail(String email);

    // used for pagination
    Page<User> findAll(Pageable pageable);

    @Query(value = "select * from users u where u.name = ?1", nativeQuery = true)
    User findUserByName(String name);

    @Query(value = "select u from User u where u.name = :name and u.id = :id")
    User findUserByNameAndId(@Param("name") String name, @Param("id") Integer id);

    @Query(value = "select u from User u where u.name IN :names")
    Collection<User> findAllWithNames(@Param("names") List<String> names);
}
