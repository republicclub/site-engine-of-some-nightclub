package by.ladyka.club.entity.menu;

import by.ladyka.club.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_category")
@EntityListeners(AuditingEntityListener.class)
public class MenuCategory extends AbstractEntity {
	private String name;
	@Lob
	private String description;
	private Integer categoryType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private MenuCategory parent;

	@OneToMany(mappedBy = "parent")
	private List<MenuCategory> children = new ArrayList<>();

	@OneToMany(mappedBy = "category")
	private List<MenuItem> items = new ArrayList<>();

	public MenuCategory(Long id) {
		setId(id);
	}
}