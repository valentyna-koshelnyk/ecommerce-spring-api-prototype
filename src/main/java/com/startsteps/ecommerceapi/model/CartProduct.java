package com.startsteps.ecommerceapi.model;

import com.startsteps.ecommerceapi.utils.Default;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name="cart_product")
public class CartProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_product_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	private ShoppingCart shoppingCart;

	@Column(name = "quantity")
	private long quantity;

	@Column(name = "price_per_product")
	private Double priceProduct;
	@ManyToOne
	private OrderProducts orderProducts;

	@Default
	public CartProduct(Product product, ShoppingCart shoppingCart, Long quantity) {
		this.product = product;
		this.shoppingCart = shoppingCart;
		this.quantity = quantity;
	}

	@Override
	public String toString(){
		return "Product: " + this.product.getProductName() +
				"Product price: " + this.product.getPrice() +
				"Quantity: " + this.quantity +
				"Total per Product: " + this.priceProduct;
	}

}
