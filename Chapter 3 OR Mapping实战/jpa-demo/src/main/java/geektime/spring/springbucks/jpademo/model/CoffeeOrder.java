package geektime.spring.springbucks.jpademo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeOrder implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String customer;

    @ManyToMany
    @JoinTable(name = "t_order_coffee")
    private List<Coffee> items;

    @Column(nullable = false)
    private Integer state;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

}
