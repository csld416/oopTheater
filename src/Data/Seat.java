package Data;

import java.util.ArrayList;

public class Seat {
    private String label;

    // Constructor
    public Seat(String label) {
        this.label = label;
    }

    // Getter
    public String getLabel() {
        return label;
    }

    // Setter
    public void setLabel(String label) {
        this.label = label;
    }

    // Static dummy list
    public static ArrayList<Seat> dummySeats = new ArrayList<>() {{
        add(new Seat("A-1"));
        add(new Seat("A-2"));
        add(new Seat("B-1"));
        add(new Seat("B-2"));
        add(new Seat("C-1"));
    }};
}