package springbootfinal.indieWearhaul.repository;

import org.springframework.data.repository.CrudRepository;

import springbootfinal.indieWearhaul.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	public Customer findByUsername( String username );
}
