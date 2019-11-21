package dz.wta.web.ooredoo.frontend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.wta.web.ooredoo.frontend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Product findByName(String name);

}
