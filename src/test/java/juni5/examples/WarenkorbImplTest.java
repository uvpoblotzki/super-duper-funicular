package juni5.examples;

import static org.junit.gen5.api.Assertions.assertAll;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertThrows;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit5.class)
@DisplayName("Warenkorb Tests")
public class WarenkorbImplTest {

	@Mock
	Product product;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		final MonetaryAmount prise = Monetary.getDefaultAmountFactory().setCurrency("EUR")
				.setNumber(1.00).create();
		when(product.getPrice()).thenReturn(prise);
		when(product.getIdentifier()).thenReturn("978-2434-2-2423-1");
	}

	@Test
	@DisplayName("Fügt ein Element hinzu")
	public void addAll_one() {
		final Warenkorb warenkorb = new WarenkorbImpl();
		warenkorb.addAll(product);

		assertAll(() -> assertTrue(warenkorb.size() == 1),
				() -> assertEquals(warenkorb.getContent().get(0).getProduct(), product),
				() -> assertEquals(warenkorb.getContent().get(0).getAmount(), 1));
	}

	@Test
	@DisplayName("Exception wenn Menge negativ")
	public void modify_negative_amount() {
		final Warenkorb warenkorb = new WarenkorbImpl();
		warenkorb.addAll(product);

		assertThrows(IllegalArgumentException.class,
				() -> warenkorb.getContent().get(0).setAmount(-1));
	}

	@Test
	@DisplayName("Summe zweimal das gleiche Produkt")
	public void sum_same() {
		final Warenkorb warenkorb = new WarenkorbImpl();
		warenkorb.addAll(product, product);

		assertTrue(warenkorb.sum().getNumber().longValueExact() == 2L);
	}

}