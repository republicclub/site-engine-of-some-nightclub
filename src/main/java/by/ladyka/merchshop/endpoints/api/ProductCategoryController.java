package by.ladyka.merchshop.endpoints.api;

import by.ladyka.club.config.constant.ClubRole;
import by.ladyka.merchshop.dto.ProductCategoryDTO;
import by.ladyka.merchshop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/merchshop/product-category", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping(params = { "parentid" })
    @Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_SHOP})
    public @ResponseBody
    List<ProductCategoryDTO> getSubcategoriesByParentCategoryId(@RequestParam("parentid") Long parentCategoryId){
        return productCategoryService.getSubcategoriesByParentCategoryIdAndIsVisible(parentCategoryId);
    }
}
