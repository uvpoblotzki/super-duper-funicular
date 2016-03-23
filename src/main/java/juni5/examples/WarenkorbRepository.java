package juni5.examples;

import java.util.Optional;

/**
 * Ein Warenkorb Repository zum
 */
public interface WarenkorbRepository {

    Warenkorb create();

    Optional<Warenkorb> get(String identifier);

    void saveOrUpdate(Warenkorb warenkorb);

    void delete(Warenkorb warenkorb);

}
