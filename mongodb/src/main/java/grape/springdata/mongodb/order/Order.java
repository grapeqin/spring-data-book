package grape.springdata.mongodb.order;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.mysema.commons.lang.Assert;
import grape.springdata.mongodb.core.AbstractDocument;
import grape.springdata.mongodb.core.Address;
import grape.springdata.mongodb.core.Customer;
import lombok.Getter;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author grape
 * @date 2019-05-22
 */
@Getter
@Document
public class Order extends AbstractDocument {

	@DBRef
	private Customer customer;

	private Address billingAddress;

	private Address shippingAddress;

	private Set<LineItem> lineItems = new HashSet<>();

	@PersistenceConstructor
	public Order(Customer customer,Address shippingAddress,Address billingAddress){
		Assert.notNull(customer,"customer must not be null!");
		Assert.notNull(shippingAddress,"shippingAddress must not be null!");

		this.customer = customer;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
	}

	public Order(Customer customer,Address shippingAddress){
		this(customer,shippingAddress,null);
	}

	/**
	 * 添加购物车
	 * @param item must not be null
	 * @throws IllegalArgumentException when item is null
	 */
	public void addLineItem(LineItem item){
		Assert.notNull(item,"item must not be null!");
		lineItems.add(item);
	}

	/**
	 * 获取当前订单的总金额
	 * @return
	 */
	public BigDecimal getTotalPriceOfOrder(){
		BigDecimal total = BigDecimal.ZERO;
		for(LineItem lineItem : lineItems){
			total = total.add(lineItem.getTotalPrice());
		}
		return total;
	}
}
