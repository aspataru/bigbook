package dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

	private String isin;
	private String cfi;
	private String name;
	private BigDecimal lastPrice;

}
