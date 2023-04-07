package tdtu.edu.vn.midterm.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    private double totalPrice;
    private String status;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail = new ArrayList<OrderDetail>();
}
