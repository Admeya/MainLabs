package Lab2.DAO;

import Lab2.Entities.CountryEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Ирина on 17.02.2017.
 */
@XmlRootElement(name = "countries")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountriesDao {
    @XmlElement(name = "country")
    private List<CountryEntity> countries = null;

    public List<CountryEntity> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryEntity> countries) {
        this.countries = countries;
    }
}

