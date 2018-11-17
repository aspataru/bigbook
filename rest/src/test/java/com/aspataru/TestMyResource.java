package com.aspataru;

import da.Product;
import dao.ProductDao;
import dto.ProductDTO;
import main.Main;
import org.assertj.core.api.Assertions;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.math.BigDecimal;

public class TestMyResource {

	private static final Product PRODUCT = new Product("CH0000000000", "XXXXXX", "NAME", BigDecimal.ONE);

	@Rule
	public RuleChain rules = RuleChain
			.outerRule(new DockerRule())
			.around(new DbInsertingRule())
			.around(new ServerStartingRule());

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@Test
	public void testGetIt() {
		Client c = ClientBuilder.newClient();
		WebTarget target = c.target(Main.BASE_URI);

		final ProductDTO retrievedProduct =
				target.path("myresource/product/CH0000000000").request().get(ProductDTO.class);

		Assertions.assertThat(retrievedProduct).isEqualToComparingFieldByField(PRODUCT);
	}

	private class DbInsertingRule implements TestRule {
		@Override
		public Statement apply(Statement base, Description description) {
			return new Statement() {
				@Override
				public void evaluate() throws Throwable {
					ProductDao productDao = new ProductDao();
					productDao.persist(PRODUCT);
					base.evaluate();
				}
			};
		}
	}

	private class ServerStartingRule implements TestRule {
		HttpServer server;

		@Override
		public Statement apply(Statement base, Description description) {
			return new Statement() {
				@Override
				public void evaluate() throws Throwable {
					server = Main.startServer();
					try {
						base.evaluate();
					} finally {
						server.stop();
					}
				}
			};
		}
	}
}
