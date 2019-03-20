package by.ladyka.merchshop.endpoints.api;

import by.ladyka.club.config.constant.ClubRole;
import by.ladyka.merchshop.dto.ProductDTO;
import by.ladyka.merchshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/merchshop/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(params = { "page", "size", "categoryid" })
    public @ResponseBody
    List<ProductDTO> getByCategoryId(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("categoryid") Long categoryId){
        Page<ProductDTO> products = productService.getByCategoryIdAndIsVisible(page, size, categoryId);
        return products.getContent();
    }
}
