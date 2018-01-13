package electro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties({"date"})
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_plot_id")
    private GardenPlot gardenPlot;*/
    private Long gardenPlotId;
    private LocalDateTime date;
    private Long count;

    public Record() {
    }

    public Record(Long gardenPlotId, LocalDateTime date, Long count) {
        this.gardenPlotId = gardenPlotId;
        this.date = date;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Long getGardenPlotId() {
        return gardenPlotId;
    }

    public void setGardenPlotId(Long gardenPlot) {
        this.gardenPlotId = gardenPlotId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
                ", gardenPlotId=" + gardenPlotId +
                //", gardenPlot=" + gardenPlot.getId() +
                //", date=" + date +
                ", count=" + count +
                '}';
    }
}
