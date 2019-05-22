package grape.springdata.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import grape.springdata.mongodb.AbstractSpringbootMongoApplicationTests;
import grape.springdata.mongodb.core.Address;
import grape.springdata.mongodb.core.Customer;
import grape.springdata.mongodb.core.QCustomer;
import org.junit.Test;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


/**
 *
 * @author grape
 * @date 2019-05-17
 */
public class CustomerSpringbootMongoApplicationTests extends AbstractSpringbootMongoApplicationTests {

	@Test
	public void testDefaultRepositoryMethods(){
		customerRepository.findAll().forEach(
				c -> {
					assertThat(c,is(notNullValue()));
					assertThat(customerRepository.findById(c.getId()),is(notNullValue()));
					assertThat(customerRepository.existsById(c.getId()),is(notNullValue()));

					c.addAddress(new Address("深圳市南山区","广东省","中国"));
					c = customerRepository.save(c);
					assertThat(c,is(notNullValue()));
					assertThat(c.getAddresses(),hasSize(2));
				}
		);
	}

	@Test
	public void testFindByEmailAddress() {
		String email = "";
		assertThat(customerRepository.findByEmailAddressValue(email),is(nullValue()));

		email = "dave@dmband.com";
		assertThat(customerRepository.findByEmailAddressValue(email),is(notNullValue()));
	}

	@Test
	public void testFindByAddressesCountry(){
		String country = "United States";
		int page = 0;
		Pageable pageable = PageRequest.of(page,1, Direction.DESC,"firstname");
		Slice<Customer> customers = null;
		do{
			customers=  customerRepository.findByAddressesCountry(country,pageable);
			customers.forEach(c -> {
				assertThat(c,is(notNullValue()));
				System.out.println(c);
			});
			pageable = PageRequest.of(++page,1, Direction.DESC,"firstname");
		}while(customers.hasNext());
		assertThat(page,is(2));
	}

	@Test
	public void testFindDistinctByLastnameIsIn(){
		List<String> lastnames = new ArrayList<>();

		assertThat(customerRepository.findDistinctByLastnameIsIn(lastnames),hasSize(0));

		lastnames.add("Matthews");
		lastnames.add("Keys");
		assertThat(customerRepository.findDistinctByLastnameIsIn(lastnames),hasSize(2));
	}


	/**
	 * 测试QueryDSL的支持情况
	 */
	@Test
	public void testByQuerydsl(){
		QCustomer customer = QCustomer.customer;
		customerRepository.findAll(customer.lastname.eq("Matthews")).forEach(
				c -> {
					assertThat(c.getLastname(),is("Matthews"));
				}
		);
	}

	@Test
	public void testDefaultTemplateMethods(){
		Customer c = mongoTemplate.findOne(query(where("firstname").is("Dave")),Customer.class);
		assertThat(c,is(notNullValue()));
		System.out.println(c);
	}
}
