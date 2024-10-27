package sample.cafekiosk_tdd.spring.api.service.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk_tdd.spring.api.controller.product.request.ProductCreateRequest;
import sample.cafekiosk_tdd.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk_tdd.spring.domain.product.Product;
import sample.cafekiosk_tdd.spring.domain.product.ProductRepository;
import sample.cafekiosk_tdd.spring.domain.product.ProductSellingStatus;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        String productNumber = calculateNextProductNumber();
        Product savedProduct = productRepository.save(request.toEntity(productNumber));
        return ProductResponse.of(savedProduct);
    }

    private String calculateNextProductNumber() {
        Optional<String> latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber.isEmpty()) {
            return "001";
        }
        return String.format("%03d", Integer.parseInt(latestProductNumber.get())+1);
    }
}
