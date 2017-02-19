package Lab2.DAO;

import Lab2.Entities.DestinationEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Ирина on 17.02.2017.
 */
@XmlRootElement(name = "destinations")
@XmlAccessorType(XmlAccessType.FIELD)
public class DestinationsDao {
    @XmlElement(name = "destination")
    private List<DestinationEntity> destinations = null;

    public List<DestinationEntity> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<DestinationEntity> destinations) {
        this.destinations = destinations;
    }
}

