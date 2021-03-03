package springbootfinal.indieWearhaul.repository;

import org.springframework.data.repository.CrudRepository;

import springbootfinal.indieWearhaul.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
