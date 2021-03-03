package springbootfinal.indieWearhaul.repository;

import org.springframework.data.repository.CrudRepository;

import springbootfinal.indieWearhaul.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
