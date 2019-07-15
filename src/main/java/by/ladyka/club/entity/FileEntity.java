package by.ladyka.club.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files_users")
@EntityListeners(AuditingEntityListener.class)
public class FileEntity  extends BasicEntity {
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
