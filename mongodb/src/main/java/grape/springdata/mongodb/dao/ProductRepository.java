package grape.springdata.mongodb.dao;

import java.util.List;

import grape.springdata.mongodb.core.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author grape
 * @date 2019-05-22
 */
public interface ProductRepository extends CrudRepository<Product,String>, QuerydslPredicateExecutor<Product> {

	/**
	 * 分页查询产品详情描述中包含指定内容的产品列表
	 * @param description
	 * @param pageable
	 * @return
	 */
	Page<Product> findByDescriptionContaining(String description, Pageable pageable);


	/**
	 * 查找所有attributes中指定key、value匹配的产品列表
	 * @param key
	 * @param value
	 * @return
	 */
	@Query("{ ?0 : ?1 }")
	List<Product> findByAttributes(String key,String value);
}
