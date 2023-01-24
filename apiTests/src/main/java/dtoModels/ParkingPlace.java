package dtoModels;

/**
 * Класс хранения данных модели ParkingPlace
 */
public class ParkingPlace extends Model {

    public Boolean isWarm;
    public Boolean isCovered;
    public Integer placesCount;

    public ParkingPlace() {
    }

    public ParkingPlace(Boolean isWarm, Boolean isCovered, Integer placesCount) {
        this.isWarm = isWarm;
        this.isCovered = isCovered;
        this.placesCount = placesCount;
    }

    @Override
    public String toString() {
        return "ParkingPlace{" +
                "isWarm=" + isWarm +
                ", isCovered=" + isCovered +
                ", placesCount=" + placesCount +
                '}';
    }
}
