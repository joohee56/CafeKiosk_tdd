package sample.cafekiosk_tdd.spring.api.controller.product;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sample.cafekiosk_tdd.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk_tdd.spring.domain.product.ProductType.HANDMADE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sample.cafekiosk_tdd.spring.ControllerTestSupport;
import sample.cafekiosk_tdd.spring.api.controller.product.request.ProductCreateRequest;

class ProductControllerTest extends ControllerTestSupport {

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                        .type(HANDMADE)
                                        .sellingStatus(SELLING)
                                        .name("아메리카노")
                                        .price(1000)
                                        .build();

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @DisplayName("신규 상품을 등록할 때, 상품 타입은 필수 입력값이다.")
    @Test
    void createProductWithOutProductType() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(1000)
                .build();

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록할 때, 상품 판매 상태는 필수 입력값이다.")
    @Test
    void createProductWithOutProductSellingStatus() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .name("아메리카노")
                .price(1000)
                .build();

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록할 때, 상품 이름은 필수 입력값이다.")
    @Test
    void createProductWithOutName() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .price(1000)
                .build();

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록할 때, 상품 가격은 양수이다.")
    @Test
    void createProductWithOutPrice() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(0)
                .build();

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("판매 상품을 조회한다.")
    @Test
    void getSellingProducts() throws Exception {
        //given
        given(productService.getSellingProducts())
                        .willReturn(List.of());

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/selling")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());
    }

}