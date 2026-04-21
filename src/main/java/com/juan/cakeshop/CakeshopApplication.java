package com.juan.cakeshop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class CakeshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CakeshopApplication.class, args);
	}

}

//@Component
//class MyRunner implements CommandLineRunner {
//    @Autowired
//    private ProductService productService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        var response = productService.getProducts(1, 5);
//        System.out.println("Product response: "+response.toString());
//    }
//}
