package grape.springdata.mongodb.core;

import java.util.regex.Pattern;

import lombok.Getter;
import lombok.ToString;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 *
 * @author grape
 * @date 2019-05-17
 */
@Getter
@ToString
public final class EmailAddress {

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

	@Field("email")
	private final String value;

	private String suffix;

	public EmailAddress(String value){
		Assert.isTrue(isValidEmailAddress(value),"invalid emailAddress.");
		this.value =value;
		this.suffix = value.substring(value.indexOf("@")+1);
	}

	/**
	 * 校验字符串是否合法的emailAddress
	 * @param candidate
	 * @return
	 */
	public static boolean isValidEmailAddress(String candidate){
		return StringUtils.hasText(candidate) ? PATTERN.matcher(candidate).matches():false;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(!(obj instanceof EmailAddress)){
			return false;
		}
		EmailAddress that = (EmailAddress)obj;
		return this.value.equals(that.value);
	}
}
