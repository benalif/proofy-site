package dz.wta.web.ooredoo.frontend.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	

	@Column(name = "id")
	private long id;
	
	@Column(name="name")
	private String name;
	

}
