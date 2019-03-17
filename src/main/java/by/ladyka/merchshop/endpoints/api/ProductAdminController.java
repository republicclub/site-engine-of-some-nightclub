package by.ladyka.merchshop.endpoints.api;

import by.ladyka.club.config.constant.ClubRole;
import by.ladyka.club.dto.shared.BaseListResultDto;
import by.ladyka.merchshop.dto.ProductDTO;
import by.ladyka.merchshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin/merchshop/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductAdminController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    @Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_SHOP})
    public @ResponseBody
    Map<String, Object> save(Principal principal, HttpServletRequest httpServletRequest, @RequestBody ProductDTO productDTO) {
        Map<String, Object> result = new HashMap<>();
        result.put("input", productDTO);
        try {
            result.put("success", true);
            result.put("data", productService.save(productDTO));
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
    Map<String, Object> delete(Principal principal, HttpServletRequest httpServletRequest, @RequestBody ProductDTO productDTO) {
        Map<String, Object> result = new HashMap<>();
        result.put("input", productDTO);
        try {
            result.put("success", true);
            result.put("data", productService.delete(productDTO));
        } catch (Exception ex) {
            result.put("message", ex.getLocalizedMessage());
            result.put("success", false);
            logger.error("Error", ex);
        }
        return result;
    }

    @GetMapping(params = { "page", "size", "categoryid" })
    @Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_SHOP})
    public @ResponseBody
    List<ProductDTO> getByCategoryId(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("categoryid") Long categoryId){
        Page<ProductDTO> products = productService.getByCategoryId(page, size, categoryId);
        return products.getContent();
    }
}
