package history.repository;

import history.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Override
    Optional<Role> findById(Integer integer);

    Optional<Role> findByName(String name);
}
