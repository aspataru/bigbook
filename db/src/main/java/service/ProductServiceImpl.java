package service;

import da.Product;
import dao.ProductDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl {

    private final ProductDao productDao;

    public void save(Product product) {
        productDao.persist(product);
        log.info("Saved product {}", product);
    }

    public Product getById(String id) {
        return productDao.findByIsin(id);
    }

}
