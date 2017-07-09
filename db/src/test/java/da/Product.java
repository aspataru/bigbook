package da;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by aspataru on 7/9/17.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    private String isin;
    private String cfid;
    private String name;
    private BigDecimal lastPrice;

}
