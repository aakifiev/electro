package electro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@JsonIgnoreProperties({"gardenPlot"})
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_plot_id")
    private GardenPlot gardenPlot;

    private LocalDate date;

    private Long count;

    public Record() {
    }

    public Record(GardenPlot gardenPlot, LocalDate date, Long count) {
        this.gardenPlot = gardenPlot;
        this.date = date;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public GardenPlot getGardenPlot() {
        return gardenPlot;
    }

    public void setGardenPlot(GardenPlot gardenPlot) {
        this.gardenPlot = gardenPlot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", gardenPlot=" + gardenPlot.getId() +
                ", date=" + date +
                ", count=" + count +
                '}';
    }
}
