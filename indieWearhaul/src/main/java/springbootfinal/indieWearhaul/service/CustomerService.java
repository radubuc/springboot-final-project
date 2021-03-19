package springbootfinal.indieWearhaul.service;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import springbootfinal.indieWearhaul.entity.Credentials;
import springbootfinal.indieWearhaul.entity.Customer;
import springbootfinal.indieWearhaul.repository.CustomerRepository;
import springbootfinal.indieWearhaul.service.CustomerService;

@Service
public class CustomerService {

private static final Logger logger = LogManager.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerRepository repo;
	
	public Customer getCustomerById(Long id) throws Exception {
		try {
			return repo.findOne(id);
		} catch (Exception e) {
			logger.error("Exception occurred while trying to retrieve customer: " + id, e);
			throw e;
		}
	}
	
	public Iterable<Customer> getCustomers() {
		return repo.findAll();
	}
	
	public Customer createCustomer(Customer customer) {
		return repo.save(customer);
	}
	
	public Customer updateCustomer(Customer customer, Long id) throws Exception {
		try {
			Customer oldCustomer = repo.findOne(id);
			if (!(customer.getEmail() == null))
				oldCustomer.setEmail(customer.getEmail());
			
			if (!(customer.getAddress() == null))
				oldCustomer.setAddress(customer.getAddress());
			
			if (!(customer.getFirstName() == null))
				oldCustomer.setFirstName(customer.getFirstName());
			
			if (!(customer.getLastName() == null))
				oldCustomer.setLastName(customer.getLastName());
			
			if (!(customer.getPhone() == null))
				oldCustomer.setPhone(customer.getPhone());
			
			if (!(customer.getLevel() == null))
				oldCustomer.setLevel(customer.getLevel());
			return repo.save(oldCustomer);
		} catch (Exception e){
			logger.error("Exception occurred while trying to update customer: " + id, e);
			throw new Exception("Unable to update customer.");
		}
	}
	
	public void deleteCustomer(Long id) throws Exception {
		try {
			repo.delete(id);
		} catch (Exception e){
			logger.error("Exception occurred while trying to delete customer: " + id, e);
			throw new Exception("Unable to delete customer.");
		}
	}
	

		public Customer register(Credentials cred) throws AuthenticationException {
			Customer customer = new Customer();
			customer.setUsername(cred.getUsername());
			customer.setHash(BCrypt.hashpw(cred.getPassword(), BCrypt.gensalt()));
			try {		
			repo.save(customer);
			return customer;
			} catch (DataIntegrityViolationException e) {
				throw new AuthenticationException("Username not available.");
			}
			}
		public Customer login(Credentials cred) throws AuthenticationException {
			Customer foundUser = repo.findByUsername(cred.getUsername());
			if(foundUser !=null && BCrypt.checkpw(cred.getPassword(), foundUser.getHash())) {
				return foundUser;
			}
			throw new AuthenticationException("Incorrect username or password");
		}
	
}
