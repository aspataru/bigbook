package transformer;

import da.Product;
import dto.ProductDTO;

public class ProductTransformer {

	public static ProductDTO from(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setIsin(product.getIsin());
		productDTO.setCfi(product.getCfi());
		productDTO.setName(product.getName());
		productDTO.setLastPrice(product.getLastPrice());
		return productDTO;
	}

}
