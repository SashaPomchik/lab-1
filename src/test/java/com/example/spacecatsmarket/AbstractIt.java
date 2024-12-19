package com.example.spacecatsmarket;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.lang.String.format;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public abstract class AbstractIt {

//    private static final int PAYMENT_PORT = 8080;
//    private static final Path PAYMENT_DOCKERFILE = Paths.get("scripts", "docker", "payment-mock");
//    private static final GenericContainer PAYMENT_SERVICE_CONTAINER =
//        new GenericContainer(new ImageFromDockerfile()
//            .withFileFromPath(".", PAYMENT_DOCKERFILE)
//            .withDockerfile(PAYMENT_DOCKERFILE.resolve("Dockerfile")))
//            .withExposedPorts(PAYMENT_PORT);
//
//
//    static {
//        PAYMENT_SERVICE_CONTAINER.start();
//    }

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).configureStaticDsl(true).build();

    @DynamicPropertySource
    static void setupTestContainerProperties(DynamicPropertyRegistry registry) {

//        registry.add("application.payment-service.base-path", () -> format("http://%s:%d",
//           PAYMENT_SERVICE_CONTAINER.getHost(), PAYMENT_SERVICE_CONTAINER.getMappedPort(PAYMENT_PORT)));

        registry.add("application.payment-service.base-path", wireMockServer::baseUrl);
        WireMock.configureFor(wireMockServer.getPort());
    }

}
