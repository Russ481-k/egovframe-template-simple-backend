package egovframework.let.cms.user.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CMS05_ROLE")
@Getter
@Setter
public class Cms05Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(unique = true)
    private String roleName;

    private String description;

    @OneToMany(mappedBy = "role")
    private List<Cms05User> users = new ArrayList<>();
} 