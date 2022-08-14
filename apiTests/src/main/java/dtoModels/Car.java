package dtoModels;

import java.math.BigDecimal;

public class Car {

//    {
//        "id": 8,
//            "engineType": "Electric",
//            "mark": "Tesla",
//            "model": "Model X",
//            "price": 70000
//    }

    public Integer id;
    public String engineType;
    public String mark;
    public String model;
    public BigDecimal price;

    public Car() {
    }

    public Car(Integer id, String engineType, String mark, String model, BigDecimal price) {
        this.id = id;
        this.engineType = engineType;
        this.mark = mark;
        this.model = model;
        this.price = price;
    }

    public enum EngineType {
        GASOLINE("Gasoline"),
        DIESEL("Diesel"),
        CNG("CNG"),
        HYDROGENIC("Hydrogenic"),
        ELECTRIC("Electric"),
        PHEV("PHEV");

       private String engineType;

        EngineType(String engineType) {
            this.engineType = engineType;
        }

        public String getEngineType() {
            return engineType;
        }
    }
}
