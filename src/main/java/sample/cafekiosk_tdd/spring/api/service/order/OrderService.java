package sample.cafekiosk_tdd.spring.api.service.order;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk_tdd.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk_tdd.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk_tdd.spring.domain.order.Order;
import sample.cafekiosk_tdd.spring.domain.order.OrderRepository;
import sample.cafekiosk_tdd.spring.domain.product.Product;
import sample.cafekiosk_tdd.spring.domain.product.ProductRepository;
import sample.cafekiosk_tdd.spring.domain.product.ProductType;
import sample.cafekiosk_tdd.spring.domain.stock.Stock;
import sample.cafekiosk_tdd.spring.domain.stock.StockRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductsBy(productNumbers);

        deductQuantity(products);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream()
                .collect(toMap(Product::getProductNumber, p->p));

        List<Product> duplicateProducts = productNumbers.stream()
                .map(productMap::get)
                .collect(toList());

        return duplicateProducts;
    }

    private void deductQuantity(List<Product> products) {
        List<String> stockProductNumbers = extractStockProductNumbers(products);

        Map<String, Long> productCountMap = createCountMapBy(stockProductNumbers);
        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountMap.get(stockProductNumber).intValue();

            if (stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }
    }

    private List<String> extractStockProductNumbers(List<Product> products) {
        return products.stream()
                .filter(product -> ProductType.hasStock(product.getType()))
                .map(Product::getProductNumber)
                .collect(toList());
    }

    private Map<String, Long> createCountMapBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
                .collect(groupingBy(p -> p, counting()));
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        return stocks.stream()
                .collect(toMap(Stock::getProductNumber, s -> s));
    }
}
