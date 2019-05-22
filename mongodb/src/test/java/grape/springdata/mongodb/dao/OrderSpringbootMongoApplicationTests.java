package grape.springdata.mongodb.dao;

import grape.springdata.mongodb.AbstractSpringbootMongoApplicationTests;
import grape.springdata.mongodb.core.Address;
import grape.springdata.mongodb.core.Customer;
import grape.springdata.mongodb.core.Product;
import grape.springdata.mongodb.order.LineItem;
import grape.springdata.mongodb.order.Order;
import org.junit.Test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 *
 * @author grape
 * @date 2019-05-22
 */
public class OrderSpringbootMongoApplicationTests extends AbstractSpringbootMongoApplicationTests {

	@Test
	public void testCreateOrder(){
		Customer dave = customerRepository.findByEmailAddressValue("dave@dmband.com");
		assertThat(dave,is(notNullValue()));

		Address shippingAddress = dave.getAddresses().iterator().next();
		assertThat(shippingAddress,is(notNullValue()));

		Product ipad = productRepository.findByAttributes("attributes.connector","plus").iterator().next();
		assertThat(ipad,is(notNullValue()));

		Order order = new Order(dave,shippingAddress);
		order.addLineItem(new LineItem(ipad));

		order = orderRepository.save(order);
		assertThat(order.getId(),is(notNullValue()));
	}

	@Test
	public void testFindByCustomer(){
		Pageable pageable = PageRequest.of(0,1);
		Customer customer = customerRepository.findByEmailAddressValue("dave@dmband.com");
		assertThat(customer,is(notNullValue()));

		Page<Order> orders = orderRepository.findByCustomer(customer,pageable);
		assertThat(orders.getContent(),hasSize(is(1)));
		assertThat(orders.hasNext(),is(false));
		assertThat(orders.getContent().iterator().next().getLineItems().iterator().next().getProduct().getName().equals("iPad"),is(true));
	}

}
