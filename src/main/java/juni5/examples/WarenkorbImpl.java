package juni5.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class WarenkorbImpl implements Warenkorb {

	private final List<EntryImpl> products = new ArrayList<>();
	private String identifier;

	@Override
	public void remove(final String identifier) {
		products.removeIf(e -> e.getProduct().getIdentifier().equals(identifier));
	}

	@Override
	public MonetaryAmount sum() {
		final MonetaryAmount nullAmount = Monetary.getDefaultAmountFactory()
				.setCurrency("EUR").setNumber(0).create();
		return products.stream()
				.map(e -> e.getProduct().getPrice().multiply(e.getAmount()))
				.reduce(nullAmount, MonetaryAmount::add);
	}

	@Override
	public List<Entry> getContent() {
		return Collections.unmodifiableList(products);
	}

	@Override
	public void addAll(final Product... products) {
		final Collection<Product> productToAdd = Arrays.asList(products);
		final Collection<Product> productsInCart = this.products.stream()
				.map(Entry::getProduct).collect(Collectors.toList());
		this.products.stream().filter(e -> productToAdd.contains(e.getProduct()))
				.forEach(EntryImpl::incr);
		this.products.addAll(productToAdd.stream()
				.filter(product -> !productsInCart.contains(product))
				.map(product -> new EntryImpl(1, product)).collect(Collectors.toList()));
	}

	@Override
	public void clear() {
		products.clear();
	}

	@Override
	public int size() {
		return products.size();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	private static class EntryImpl implements Entry {
		private int amount;

		@Setter(AccessLevel.PRIVATE)
		private Product product;

		@Override
		public void setAmount(final int amount) {
			if (amount < 0) {
				throw new IllegalArgumentException("Menge kann nich < 0 sein");
			}
			this.amount = amount;
		}

		private void incr() {
			setAmount(getAmount() + 1);
		}
	}

}
