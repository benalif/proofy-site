package dz.wta.web.ooredoo.frontend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.wta.web.ooredoo.frontend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
