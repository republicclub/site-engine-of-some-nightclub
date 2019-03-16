package by.ladyka.merchshop;

import by.ladyka.club.ClubApplication;
import by.ladyka.club.config.constant.ClubRole;
import by.ladyka.club.dto.EventDTO;
import by.ladyka.merchshop.dto.ImageDTO;
import by.ladyka.merchshop.dto.ProductCategoryDTO;
import by.ladyka.merchshop.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.replacers.Replacer;
import com.github.database.rider.spring.api.DBRider;
import org.dbunit.dataset.ReplacementDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ClubApplication.class})
@AutoConfigureMockMvc
@DBRider
@DBUnit(replacers = ProductAndCategoryCrudIT.class)
public class ProductAndCategoryCrudIT implements Replacer {

    //EXAMPLE: private static LocalDateTime _YESTERDAY_ = LocalDateTime.now().minusDays(1);

    @Override
    public void addReplacements(ReplacementDataSet dataSet) {
        //EXAMPLE: dataSet.addReplacementSubstring("_YESTERDAY_", Timestamp.valueOf(_YESTERDAY_).toString());
    }

    @Autowired
    private MockMvc mvc;

    @Test
    @DataSet(
            value = {
                    "datasets/common/users.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/categories.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/images.yml"
            },
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "MERCHSHOP_PRODUCT_CATEGORY", "MERCHSHOP_PRODUCT", "FILES_USERS","MERCHSHOP_PRODUCT_IMAGES"},
            useSequenceFiltering = false,
            executeStatementsBefore = "SET FOREIGN_KEY_CHECKS = 0;",
            executeStatementsAfter = "SET FOREIGN_KEY_CHECKS = 1;"

    )
    @ExpectedDataSet(
            value = {
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/expectedCreatedProductWithImageAndCategoryRelations.yml",
            },
            compareOperation = CompareOperation.CONTAINS
    )
    @WithMockUser(username = "shop", authorities = ClubRole.ROLE_SHOP)
    public void createProduct() throws Exception {
        ProductDTO productDTORequest = new ProductDTO();
        productDTORequest.setCost(new BigDecimal(400));
        productDTORequest.setName("Some name of product");
        productDTORequest.setDescription("Some desc of product");
        productDTORequest.setSize("Some size of product");
        ProductCategoryDTO productCategoryDTORequest = new ProductCategoryDTO();
        productCategoryDTORequest.setId(4L);
        productDTORequest.setProductCategory(productCategoryDTORequest);
        ImageDTO image1 = new ImageDTO();
        image1.setPath("/files/2019/2/6666k.jpg");
        ImageDTO image2 = new ImageDTO();
        image2.setPath("/files/2019/2/7777g.jpg");
        List<ImageDTO> images = Arrays.asList(image1, image2);
        productDTORequest.setImages(images);

        String productDTORequestJson = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules()
                .writeValueAsString(productDTORequest);

        mvc.perform(
                post("/api/admin/merchshop/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDTORequestJson)

        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


    }



    @Test
    @DataSet(
            value = {
                    "datasets/common/users.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/categories.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/images.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/product.yml"
            },
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "MERCHSHOP_PRODUCT_CATEGORY", "MERCHSHOP_PRODUCT", "FILES_USERS","MERCHSHOP_PRODUCT_IMAGES"},
            useSequenceFiltering = false,
            executeStatementsBefore = "SET FOREIGN_KEY_CHECKS = 0;",
            executeStatementsAfter = "SET FOREIGN_KEY_CHECKS = 1;"
    )
    @ExpectedDataSet(
            value = {
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/expectedUpdatedProductWithImageAndCategoryRelations.yml",
            },
            compareOperation = CompareOperation.CONTAINS
    )
    @WithMockUser(username = "shop", authorities = ClubRole.ROLE_SHOP)
    public void updateProduct() throws Exception {
        ProductDTO productDTORequest = new ProductDTO();
        productDTORequest.setId(1L);
        productDTORequest.setCost(new BigDecimal(350));
        productDTORequest.setName("Some name of product UPDATED");
        productDTORequest.setDescription("Some desc of product");
        productDTORequest.setSize("Some size of product UPDATED");
        ProductCategoryDTO productCategoryDTORequest = new ProductCategoryDTO();
        productCategoryDTORequest.setId(3L);
        productDTORequest.setProductCategory(productCategoryDTORequest);
        ImageDTO image1 = new ImageDTO();
        image1.setPath("/files/2019/2/88888w.jpg");
        ImageDTO image2 = new ImageDTO();
        image2.setPath("/files/2019/2/99999x.jpg");
        List<ImageDTO> images = Arrays.asList(image1, image2);
        productDTORequest.setImages(images);

        String productDTORequestJson = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules()
                .writeValueAsString(productDTORequest);

        mvc.perform(
                post("/api/admin/merchshop/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDTORequestJson)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


    }



    @Test
    @DataSet(
            value = {
                    "datasets/common/users.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/categories.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/images.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/product.yml"
            },
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "MERCHSHOP_PRODUCT_CATEGORY", "MERCHSHOP_PRODUCT", "FILES_USERS","MERCHSHOP_PRODUCT_IMAGES"},
            useSequenceFiltering = false,
            executeStatementsBefore = "SET FOREIGN_KEY_CHECKS = 0;",
            executeStatementsAfter = "SET FOREIGN_KEY_CHECKS = 1;"
    )
    @ExpectedDataSet(
            value = {
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/expectedDeletedProductWithImageAndCategoryRelations.yml",
            },
            compareOperation = CompareOperation.CONTAINS
    )
    @WithMockUser(username = "shop", authorities = ClubRole.ROLE_SHOP)
    public void deleteProduct() throws Exception {
        ProductDTO productDTORequest = new ProductDTO();
        productDTORequest.setId(1L);

        String productDTORequestJson = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules()
                .writeValueAsString(productDTORequest);

        mvc.perform(
                delete("/api/admin/merchshop/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDTORequestJson)

        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


    }




    @Test
    @DataSet(
            value = {
                    "datasets/common/users.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/categories.yml",
            },
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "MERCHSHOP_PRODUCT_CATEGORY", "MERCHSHOP_PRODUCT", "FILES_USERS","MERCHSHOP_PRODUCT_IMAGES"},
            useSequenceFiltering = false,
            executeStatementsBefore = "SET FOREIGN_KEY_CHECKS = 0;",
            executeStatementsAfter = "SET FOREIGN_KEY_CHECKS = 1;"
    )
    @ExpectedDataSet(
            value = {
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/expectedCreatedCategory.yml",
            },
            compareOperation = CompareOperation.CONTAINS
    )
    @WithMockUser(username = "shop", authorities = ClubRole.ROLE_SHOP)
    public void createCategory() throws Exception {
        ProductCategoryDTO productCategoryDTORequest = new ProductCategoryDTO();
        productCategoryDTORequest.setParentCategoryId(4L);
        productCategoryDTORequest.setName("Some name of category");
        productCategoryDTORequest.setDescription("Some description of category");

        String productCategoryDTORequestJson = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules()
                .writeValueAsString(productCategoryDTORequest);

        mvc.perform(
                post("/api/admin/merchshop/product-category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productCategoryDTORequestJson)

        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


    }



    @Test
    @DataSet(
            value = {
                    "datasets/common/users.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/categories.yml",
            },
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "MERCHSHOP_PRODUCT_CATEGORY", "MERCHSHOP_PRODUCT", "FILES_USERS","MERCHSHOP_PRODUCT_IMAGES"},
            useSequenceFiltering = false,
            executeStatementsBefore = "SET FOREIGN_KEY_CHECKS = 0;",
            executeStatementsAfter = "SET FOREIGN_KEY_CHECKS = 1;"
    )
    @ExpectedDataSet(
            value = {
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/expectedUpdatedCategory.yml",
            },
            compareOperation = CompareOperation.CONTAINS
    )
    @WithMockUser(username = "shop", authorities = ClubRole.ROLE_SHOP)
    public void updateCategory() throws Exception {
        ProductCategoryDTO productCategoryDTORequest = new ProductCategoryDTO();
        productCategoryDTORequest.setId(4L);
        productCategoryDTORequest.setParentCategoryId(3L);
        productCategoryDTORequest.setName("Some product name 4");
        productCategoryDTORequest.setDescription("Some description of category UPDATED");

        String productCategoryDTORequestJson = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules()
                .writeValueAsString(productCategoryDTORequest);

        mvc.perform(
                post("/api/admin/merchshop/product-category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productCategoryDTORequestJson)

        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


    }




    @Test
    @DataSet(
            value = {
                    "datasets/common/users.yml",
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/categories.yml",
            },
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "MERCHSHOP_PRODUCT_CATEGORY", "MERCHSHOP_PRODUCT", "FILES_USERS","MERCHSHOP_PRODUCT_IMAGES"},
            useSequenceFiltering = false,
            executeStatementsBefore = "SET FOREIGN_KEY_CHECKS = 0;",
            executeStatementsAfter = "SET FOREIGN_KEY_CHECKS = 1;"
    )
    @ExpectedDataSet(
            value = {
                    "datasets/by/ladyka/merchshop/ProductAndCategoryCrudIT/expectedDeletedCategory.yml",
            },
            compareOperation = CompareOperation.CONTAINS
    )
    @WithMockUser(username = "shop", authorities = ClubRole.ROLE_SHOP)
    public void deleteCategory() throws Exception {
        ProductCategoryDTO productCategoryDTORequest = new ProductCategoryDTO();
        productCategoryDTORequest.setId(4L);

        String productCategoryDTORequestJson = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules()
                .writeValueAsString(productCategoryDTORequest);

        mvc.perform(
                delete("/api/admin/merchshop/product-category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productCategoryDTORequestJson)

        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


    }






}
