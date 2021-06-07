package history.service;

import history.model.Role;
import history.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getById(Integer id) {
       return roleRepository.findById(id).get();
    }

    public Iterable<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getByName(String name) {
        return roleRepository.findByName(name).get();
    }
}
