package springbootfinal.indieWearhaul.entity;

import javax.persistence.ManyToMany;

public class ProductOrders {

	private long orderId;
	private long productId;
	
	
	@ManyToMany(mappedBy = "order")
	public long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	@ManyToMany(mappedBy = "product")
	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	
	
}
