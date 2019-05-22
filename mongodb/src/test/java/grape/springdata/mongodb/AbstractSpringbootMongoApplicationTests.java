package grape.springdata.mongodb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Line;

import com.mongodb.MongoClient;
import grape.springdata.mongodb.core.Address;
import grape.springdata.mongodb.core.Customer;
import grape.springdata.mongodb.core.EmailAddress;
import grape.springdata.mongodb.core.Product;
import grape.springdata.mongodb.dao.CustomerRepository;
import grape.springdata.mongodb.dao.OrderRepository;
import grape.springdata.mongodb.dao.ProductRepository;
import grape.springdata.mongodb.order.LineItem;
import grape.springdata.mongodb.order.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 * @author grape
 * @date 2019-05-17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractSpringbootMongoApplicationTests {

	@Autowired
	protected MongoClient mongoClient;

	@Autowired
	protected MongoTemplate mongoTemplate;

	@Autowired
	protected CustomerRepository customerRepository;

	@Autowired
	protected ProductRepository productRepository;

	@Autowired
	protected OrderRepository orderRepository;

	@Before
	public void setUp(){
		//customers
		customerRepository.deleteAll();

		EmailAddress emailAddress = new EmailAddress("dave@dmband.com");
		Address address = new Address("Broadway","New York","United States");
		Customer dave = new Customer("Dave","Matthews");
		dave.setEmailAddress(emailAddress);
		dave.addAddress(address);
		customerRepository.save(dave);

		emailAddress = new EmailAddress("alicia@keys.com");
		address = new Address("27 Broadway","New York","United States");
		Customer alicia = new Customer("Alicia","Keys");
		alicia.setEmailAddress(emailAddress);
		alicia.addAddress(address);
		customerRepository.save(alicia);

		//products
		productRepository.deleteAll();

		List<Product> productList = new ArrayList<>();
		Product ipad = new Product("iPad", BigDecimal.valueOf(499.0),"Apple tablet device");
		ipad.setAttribute("connector","plus");
		productList.add(ipad);

		Product macBook = new Product("MacBook Pro",BigDecimal.valueOf(1299.0),"Apple notebook");
		productList.add(macBook);

		Product dock = new Product("dock",BigDecimal.valueOf(49.0),"Dock for iPhone/iPad");
		dock.setAttribute("connector","plus");
		productList.add(dock);

		productRepository.saveAll(productList);

		//orders
		Order order = new Order(dave,address);
		LineItem ipadLineItem = new LineItem(ipad,2);
		order.addLineItem(ipadLineItem);
		LineItem macBookLineItem = new LineItem(macBook);
		order.addLineItem(macBookLineItem);
		orderRepository.save(order);

	}

	@After
	public void destroy(){
	}

	@Test
	public void testComponents(){
		assertThat(mongoClient, is(notNullValue()));
		assertThat(mongoTemplate,is(notNullValue()));
		assertThat(customerRepository,is(notNullValue()));
	}
}
