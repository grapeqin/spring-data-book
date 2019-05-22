package grape.springdata.mongodb.core;

import java.util.HashSet;
import java.util.Set;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mongodb.morphia.annotations.Entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

/**
 * 用户
 * @author grape
 * @date 2019-05-16
 */
@Getter
@Setter
@ToString
@Document
@Entity
public class Customer extends AbstractDocument{

	private String firstname;

	private String lastname;

	private EmailAddress emailAddress;

	private Set<Address> addresses = new HashSet<>();

	public Customer(String firstname,String lastname){
		Assert.hasText(firstname,"firstname must not be null or empty");
		Assert.hasText(lastname,"lastname must not be null or empty");
		this.firstname = firstname;
		this.lastname = lastname;
	}

	/**
	 *
	 * @param address must not be {@literal null}
	 */
	public void addAddress(Address address){
		Assert.notNull(address,"address must not be null");
		addresses.add(address);
	}
}
