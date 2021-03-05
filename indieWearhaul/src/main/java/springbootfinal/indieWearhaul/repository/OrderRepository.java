package springbootfinal.indieWearhaul.repository;

import org.springframework.data.repository.CrudRepository;

import springbootfinal.indieWearhaul.entity.Orders;

public interface OrderRepository extends CrudRepository<Orders, Long> {

}
