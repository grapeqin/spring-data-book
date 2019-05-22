package grape.springdata.mongodb.core;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author grape
 * @date 2019-05-16
 */
@Setter
@Getter
public abstract class AbstractDocument {

	private String id;

	@Override
	public int hashCode() {
		return null == id ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(this.id == null || obj == null || !(obj instanceof AbstractDocument)){
			return false;
		}

		AbstractDocument that = (AbstractDocument)obj;
		return this.id.equals(that.getId());
	}
}
