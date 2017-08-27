package da;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by aspataru on 7/9/17.
 */
@Entity
@Table(name = "PRODUCTS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Product {

    @Id
    private String isin;
    private String cfid;
    private String name;
    @Column(name = "LAST_PRICE")
    private BigDecimal lastPrice;

}
