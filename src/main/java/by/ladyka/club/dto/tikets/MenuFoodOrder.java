package by.ladyka.club.dto.tikets;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class MenuFoodOrder {
	private Map<Long, Integer> food = new LinkedHashMap<>();
	private Map<Long, BigDecimal> foodPrice = new LinkedHashMap<>();
	private BigDecimal totalMoney = new BigDecimal(0);
}
