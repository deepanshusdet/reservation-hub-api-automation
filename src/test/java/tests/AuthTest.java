package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import utils.AuthUtil;

public class AuthTest extends BaseTest {

    @Test
    public void checkToken() {

        String token = AuthUtil.getToken();

     
    }
}