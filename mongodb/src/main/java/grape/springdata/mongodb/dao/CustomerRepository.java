package grape.springdata.mongodb.dao;

import java.util.List;

import grape.springdata.mongodb.core.Customer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

/**
 *
 * @author grape
 * @date 2019-05-16
 */
public interface CustomerRepository extends CrudRepository<Customer,String>, QuerydslPredicateExecutor<Customer> {

	/**
	 * 根据emailAdderss来查询Customer
	 * @param value
	 * @return
	 */
	@Nullable  Customer findByEmailAddressValue(String value);


	/**
	 * 根据国家编码分页查询Customer列表
	 * @param country
	 * @param pageable
	 * @return
	 */
	Slice<Customer> findByAddressesCountry(String country, Pageable pageable);


	/**
	 * 匹配指定lastname的Customer集合
	 * @param lastNames
	 * @return
	 */
	List<Customer> findDistinctByLastnameIsIn(List<String> lastNames);
}
