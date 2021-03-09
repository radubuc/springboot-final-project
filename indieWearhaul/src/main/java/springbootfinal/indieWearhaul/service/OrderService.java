package springbootfinal.indieWearhaul.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springbootfinal.indieWearhaul.entity.Customer;
import springbootfinal.indieWearhaul.entity.Orders;
import springbootfinal.indieWearhaul.entity.Product;
import springbootfinal.indieWearhaul.repository.CustomerRepository;
import springbootfinal.indieWearhaul.repository.OrderRepository;
import springbootfinal.indieWearhaul.repository.ProductRepository;
import springbootfinal.indieWearhaul.service.OrderService;
import springbootfinal.indieWearhaul.util.MembershipLevel;
import springbootfinal.indieWearhaul.util.OrderStatus;

@Service
public class OrderService {

	private static final Logger logger = LogManager.getLogger(OrderService.class);
	private final int DELIVERY_DAYS = 7;
	
	@Autowired
	private OrderRepository repo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	public Orders submitNewOrder(Set<Long> productIds, Long customerId) throws Exception {
		try {
			Customer customer = customerRepo.findOne(customerId);
			Orders order = initializeNewOrder(productIds, customer);
			return repo.save(order);
		} catch (Exception e) {
			logger.error("Exception occurred while trying to create new order for customer: " + customerId, e);
			throw e;
		}
	}

	public Orders cancelOrder(Long orderId) throws Exception {
		try {
			Orders order = repo.findOne(orderId);
			order.setStatus(OrderStatus.CANCELED);
			return repo.save(order);
		} catch (Exception e) {
			logger.error("Exception occurred while trying to cancel order: " + orderId, e);
			throw new Exception("Unable to update order.");
		}
	}
	
	public Orders completeOrder(Long orderId) throws Exception {
		try {
			Orders order = repo.findOne(orderId);
			order.setStatus(OrderStatus.DELIVERED);
			order.setDelivered(LocalDate.now());
			return repo.save(order);
		} catch (Exception e){
			logger.error("Exception occurred while trying to complete order: " + orderId, e);
			throw new Exception("Unable to complete order.");
		}
	}
	
	private Orders initializeNewOrder(Set<Long> productIds, Customer customer) {
		Orders order = new Orders();
		order.setProducts(convertToProductSet(productRepo.findAll(productIds)));
		order.setOrdered(LocalDate.now());
		order.setEstimatedDelivery(LocalDate.now().plusDays(DELIVERY_DAYS));
		order.setCustomer(customer);
		order.setTotal(calculateOrderTotal(order.getProducts(), customer.getLevel()));
		order.setStatus(OrderStatus.ORDERED);
		addOrderToProducts(order);
		return order;
	}
	
	private void addOrderToProducts(Orders order) {
		Set<Product> products = order.getProducts();
		for (Product product : products) {
			product.getOrder().add(order);
		}
	}
	
	private Set<Product> convertToProductSet(Iterable<Product> iterable) {
		Set<Product> set = new HashSet<Product>();
		for (Product product : iterable) {
			set.add(product);
		}
		return set;
	}
	
	public double calculateOrderTotal(Set<Product> products, MembershipLevel level) {
		double total = 0;
		for (Product product : products) {
			total += product.getPrice();
		}
		return total - total * level.getDiscount();
	}
}
