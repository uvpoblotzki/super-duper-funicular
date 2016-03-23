package juni5.examples;

import javax.money.MonetaryAmount;
import java.util.List;

/**
 * Ein Warenkorb verwaltet Dinge die gekauft werden können und kann nützliche Informationen
 * über diese ermitteln
 */
public interface Warenkorb {

    interface Entry {
        int getAmount();

        Product getProduct();

        void setAmount(int amount);
    }

    String getIdentifier();

    void addAll(Product... product);

    void remove(String identifier);

    void clear();

    int size();

    MonetaryAmount sum();

    List<Entry> getContent();

}
