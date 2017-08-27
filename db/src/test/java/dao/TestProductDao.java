package dao;

import da.Product;
import docker.DockerRule;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TestProductDao {

    @Rule
    public DockerRule dockerRule = new DockerRule();

    private ProductDao productDao;

    @Test
    public void shouldInsertAProduct() {
        productDao = new ProductDao();
        Product theProduct = new Product("CH000000000", "XXXXXXXX", "Test", BigDecimal.valueOf(42));
        productDao.persist(theProduct);

        Product retrieved = productDao.findByIsin("CH000000000");
        assertThat(retrieved).isNotNull();
        assertThat(retrieved).isEqualToComparingFieldByField(theProduct);
    }

}