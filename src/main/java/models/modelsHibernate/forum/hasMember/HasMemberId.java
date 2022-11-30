package models.modelsHibernate.forum.hasMember;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class HasMemberId implements Serializable {
    private Long pid;
    private Long fid;
}
