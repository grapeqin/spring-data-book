package grape.springdata.mongodb.dao;

import grape.springdata.mongodb.core.Customer;
import grape.springdata.mongodb.order.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author grape
 * @date 2019-05-22
 */
public interface OrderRepository extends PagingAndSortingRepository<Order,String> {

	/**
	 * 分页查询用户拥有的订单列表
	 * @param customer 用户信息
	 * @param pageable 分页对象
	 * @return
	 */
	Page<Order> findByCustomer(Customer customer, Pageable pageable);
}
