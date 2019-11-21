package dz.wta.web.ooredoo.frontend.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem implements Serializable {

	@Id
	@ManyToOne
	private Order order;

	@Id
	@ManyToOne
	private Product product;

	public Order getOder() {
		return order;
	}

	public void setOder(Order oder) {
		this.order = oder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
