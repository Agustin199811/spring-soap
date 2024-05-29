package com.uce.products.Endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.uce.products.Converter.ProductConverter;
import com.uce.products.Model.ProductModel;
import com.uce.products.Repository.ProductRepository;
import com.uce.products.gen.GetProductRequest;
import com.uce.products.gen.GetProductResponse;
import com.uce.products.gen.GetProductsRequest;
import com.uce.products.gen.GetProductsResponse;
import com.uce.products.gen.PostProductRequest;
import com.uce.products.gen.PostProductResponse;
import com.uce.products.gen.Product;

@Endpoint
public class ProductEndPoint {
    private static final String NAMESPACE_URI = "http://www.uce.com/products/gen";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConverter productConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        ProductModel productModel = productRepository.findByName(request.getName());
        response.setProduct(productConverter.convertProductModelToProduct(productModel));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProducts(@RequestPayload GetProductsRequest request) {
        GetProductsResponse response = new GetProductsResponse();
        List<ProductModel> productModels = productRepository.findAll();
        List<Product> products = productConverter.convertProductModelsToProducts(productModels);
        response.getProducts().addAll(products);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postProductRequest")
    @ResponsePayload
    public PostProductResponse postProducts(@RequestPayload PostProductRequest request) {
        PostProductResponse response = new PostProductResponse();
        ProductModel productModel = productConverter.convertProductToProductModel(request.getProduct());
        Product product = productConverter.convertProductModelToProduct(productRepository.save(productModel));
        response.setProduct(product);
        return response;
    }
}
