package sample.cafekiosk_tdd.spring.api.controller.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk_tdd.spring.api.controller.product.request.ProductCreateRequest;
import sample.cafekiosk_tdd.spring.api.service.product.ProductService;
import sample.cafekiosk_tdd.spring.api.service.product.response.ProductResponse;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/api/v1/products/selling")
    public List<ProductResponse> getSellingProducts() {
        return productService.getSellingProducts();
    }

    @PostMapping("/api/v1/products/new")
    public ProductResponse createProduct(@RequestBody ProductCreateRequest request) {
        return productService.createProduct(request);
    }
}
