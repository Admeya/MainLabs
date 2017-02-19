package Lab2.Entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Ирина on 17.02.2017.
 */
@XmlType(propOrder = { "idCountry", "nameCountry", "destinationPlacesByIdCountry"}, name = "CountryEntity")
@XmlRootElement
public class CountryEntity implements Serializable{
    private Integer idCountry;
    private String nameCountry;


    private Collection<DestinationEntity> destinationPlacesByIdCountry;

    public static String tableName = "country";
    public static String columnId = "id_country";
    public static String columnName = "name_country";

    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEntity that = (CountryEntity) o;

        if (idCountry != null ? !idCountry.equals(that.idCountry) : that.idCountry != null) return false;
        if (nameCountry != null ? !nameCountry.equals(that.nameCountry) : that.nameCountry != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "CountryEntity{" +
                "idCountry=" + idCountry +
                ", nameCountry='" + nameCountry + '\'' +
                ", destinationPlacesByIdCountry=" + destinationPlacesByIdCountry +
                '}';
    }

    @Override
    public int hashCode() {
        int result = idCountry != null ? idCountry.hashCode() : 0;
        result = 31 * result + (nameCountry != null ? nameCountry.hashCode() : 0);
        return result;
    }

    public Collection<DestinationEntity> getDestinationPlacesByIdCountry() {
        return destinationPlacesByIdCountry;
    }

    public void setDestinationPlacesByIdCountry(Collection<DestinationEntity> destinationPlacesByIdCountry) {
        this.destinationPlacesByIdCountry = destinationPlacesByIdCountry;
    }
}
