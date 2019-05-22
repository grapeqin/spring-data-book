package grape.springdata.mongodb.core;

import lombok.Getter;
import lombok.ToString;

import org.springframework.util.Assert;

/**
 * 地址对象
 * @author grape
 * @date 2019-05-17
 */
@Getter
@ToString
public class Address {

	private final String street;
	private final String city;
	private final String country;

	public Address(String street,String city,String country){
		Assert.hasText(street,"street不能为空!");
		Assert.hasText(city,"city不能为空!");
		Assert.hasText(country,"country不能为空!");
		this.street = street;
		this.city = city;
		this.country = country;
	}
}
