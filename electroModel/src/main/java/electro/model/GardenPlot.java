package electro.model;

import javax.persistence.*;
import java.util.List;

@Entity
//@JsonSerialize(keyUsing = GardenPlotDeserializer.class)
public class GardenPlot {

    @Id
    private Long id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "gardenPlot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Record> records;

    public GardenPlot() {
    }

    public GardenPlot(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    @Override
    //@JsonValue
    public String toString() {
        return "GardenPlot{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", records=" + records +
                '}';
    }
}
