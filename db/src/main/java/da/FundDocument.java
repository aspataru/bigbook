package da;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by aspataru on 7/12/17.
 */

@Entity
@Table(name = "FUND_DOCUMENTS")
@NoArgsConstructor
@AllArgsConstructor
public class FundDocument {

    @Id
    private String isin;
    @Column(name = "FUND_NAME")
    private String fundName;
}
