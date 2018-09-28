package T20180927;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// enum type for Vehicle
enum VehicleSize {
    Motorcycle,
    Compact,
    Large,
}

abstract class Vehicle {
    // Write your code here
    abstract int size();//What size lot takes

    abstract int length();//how many lot takes

    private Lot lot;
    private int level;
    private int onSize;


    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }
}

class Motorcycle extends Vehicle {
    @Override
    int size() {
        return 0;
    }

    @Override
    int length() {
        return 1;
    }
}

class Car extends Vehicle {
    @Override
    int size() {
        return 1;
    }

    @Override
    int length() {
        return 1;
    }
}

class Bus extends Vehicle {
    @Override
    int size() {
        return 2;
    }

    @Override
    int length() {
        return 5;
    }
}

/* Represents a level in a parking garage */
class Level {
    private Row[] rows;

    public Level(Row[] rows) {
        this.rows = rows;
    }

    public Lot alloc(int size, int length) {
        for (Row row : rows) {
            Lot lot = row.alloc(size, length);
            if (lot != null)
                return lot;
        }
        return null;
    }
}

/* Represents a row in a parking garage */
class Row {
    private Lots[] lots;//length is 3

    public Row(Lots[] lots) {
        this.lots = lots;
    }

    public Lot alloc(int size, int length) {
        for (int i = size; i < 3; i++) {
            Lot lot = lots[i].alloc(length);
            if (lot != null) {
                return lot;
            }
        }
        return null;
    }
}

class Lots {
    public Lots(Lot lot) {
        lots = new LinkedList<>();
        this.lots.add(lot);
    }

    private List<Lot> lots;

    public Lot alloc(int length) {
        for (int i = 0; i < lots.size(); i++) {
            Lot lot = lots.get(i);
            if (lot.getLength() < length) {
                continue;
            }

            if (lot.getLength() == length) {
                lots.remove(i);
                lot.setLots(this);
                return lot;
            }

            Lot result = new Lot(this, lot.getStart(), length);
            lot.setStart(lot.getStart() + length);
            lot.setLength(lot.getLength() - length);
            return result;
        }
        return null;
    }

    public void back(Lot backLot) {
        for (int i = 0; i < lots.size(); i++) {
            Lot lot = lots.get(i);

            if (lot.getEnd() == backLot.getStart()) {
                lot.setLength(lot.getLength() + backLot.getLength());
                int next = i + 1;
                while (next < lots.size()) {
                    Lot nextLot = lots.get(next);
                    if (lot.getEnd() == nextLot.getStart()) {
                        lot.setLength(lot.getLength() + nextLot.getLength());
                        lots.remove(next);
                    } else {
                        return;
                    }
                }

                return;
            }
            if (backLot.getEnd() == lot.getStart()) {
                lot.setStart(backLot.getStart());
                lot.setLength(lot.getLength() + backLot.getLength());
                return;
            }

            if (backLot.getStart() < lot.getStart()) {
                lots.add(i, backLot.backed());
                return;
            }
        }
        lots.add(backLot.backed());
    }
}

class Lot {
    Lots lots;

    private int start;
    private int length;

    public Lot() {
    }

    public Lot(int start, int length) {
        this.start = start;
        this.length = length;
    }

    public Lot(Lots lots, int start, int length) {
        this.lots = lots;
        this.start = start;
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return start + length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Lots getLots() {
        return lots;
    }

    public void setLots(Lots lots) {
        this.lots = lots;
    }

    public Lot backed() {
        this.lots = null;
        return this;
    }
}

/**
 * Description
 * Design a parking lot.
 * <p>
 * see CC150 OO Design for details.
 * <p>
 * n levels, each level has m rows of spots and each row has k spots.So each level has m x k spots.
 * The parking lot can park motorcycles, cars and buses
 * The parking lot has motorcycle spots, compact spots, and large spots
 * Each row, motorcycle spots id is in range [0,k/4)(0 is included, k/4 is not included), compact spots id is in range [k/4,k/4*3) and large spots id is in range [k/4*3,k).
 * A motorcycle can park in any spot
 * A car park in single compact spot or large spot
 * A bus can park in five large spots that are consecutive and within same row. it can not park in small spots
 * <p>
 * Example
 * level=1, num_rows=1, spots_per_row=11
 * parkVehicle("Motorcycle_1") // return true
 * parkVehicle("Car_1") // return true
 * parkVehicle("Car_2") // return true
 * parkVehicle("Car_3") // return true
 * parkVehicle("Car_4") // return true
 * parkVehicle("Car_5") // return true
 * parkVehicle("Bus_1") // return false
 * unParkVehicle("Car_5")
 * parkVehicle("Bus_1") // return true
 * <p>
 * 0
 */
public class ParkingLot {

    private Level[] levels;

    // @param n number of leves
    // @param num_rows  each level has num_rows rows of spots
    // @param spots_per_row each row has spots_per_row spots
    public ParkingLot(int n, int num_rows, int spots_per_row) {
        // Write your code here
        levels = new Level[n];
        int s1 = spots_per_row / 4;
        int s2 = spots_per_row / 4 * 2;
        int s3 = spots_per_row - s1 - s2;

        for (int i = 0; i < n; i++) {
            Row[] rows = new Row[num_rows];
            for (int row = 0; row < num_rows; row++) {

                Lots[] lots = new Lots[3];
                lots[0] = new Lots(new Lot(0, s1));
                lots[1] = new Lots(new Lot(0, s2));
                lots[2] = new Lots(new Lot(0, s3));
                rows[row] = new Row(lots);
            }
            levels[i] = new Level(rows);
        }
    }

    // Park the vehicle in a spot (or multiple spots)
    // Return false if failed
    public boolean parkVehicle(Vehicle vehicle) {
        for (Level cLevel : levels) {
            Lot lot = cLevel.alloc(vehicle.size(), vehicle.length());
            if (lot != null) {
                vehicle.setLot(lot);
                return true;
            }
        }
        return false;
    }

    // unPark the vehicle
    public void unParkVehicle(Vehicle vehicle) {
        if (vehicle.getLot() == null)
            return;
        Lot lot = vehicle.getLot();
        lot.getLots().back(lot);
    }

    private static ParkingLot parkingLot;

    private static Map<String, Object> cache = new HashMap<>();

    public static int index = 1;

    private static void parkVehicle(String vehicle) {
        Vehicle v = getBean(vehicle);
        System.out.println(index++ + ":" + parkingLot.parkVehicle(v));
    }

    private static void unParkVehicle(String vehicle) {
        Vehicle v = getBean(vehicle);
        parkingLot.unParkVehicle(v);
    }

    static Vehicle getBean(String vehicle) {

        Object bean = cache.get(vehicle);
        if (bean == null) {
            String type = vehicle.substring(0, vehicle.indexOf("_"));
            switch (type) {
                case "Car":
                    bean = new Car();
                    break;
                case "Bus":
                    bean = new Bus();
                    break;
                case "Motorcycle":
                    bean = new Motorcycle();
                    break;
            }
            cache.put(vehicle, bean);
        }
        return (Vehicle) bean;
    }

    public static void main(String[] args) {

        parkingLot = new ParkingLot(2, 3, 34);
        parkVehicle("Car_1");
        unParkVehicle("Car_1");
        parkVehicle("Car_2");
        parkVehicle("Car_3");
        unParkVehicle("Car_3");
        parkVehicle("Bus_1");
        parkVehicle("Bus_2");
        unParkVehicle("Car_2");
        unParkVehicle("Bus_1");
        parkVehicle("Motorcycle_1");
        parkVehicle("Bus_3");
        parkVehicle("Bus_4");
        parkVehicle("Car_4");
        parkVehicle("Car_5");
        parkVehicle("Bus_5");
        parkVehicle("Motorcycle_2");
        unParkVehicle("Bus_4");
        parkVehicle("Bus_6");
        parkVehicle("Bus_7");
        parkVehicle("Motorcycle_3");
        parkVehicle("Motorcycle_4");
        unParkVehicle("Motorcycle_1");
        parkVehicle("Car_6");
        parkVehicle("Bus_8");
        parkVehicle("Bus_9");
        unParkVehicle("Bus_6");
        unParkVehicle("Car_5");
        parkVehicle("Motorcycle_5");
        unParkVehicle("Bus_5");
        parkVehicle("Car_7");
        unParkVehicle("Motorcycle_4");
        parkVehicle("Bus_10");
        parkVehicle("Motorcycle_6");
        parkVehicle("Car_8");
        parkVehicle("Motorcycle_7");
        parkVehicle("Motorcycle_8");
        parkVehicle("Car_9");
        parkVehicle("Car_10");
        parkVehicle("Car_11");
        parkVehicle("Bus_11");
        parkVehicle("Car_12");
        parkVehicle("Motorcycle_9");
        unParkVehicle("Bus_9");
        parkVehicle("Bus_12");
        parkVehicle("Car_13");
        parkVehicle("Bus_13");
        parkVehicle("Motorcycle_10");
        parkVehicle("Motorcycle_11");
        parkVehicle("Bus_14");
        parkVehicle("Bus_15");
        parkVehicle("Bus_16");
        parkVehicle("Car_14");
        parkVehicle("Car_15");
        parkVehicle("Bus_17");
        unParkVehicle("Bus_17");
        parkVehicle("Motorcycle_12");
        unParkVehicle("Motorcycle_9");
        parkVehicle("Motorcycle_13");
        parkVehicle("Bus_18");
        unParkVehicle("Bus_16");
        parkVehicle("Motorcycle_14");
        parkVehicle("Motorcycle_15");
        parkVehicle("Motorcycle_16");
        unParkVehicle("Car_10");
        parkVehicle("Bus_19");
        parkVehicle("Bus_20");
        unParkVehicle("Car_11");
        parkVehicle("Bus_21");
        unParkVehicle("Motorcycle_10");
        parkVehicle("Bus_22");
        unParkVehicle("Car_6");
        parkVehicle("Motorcycle_17");
        unParkVehicle("Motorcycle_14");
        parkVehicle("Bus_23");
        unParkVehicle("Bus_13");
        parkVehicle("Bus_24");
        parkVehicle("Bus_25");
        parkVehicle("Car_16");
        parkVehicle("Motorcycle_18");
        parkVehicle("Motorcycle_19");
        unParkVehicle("Motorcycle_7");
        unParkVehicle("Bus_3");
        parkVehicle("Motorcycle_20");
        parkVehicle("Bus_26");
        parkVehicle("Car_17");
        parkVehicle("Motorcycle_21");
        parkVehicle("Car_18");
        parkVehicle("Car_19");
        unParkVehicle("Motorcycle_16");
        parkVehicle("Bus_27");
        parkVehicle("Bus_28");
        parkVehicle("Motorcycle_22");
        parkVehicle("Motorcycle_23");
        parkVehicle("Motorcycle_24");
        parkVehicle("Car_20");
        unParkVehicle("Motorcycle_2");
        unParkVehicle("Car_4");
        parkVehicle("Motorcycle_25");
        parkVehicle("Car_21");
        parkVehicle("Bus_29");
        parkVehicle("Bus_30");
        unParkVehicle("Motorcycle_19");
        parkVehicle("Motorcycle_26");
        parkVehicle("Bus_31");
        parkVehicle("Motorcycle_27");
        parkVehicle("Bus_32");
        unParkVehicle("Motorcycle_8");
        parkVehicle("Motorcycle_28");
        unParkVehicle("Motorcycle_12");
        parkVehicle("Bus_33");
        unParkVehicle("Motorcycle_25");
        parkVehicle("Car_22");
        unParkVehicle("Motorcycle_15");
        parkVehicle("Car_23");
        parkVehicle("Bus_34");
        unParkVehicle("Car_12");
        parkVehicle("Car_24");
        parkVehicle("Bus_35");
        parkVehicle("Bus_36");
        parkVehicle("Car_25");
        unParkVehicle("Car_25");
        parkVehicle("Motorcycle_29");
        parkVehicle("Car_26");
        parkVehicle("Car_27");
        parkVehicle("Car_28");
        parkVehicle("Bus_37");
        parkVehicle("Bus_38");
        parkVehicle("Bus_39");
        parkVehicle("Motorcycle_30");
        parkVehicle("Motorcycle_31");
        parkVehicle("Car_29");
        parkVehicle("Car_30");
        parkVehicle("Car_31");
        parkVehicle("Motorcycle_32");
        parkVehicle("Car_32");
        parkVehicle("Motorcycle_33");
        parkVehicle("Bus_40");
        unParkVehicle("Motorcycle_17");
        parkVehicle("Car_33");
        parkVehicle("Bus_41");
        parkVehicle("Bus_42");
        parkVehicle("Bus_43");
        parkVehicle("Bus_44");
        parkVehicle("Bus_45");
        unParkVehicle("Car_21");
        parkVehicle("Bus_46");
        parkVehicle("Car_34");
        unParkVehicle("Car_34");
        parkVehicle("Motorcycle_34");
        parkVehicle("Motorcycle_35");
        parkVehicle("Motorcycle_36");
        parkVehicle("Motorcycle_37");
        parkVehicle("Car_35");
        unParkVehicle("Motorcycle_37");
        parkVehicle("Car_36");
        parkVehicle("Bus_47");
        parkVehicle("Motorcycle_38");
        parkVehicle("Motorcycle_39");
        parkVehicle("Car_37");
        unParkVehicle("Car_18");
        parkVehicle("Motorcycle_40");
        parkVehicle("Car_38");
        parkVehicle("Motorcycle_41");
        parkVehicle("Bus_48");
        parkVehicle("Motorcycle_42");
        parkVehicle("Motorcycle_43");
        parkVehicle("Motorcycle_44");
        parkVehicle("Motorcycle_45");
        parkVehicle("Motorcycle_46");
        unParkVehicle("Car_38");
        parkVehicle("Bus_49");
        unParkVehicle("Car_23");
        parkVehicle("Motorcycle_47");
        parkVehicle("Motorcycle_48");
        parkVehicle("Car_39");
        unParkVehicle("Car_32");
        parkVehicle("Car_40");
        parkVehicle("Bus_50");
        parkVehicle("Motorcycle_49");
        parkVehicle("Motorcycle_50");
        parkVehicle("Bus_51");
        parkVehicle("Bus_52");
        unParkVehicle("Motorcycle_32");
        unParkVehicle("Car_30");
        parkVehicle("Car_41");
        unParkVehicle("Bus_19");
        parkVehicle("Car_42");
        parkVehicle("Motorcycle_51");
        parkVehicle("Car_43");
        unParkVehicle("Motorcycle_35");
        parkVehicle("Car_44");
        parkVehicle("Bus_53");
        unParkVehicle("Motorcycle_50");
        unParkVehicle("Car_43");
        parkVehicle("Bus_54");
        parkVehicle("Motorcycle_52");
        parkVehicle("Car_45");
        unParkVehicle("Car_39");
        parkVehicle("Motorcycle_53");
        parkVehicle("Motorcycle_54");
        parkVehicle("Bus_55");
        parkVehicle("Motorcycle_55");
        parkVehicle("Car_46");
        parkVehicle("Motorcycle_56");
        parkVehicle("Motorcycle_57");
        parkVehicle("Car_47");
        parkVehicle("Motorcycle_58");
        parkVehicle("Bus_56");
        parkVehicle("Motorcycle_59");
        parkVehicle("Motorcycle_60");
        unParkVehicle("Car_15");
        parkVehicle("Motorcycle_61");
        unParkVehicle("Bus_11");
        parkVehicle("Car_48");
        parkVehicle("Motorcycle_62");
        unParkVehicle("Motorcycle_59");
        parkVehicle("Motorcycle_63");
        unParkVehicle("Motorcycle_56");
        parkVehicle("Bus_57");
        parkVehicle("Bus_58");
        unParkVehicle("Car_42");
        unParkVehicle("Motorcycle_52");
        parkVehicle("Car_49");
        parkVehicle("Bus_59");
        parkVehicle("Motorcycle_64");
        parkVehicle("Bus_60");
        parkVehicle("Bus_61");
        parkVehicle("Car_50");
        unParkVehicle("Motorcycle_57");
        parkVehicle("Motorcycle_65");
        parkVehicle("Motorcycle_66");
        parkVehicle("Bus_62");
        parkVehicle("Bus_63");
        parkVehicle("Motorcycle_67");
        unParkVehicle("Motorcycle_43");
        unParkVehicle("Car_8");
        parkVehicle("Car_51");
        parkVehicle("Motorcycle_68");
        parkVehicle("Car_52");
        parkVehicle("Motorcycle_69");
        parkVehicle("Motorcycle_70");
        parkVehicle("Bus_64");
        parkVehicle("Bus_65");
        unParkVehicle("Motorcycle_36");
        unParkVehicle("Bus_10");
        parkVehicle("Car_53");
        parkVehicle("Bus_66");
        unParkVehicle("Motorcycle_31");
        parkVehicle("Car_54");
        parkVehicle("Motorcycle_71");
        parkVehicle("Bus_67");
        unParkVehicle("Motorcycle_5");
        parkVehicle("Car_55");
        parkVehicle("Motorcycle_72");
        parkVehicle("Motorcycle_73");
        parkVehicle("Car_56");
        unParkVehicle("Car_26");
        parkVehicle("Motorcycle_74");
        unParkVehicle("Car_24");
        parkVehicle("Motorcycle_75");
        parkVehicle("Car_57");
        parkVehicle("Car_58");
        parkVehicle("Motorcycle_76");
        parkVehicle("Car_59");
        parkVehicle("Motorcycle_77");
        unParkVehicle("Car_53");
        parkVehicle("Car_60");
        parkVehicle("Bus_68");
        unParkVehicle("Car_16");
        parkVehicle("Motorcycle_78");
        parkVehicle("Motorcycle_79");
        parkVehicle("Motorcycle_80");
        unParkVehicle("Motorcycle_60");
        unParkVehicle("Car_36");
        parkVehicle("Motorcycle_81");
        parkVehicle("Bus_69");
        unParkVehicle("Motorcycle_68");
        parkVehicle("Motorcycle_82");
        parkVehicle("Motorcycle_83");
        parkVehicle("Car_61");
        parkVehicle("Car_62");
        unParkVehicle("Car_9");
        parkVehicle("Motorcycle_84");
        unParkVehicle("Car_31");
        parkVehicle("Motorcycle_85");
        unParkVehicle("Car_14");
        unParkVehicle("Motorcycle_81");
        parkVehicle("Motorcycle_86");
        parkVehicle("Bus_70");
        parkVehicle("Bus_71");
        unParkVehicle("Bus_15");
        parkVehicle("Car_63");
        unParkVehicle("Motorcycle_78");
        parkVehicle("Bus_72");
        parkVehicle("Bus_73");
        parkVehicle("Motorcycle_87");
        unParkVehicle("Motorcycle_28");
        parkVehicle("Bus_74");
        parkVehicle("Car_64");
        unParkVehicle("Motorcycle_51");
        parkVehicle("Car_65");
        parkVehicle("Motorcycle_88");
        parkVehicle("Car_66");
        parkVehicle("Bus_75");
        parkVehicle("Car_67");
        parkVehicle("Car_68");
        unParkVehicle("Car_54");
        parkVehicle("Car_69");
        parkVehicle("Bus_76");
        parkVehicle("Bus_77");
        parkVehicle("Motorcycle_89");
        parkVehicle("Car_70");
        parkVehicle("Bus_78");
        parkVehicle("Bus_79");
        parkVehicle("Motorcycle_90");
    }

}