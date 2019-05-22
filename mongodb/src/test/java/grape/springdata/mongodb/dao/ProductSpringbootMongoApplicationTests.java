package grape.springdata.mongodb.dao;

import java.math.BigDecimal;
import java.util.List;

import grape.springdata.mongodb.AbstractSpringbootMongoApplicationTests;
import grape.springdata.mongodb.core.Product;
import org.junit.Test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
/**
 *
 * @author grape
 * @date 2019-05-22
 */
public class ProductSpringbootMongoApplicationTests extends AbstractSpringbootMongoApplicationTests {

	@Test
	public void testCreateProduct(){
		Product product = new Product("Camera bag", BigDecimal.valueOf(49.99));
		product = productRepository.save(product);
		assertThat(product,is(notNullValue()));
	}

	@Test
	public void lookupByDescription(){
		Pageable pageable = PageRequest.of(0,1, Direction.DESC,"name");
		Page<Product> products = productRepository.findByDescriptionContaining("Apple",pageable);

		assertThat(products.getContent(),hasSize(1));
		assertThat(products,hasItem(hasProperty("name",is("iPad"))));

		assertThat(products.getTotalPages(),is(2));
		assertThat(products.isFirst(),is(true));
		assertThat(products.isLast(),is(false));
		assertThat(products.hasNext(),is(true));
	}

	@Test
	public void testFindByAttributes(){
		List<Product> products = productRepository.findByAttributes("attributes.connector","plus");

		assertThat(products,hasItems(hasProperty("name",is("dock"))));
	}
}
