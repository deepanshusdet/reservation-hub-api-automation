package base;

import java.io.IOException;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import utils.ConfigReader;

public class BaseTest {

    @BeforeClass
    public void setup() throws IOException {

        ConfigReader.loadProperties();

        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
    }
}