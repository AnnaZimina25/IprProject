package dtoModels.person;

import java.util.Locale;

public enum Sex {

    MALE(true),
    FEMALE(false);

    private Boolean databaseValue;

    public Boolean getDatabaseValue() {
        return databaseValue;
    }

    Sex(Boolean databaseValue) {
        this.databaseValue = databaseValue;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
