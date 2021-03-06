
package by.ladyka.bepaid.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import static by.ladyka.bepaid.config.BePaidConfiguration.BE_PAID_VERSION;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "version",
    "test",
    "transaction_type",
    "attempts",
    "settings",
    "order",
    "customer"
})
@Data
public class Checkout {


    @JsonProperty("version")
    private double version = BE_PAID_VERSION;
    @JsonProperty("test")
    private boolean test;
    @JsonProperty("transaction_type")
    private TransactionType transactionType;
    @JsonProperty("attempts")
    private long attempts;
    @JsonProperty("settings")
    private Settings settings;
    @JsonProperty("order")
    private OrderDto order;
    @JsonProperty("customer")
    private Customer customer;
    @JsonProperty("token")
    private String token;
    @JsonProperty("redirect_url")
    private String redirectUrl;
}
