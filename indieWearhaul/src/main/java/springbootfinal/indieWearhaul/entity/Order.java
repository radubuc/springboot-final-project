package springbootfinal.indieWearhaul.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import springbootfinal.indieWearhaul.util.OrderStatus;

@Entity
public class Order {

	private Long id;
	private double Total;
	private LocalDate ordered;
	private LocalDate estimatedDelivery;
	private LocalDate delivered;
	private OrderStatus status;
	private Set<Product> products;
	
	@JsonIgnore
	private Customer customer;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public double getTotal() {
		return Total;
	}

	public void setTotal(double total) {
		Total = total;
	}
	
	public LocalDate getOrdered() {
		return ordered;
	}
	
	public void setOrdered(LocalDate ordered) {
		this.ordered = ordered;
	}
	
	public LocalDate getEstimatedDelivery() {
		return estimatedDelivery;
	}
	
	public void setEstimatedDelivery(LocalDate estimatedDelivery) {
		this.estimatedDelivery = estimatedDelivery;
	}
	
	public LocalDate getDelivered() {
		return delivered;
	}
	
	public void setDelivered(LocalDate delivered) {
		this.delivered = delivered;
	}
	
	public OrderStatus getStatus() {
		return status;
	}
	
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	@ManyToMany(mappedBy = "order")
	public Set<Product> getProducts() {
		return products;
	}
	
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
}






