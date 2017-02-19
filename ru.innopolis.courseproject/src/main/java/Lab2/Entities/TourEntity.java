package Lab2.Entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.Collection;

/**
 * Created by Ирина on 17.02.2017.
 */
@XmlType(propOrder = { "idTour", "name", "dateStart", "dateEnd", "cost", "idDestination", "destinationPlaceByIdDestination", "orderssByIdTour"}, name = "TourEntity")
@XmlRootElement
public class TourEntity implements Serializable{
    private Integer idTour;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private Integer cost;
    private Integer idDestination;
    private DestinationEntity destinationPlaceByIdDestination;
    private Collection<OrderEntity> orderssByIdTour;

    public static String tableName = "tour";
    public static String columnId = "id_tour";
    public static String columnName = "name";
    public static String columnDateStart = "date_start";
    public static String columnDateEnd = "date_end";
    public static String columnCost = "cost";
    public static String columnIdDestination = "id_destination";

    public Integer getIdTour() {
        return idTour;
    }

    public void setIdTour(Integer idTour) {
        this.idTour = idTour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getIdDestination() {
        return idDestination;
    }

    public void setIdDestination(Integer idDestination) {
        this.idDestination = idDestination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TourEntity that = (TourEntity) o;

        if (idTour != null ? !idTour.equals(that.idTour) : that.idTour != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (dateStart != null ? !dateStart.equals(that.dateStart) : that.dateStart != null) return false;
        if (dateEnd != null ? !dateEnd.equals(that.dateEnd) : that.dateEnd != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (idDestination != null ? !idDestination.equals(that.idDestination) : that.idDestination != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTour != null ? idTour.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (idDestination != null ? idDestination.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TourEntity{" +
                "idTour=" + idTour +
                ", name='" + name + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", cost=" + cost +
                ", idDestination=" + idDestination +
                ", destinationPlaceByIdDestination=" + destinationPlaceByIdDestination +
                ", orderssByIdTour=" + orderssByIdTour +
                '}';
    }

    public DestinationEntity getDestinationPlaceByIdDestination() {
        return destinationPlaceByIdDestination;
    }

    public void setDestinationPlaceByIdDestination(DestinationEntity destinationPlaceByIdDestination) {
        this.destinationPlaceByIdDestination = destinationPlaceByIdDestination;
    }

    public Collection<OrderEntity> getOrderssByIdTour() {
        return orderssByIdTour;
    }

    public void setOrderssByIdTour(Collection<OrderEntity> orderssByIdTour) {
        this.orderssByIdTour = orderssByIdTour;
    }
}
