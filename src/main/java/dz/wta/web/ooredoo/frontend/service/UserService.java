package dz.wta.web.ooredoo.frontend.service;

import dz.wta.web.ooredoo.frontend.entity.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);

}
