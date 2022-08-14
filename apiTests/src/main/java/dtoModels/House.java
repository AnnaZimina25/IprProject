package dtoModels;

import java.math.BigDecimal;
import java.util.List;

public class House {

//    {
//        "id": 3,
//            "floorCount": 2,
//            "price": 230,
//            "parkingPlaces": [
//        {
//            "isWarm": true,
//                "isCovered": true,
//                "placesCount": 1
//        },
//        {
//            "isWarm": false,
//                "isCovered": false,
//                "placesCount": 2
//        }
//  ],
//        "lodgers": [
//        {
//            "id": 2,
//                "firstName": "Vasiliy",
//                "secondName": "Rubenstein",
//                "age": 42,
//                "sex": "MALE",
//                "money": 929770
//        }
//  ]
//    }

    public Integer id;
    public Integer floorCount;
    public BigDecimal price;
    public List<ParkingPlace> parkingPlaces;
    public List<Person> lodgers;

    public House() {
    }

    public House(Integer id, Integer floorCount, BigDecimal price, List<ParkingPlace> parkingPlaces, List<Person> lodgers) {
        this.id = id;
        this.floorCount = floorCount;
        this.price = price;
        this.parkingPlaces = parkingPlaces;
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
