package Lab2.DAO;

import Lab2.Entities.TourEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Ирина on 17.02.2017.
 */
@XmlRootElement(name = "tours")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToursDao {
    @XmlElement(name = "tour")
    private List<TourEntity> tours = null;

    public List<TourEntity> getTours() {
        return tours;
    }

    public void setTours(List<TourEntity> tours) {
        this.tours = tours;
    }
}

