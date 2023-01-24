package dtoModels.car;

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
