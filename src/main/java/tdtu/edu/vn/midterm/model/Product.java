package tdtu.edu.vn.midterm.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "brand")
    private String brand;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "screen_size")
    private double screenSize;
    @Column(name = "processor")
    private String processor;
    @Column(name = "ram")
    private int ram;
    @Column(name = "rom")
    private int rom;
    @Column(name = "graphic_card")
    private String graphicCard;
    @Column(name = "weight")
    private double weight;
    @Column(name = "image")
    private String image;

    @Builder
    public Product(String name, int price, String brand, int quantity,
                   double screenSize, String processor, int ram, int rom,
                   String graphicCard, double weight, String image) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.quantity = quantity;
        this.screenSize = screenSize;
        this.processor = processor;
        this.ram = ram;
        this.rom = rom;
        this.graphicCard = graphicCard;
        this.weight = weight;
        this.image = image;
    }

}
