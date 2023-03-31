package tdtu.edu.vn.midterm.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
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

    public Product() {

    }

    public Product(String name, int price, String brand, int quantity,
                   int screenSize, String processor, int ram, int rom,
                   String graphicCard, double weight) {
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
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }

    public String getGraphicCard() {
        return graphicCard;
    }

    public void setGraphicCard(String graphicCard) {
        this.graphicCard = graphicCard;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", brand=" + brand + ", quantity="
                + quantity + ", screenSize=" + screenSize + ", processor=" + processor + ", ram=" + ram
                + ", rom=" + rom + ", graphicCard=" + graphicCard + ", weight=" + weight + ", image=" + image + "]";
    }
}
