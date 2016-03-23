package juni5.examples;

import javax.money.MonetaryAmount;

/**
 * Ein Product hat ein paar Einschaften
 */
public interface Product {

    MonetaryAmount getPrice();

    String getName();

    String getIdentifier();

}
