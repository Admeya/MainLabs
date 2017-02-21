package Lab2.JAXB;

import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;


/**
 * Created by Ирина on 17.02.2017.
 */
public class Binding {
    static Logger logger = Logger.getLogger(Binding.class);

    public void marshalling(Class clazz, Object entity, File xmlPath) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(entity, xmlPath);
            marshaller.marshal(entity, System.out);
        } catch (JAXBException exception) {
            logger.error("JAXBException при маршаллинге ", exception);
        }
    }

    public Object unmarshalling(Class clazz, File xmlPath) {
        Object obj = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            obj = unmarshaller.unmarshal(xmlPath);
        } catch (
                JAXBException exception) {
            logger.error("JAXBException при демаршаллинге", exception);
        }
        return obj;
    }
}
