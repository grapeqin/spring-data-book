package grape.springdata.mongodb.order;

import java.math.BigDecimal;

import grape.springdata.mongodb.core.AbstractDocument;
import grape.springdata.mongodb.core.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

/**
 *
 * @author grape
 * @date 2019-05-22
 */
@Getter
@ToString
@NoArgsConstructor
public class LineItem extends AbstractDocument {

	@DBRef
	private Product product;

	private BigDecimal price;

	private int amount;

	public LineItem(Product product,int amount){
		Assert.notNull(product,"product must not be null");
		Assert.isTrue(amount  > 0,"amount must be greater than 0");

		this.product = product;
		this.amount = amount;
		this.price = product.getPrice();
	}

	public LineItem(Product product){
		this(product,1);
	}

	/**
	 * 获取当前LineItem的总价格
	 * @return
	 */
	public BigDecimal getTotalPrice(){
		return price.multiply(BigDecimal.valueOf(amount));
	}
}
