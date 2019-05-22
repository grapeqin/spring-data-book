package grape.springdata.mongodb.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;
import org.mongodb.morphia.annotations.Entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

/**
 *
 * @author grape
 * @date 2019-05-22
 */
@Getter
@ToString
@Document
@Entity
public class Product extends AbstractDocument{

	private String name;

	private String description;

	private BigDecimal price;

	private Map<String,String> attributes = new HashMap<>();


	@PersistenceConstructor
	public Product(String name,BigDecimal price,String description){
		Assert.hasText(name,"name must not be empty or null");
		Assert.isTrue(BigDecimal.ZERO.compareTo(price) < 0,"price must greater than 0");

		this.name = name;
		this.price = price;
		this.description = description;
	}

	public Product(String name,BigDecimal price){
		this(name,price,null);
	}

	/**
	 * 1.when value == null,then remove the key
	 * 2.when value != null,then put the key with the value
	 * @param name
	 * @param value
	 * @throws IllegalArgumentException when name is null
	 */
	public void setAttribute(String name,String value){
		Assert.hasText(name,"name must not be empty or null");
		if(null == value){
			attributes.remove(name);
		}else {
			attributes.put(name,value);
		}
	}
}
