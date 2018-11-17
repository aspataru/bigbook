package resources;

import da.Product;
import dao.ProductDao;
import dto.ProductDTO;
import service.ProductServiceImpl;
import transformer.ProductTransformer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	private final ProductServiceImpl productService = new ProductServiceImpl(new ProductDao());

	/**
	 * Method handling HTTP GET requests. The returned object will be sent
	 * to the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Path("product/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductDTO getIt(@PathParam("id") String id) {
		Product retrievedProduct = productService.getById(id);
		return ProductTransformer.from(retrievedProduct);
	}
}
