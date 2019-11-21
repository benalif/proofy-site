package dz.wta.web.ooredoo.frontend.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="seller")
public class Seller {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String sellerName;
	
	@OneToMany(cascade =CascadeType.ALL,mappedBy="seller")
	private List<Product> products;
	
	@OneToMany(cascade =CascadeType.ALL,mappedBy="seller")
	private List<Order> orders;
	
	
	
}
