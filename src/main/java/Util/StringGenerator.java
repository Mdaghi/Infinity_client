package Util;
import java.util.UUID;
public class StringGenerator {

	public static String generateString() {
        return  UUID.randomUUID().toString();
    }
}
