package Lab2.Entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Ирина on 17.02.2017.
 */
@XmlType(propOrder = { "idDestination", "idCountry", "resort", "hotel", "countryByIdCountry", "catalogueTourssByIdDestination"}, name = "DestinationEntity")
@XmlRootElement
public class DestinationEntity implements Serializable{
    private Integer idDestination;
    private Integer idCountry;
    private String resort;
    private String hotel;
    @XmlTransient
    private Collection<TourEntity> catalogueTourssByIdDestination;
    private CountryEntity countryByIdCountry;

    public Integer getIdDestination() {
        return idDestination;
    }

    public void setIdDestination(Integer idDestination) {
        this.idDestination = idDestination;
    }

    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public String getResort() {
        return resort;
    }

    public void setResort(String resort) {
        this.resort = resort;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public static String tableName = "destination";
    public static String columnId = "id_destination";
    public static String columnIdCountry = "id_country";
    public static String columnResort = "resort";
    public static String columnHotel = "hotel";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DestinationEntity that = (DestinationEntity) o;

        if (idDestination != null ? !idDestination.equals(that.idDestination) : that.idDestination != null)
            return false;
        if (idCountry != null ? !idCountry.equals(that.idCountry) : that.idCountry != null) return false;
        if (resort != null ? !resort.equals(that.resort) : that.resort != null) return false;
        if (hotel != null ? !hotel.equals(that.hotel) : that.hotel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDestination != null ? idDestination.hashCode() : 0;
        result = 31 * result + (idCountry != null ? idCountry.hashCode() : 0);
        result = 31 * result + (resort != null ? resort.hashCode() : 0);
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DestinationEntity{" +
                "idDestination=" + idDestination +
                ", idCountry=" + idCountry +
                ", resort='" + resort + '\'' +
                ", hotel='" + hotel + '\'' +
                ", catalogueTourssByIdDestination=" + catalogueTourssByIdDestination +
                ", countryByIdCountry=" + countryByIdCountry +
                '}';
    }

    public Collection<TourEntity> getCatalogueTourssByIdDestination() {
        return catalogueTourssByIdDestination;
    }

    public void setCatalogueTourssByIdDestination(Collection<TourEntity> catalogueTourssByIdDestination) {
        this.catalogueTourssByIdDestination = catalogueTourssByIdDestination;
    }

    public CountryEntity getCountryByIdCountry() {
        return countryByIdCountry;
    }

    public void setCountryByIdCountry(CountryEntity countryByIdCountry) {
        this.countryByIdCountry = countryByIdCountry;
    }
}
