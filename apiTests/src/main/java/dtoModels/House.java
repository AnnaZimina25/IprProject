package dtoModels;

import dtoModels.person.Person;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс хранения данных модели House
 */
public class House extends Model {

    private Integer id;
    private Integer floorCount;
    private BigDecimal price;
    private List<ParkingPlace> parkingPlaces;
    private List<Person> lodgers;

    public House() {
    }

    public House(Integer id, Integer floorCount, BigDecimal price, List<ParkingPlace> parkingPlaces, List<Person> lodgers) {
        this.id = id;
        this.floorCount = floorCount;
        this.price = price;
        this.parkingPlaces = parkingPlaces;
        this.lodgers = lodgers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(Integer floorCount) {
        this.floorCount = floorCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ParkingPlace> getParkingPlaces() {
        return parkingPlaces;
    }

    public void setParkingPlaces(List<ParkingPlace> parkingPlaces) {
        this.parkingPlaces = parkingPlaces;
    }

    public List<Person> getLodgers() {
        return lodgers;
    }

    public void setLodgers(List<Person> lodgers) {
        this.lodgers = lodgers;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", floorCount=" + floorCount +
                ", price=" + price +
                ", parkingPlaces=" + parkingPlaces +
                ", lodgers=" + lodgers +
                '}';
    }
}
