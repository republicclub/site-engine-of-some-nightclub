package by.ladyka.merchshop.endpoints.api;

import by.ladyka.club.config.constant.ClubRole;
import by.ladyka.merchshop.dto.ProductCategoryDTO;
import by.ladyka.merchshop.dto.ProductDTO;
import by.ladyka.merchshop.service.ProductCategoryService;
import by.ladyka.merchshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin/merchshop/product-category", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCategoryAdminController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(method = RequestMethod.POST)
    @Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_SHOP})
    public @ResponseBody
    Map<String, Object> save(Principal principal, HttpServletRequest httpServletRequest, @RequestBody ProductCategoryDTO productCategoryDTO) {
        Map<String, Object> result = new HashMap<>();
        result.put("input", productCategoryDTO);
        try {
            result.put("success", true);
            result.put("data", productCategoryService.save(productCategoryDTO));
        } catch (Exception ex) {
            result.put("message", ex.getLocalizedMessage());
            result.put("success", false);
            logger.error("Error", ex);
        }
        return result;
    }



    @RequestMapping(method = RequestMethod.DELETE)
    @Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_SHOP})
    public @ResponseBody
    Map<String, Object> delete(Principal principal, HttpServletRequest httpServletRequest, @RequestBody ProductCategoryDTO productCategoryDTO) {
        Map<String, Object> result = new HashMap<>();
        result.put("input", productCategoryDTO);
        try {
            result.put("success", true);
            result.put("data", productCategoryService.delete(productCategoryDTO));
        } catch (Exception ex) {
            result.put("message", ex.getLocalizedMessage());
            result.put("success", false);
            logger.error("Error", ex);
        }
        return result;
    }

    //subcategory
    @GetMapping(params = { "parentid" })
    @Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_SHOP})
    public @ResponseBody
    List<ProductCategoryDTO> getSubcategoriesByParentCategoryId(@RequestParam("parentid") Long parentCategoryId){
        return productCategoryService.getSubcategoriesByParentCategoryId(parentCategoryId);
    }
}