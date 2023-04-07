package tdtu.edu.vn.midterm.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private double totalPrice;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
}
