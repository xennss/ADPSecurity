package com.example.ADPProject.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ADPProject.model.Customer;
import com.example.ADPProject.repository.CustomerRepository;
import com.example.ADPProject.model.CustomerFactory;
import com.example.ADPProject.model.Token;
import com.example.ADPProject.util.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/account") // Ruta base
public class TokenAPI {

    public static Token appUserToken;

    @GetMapping
    public String getAll() {
        return "jwt-fake-token-asdfasdfasfa";
    }

    @PostMapping("/token") // Ruta específica para crear el token
    public ResponseEntity<Token> createTokenForCustomer(@RequestBody Customer customer) {
        String username = customer.getName();
        String password = customer.getPassword();

        // Falta el checkpassword que el treiem nomes per mirar si va el funcionament de fer un token
		if (username != null && username.length() > 0 && password != null && password.length() > 0 ) {
            Token token = createToken(username);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private boolean checkPassword(String username, String password) {
        System.out.println("Checking credentials for user: " + username);
        if (username.equals("ApiClientApp") && password.equals("secret")) {
            return true;
        }
        Customer cust = getCustomerByNameFromCustomerAPI(username);
        System.out.println("Customer found: " + cust);
        return cust != null && cust.getName().equals(username) && cust.getPassword().equals(password);
    }

    private Token createToken(String username) {
        String scopes = "com.webage.data.apis";
    	// special case for application user
    	if( username.equalsIgnoreCase("ApiClientApp")) {
    		scopes = "com.webage.auth.apis";
    	}
    	String token_string = JWTHelper.createToken(scopes);
    	
    	
    	return new Token(token_string);
    }

    public Token getAppUserToken() {
        if (appUserToken == null || appUserToken.getToken() == null || appUserToken.getToken().length() == 0) {
            appUserToken = createToken("ApiClientApp");
        }
        return appUserToken;
    }

    private Customer getCustomerByNameFromCustomerAPI(String username) {
        try {
            URL url = new URL("http://localhost:8082/api/customers/byname/" + username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            Token token = getAppUserToken();
            conn.setRequestProperty("authorization", "Bearer " + token.getToken());

            if (conn.getResponseCode() != 200) {
                return null;
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder output = new StringBuilder();
                String out;
                while ((out = br.readLine()) != null) {
                    output.append(out);
                }
                conn.disconnect();
                return CustomerFactory.getCustomer(output.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
