package dtoModels.car;

import dtoModels.Model;

import java.math.BigDecimal;

/**
 * Класс хранения данных модели Car
 */
public class Car extends Model {

    private Integer id;
    private String engineType;
    private String mark;
    private String model;
    private BigDecimal price;
    private Integer personId;

    public Car() {
    }

    public Integer getId() {
        return id;
    }

    public Car setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEngineType() {
        return engineType;
    }

    public Car setEngineType(EngineType engineType) {
        this.engineType = engineType.getEngineType();
        return this;
    }

    public Car setDbEngineType(Integer engineType_id) {
        this.engineType = EngineType.values()[engineType_id - 1].getEngineType();
        return this;
    }

    public String getMark() {
        return mark;
    }

    public Car setMark(String mark) {
        this.mark = mark;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Car setModel(String model) {
        this.model = model;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Car setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getPersonId() {
        return personId;
    }

    public Car setPersonId(Integer personId) {
        this.personId = personId;
        return this;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", engineType='" + engineType + '\'' +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", personId=" + personId +
                '}';
    }
}
