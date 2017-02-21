package Lab2;

import Lab2.DAO.*;
import Lab2.Entities.*;
import Lab2.Helpers.ConnectionSingleton;
import Lab2.JAXB.Binding;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Ирина on 17.02.2017.
 */
public class Main {
    static Logger logger = Logger.getLogger(Main.class);

    static {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        Connection conn = ConnectionSingleton.getInstance().getConnection();

//         Стремимся к унификации. Составляем список объектов, при помощи которых
//         будем выгружать из БД
        CopyOnWriteArrayList<AbstractDao> objects = new CopyOnWriteArrayList<AbstractDao>();
        objects.add(new OrderDao(conn));
        objects.add(new TourDao(conn));
        objects.add(new DestinationDao(conn));
        objects.add(new CountryDao(conn));
        objects.add(new ClientDao(conn));
        objects.add(new EmployeeDao(conn));

        //Список потоков, чтобы отследить, когда они закончат работу.
        List<Thread> thrs = new ArrayList<>();

        logger.trace("Start marshalling!");
        for (int i = 0; i < objects.size(); i++) {
            final int t = i;
            Thread thr = new Thread(new Runnable() {
                public void run() {
                    //Получаем из листа ДАО таблицы
                    AbstractDao currentObj = objects.get(t);
                    Binding bind = new Binding();
                    //При помощи общего метода извлекаем все объекты из данной таблицы,
                    // Помещаем в лист сущностей
                    List entities = currentObj.selectAll();

                    // Проверяем, какого типа текущий DAO
                    if (currentObj instanceof ClientDao) {
                        // Чтобы выгрузить в XML с нужной иерархией, используем вспомогательный класс
                        // ClientsDAO, которому передаем List объектов, извлеченных из таблицы БД
                        ClientsDao clients = new ClientsDao();
                        clients.setClients(entities);
                        // Сериализация в XML при помощи JAXB
                        bind.marshalling(clients.getClass(), clients, currentObj.getXMLPath());
                    }

                    if (currentObj instanceof CountryDao) {
                        CountriesDao countries = new CountriesDao();
                        countries.setCountries(entities);
                        bind.marshalling(countries.getClass(), countries, currentObj.getXMLPath());
                    }

                    if (currentObj instanceof DestinationDao) {
                        DestinationsDao destinations = new DestinationsDao();
                        destinations.setDestinations(entities);
                        // Таблица Destination ссылается на таблицу Country
                        // При выгрузке в XML необходимо будет выгрузить с вложенными сущностями,
                        // Поэтому классу Destination передаем список, в котором содержится один объект -
                        // соответствующая этой записи Country
                        for (DestinationEntity entityDest : (List<DestinationEntity>) entities) {
                            int idCountry = entityDest.getIdCountry();
                            CountryDao country = new CountryDao(conn);
                            CountryEntity couEnt = new CountryEntity();
                            couEnt = country.selectByPK(idCountry, CountryEntity.columnId, couEnt);
                            entityDest.setCountryByIdCountry(couEnt);
                        }
                        bind.marshalling(destinations.getClass(), destinations, currentObj.getXMLPath());
                    }

                    if (currentObj instanceof EmployeeDao) {
                        EmployeesDao employees = new EmployeesDao();
                        employees.setEmployees(entities);
                        bind.marshalling(employees.getClass(), employees, currentObj.getXMLPath());
                    }

                    if (currentObj instanceof OrderDao) {
                        OrdersDao orders = new OrdersDao();
                        orders.setOrders(entities);
                        for (OrderEntity entityOrder : (List<OrderEntity>) entities) {
                            int idEmployee = entityOrder.getIdEmployee();
                            EmployeeDao empl = new EmployeeDao(conn);
                            EmployeeEntity emplEnt = new EmployeeEntity();
                            emplEnt = empl.selectByPK(idEmployee, EmployeeEntity.columnId, emplEnt);
                            entityOrder.setEmployeeByIdEmployee(emplEnt);

                            int idClient = entityOrder.getIdClient();
                            ClientDao client = new ClientDao(conn);
                            ClientEntity clientEnt = new ClientEntity();
                            clientEnt = client.selectByPK(idClient, ClientEntity.columnIdClient, clientEnt);
                            entityOrder.setClientByIdClient(clientEnt);

                            int idTour = entityOrder.getIdTour();
                            TourDao tour = new TourDao(conn);
                            TourEntity tourEnt = new TourEntity();
                            tourEnt = tour.selectByPK(idTour, TourEntity.columnId, tourEnt);
                            entityOrder.setCatalogueToursByIdTour(tourEnt);
                        }
                        bind.marshalling(orders.getClass(), orders, currentObj.getXMLPath());
                    }

                    if (currentObj instanceof TourDao) {
                        ToursDao tours = new ToursDao();
                        tours.setTours(entities);
                        for (TourEntity entityTour : (List<TourEntity>) entities) {
                            int idDestination = entityTour.getIdDestination();
                            DestinationDao dest = new DestinationDao(conn);
                            DestinationEntity destEnt = new DestinationEntity();
                            destEnt = dest.selectByPK(idDestination, DestinationEntity.columnId, destEnt);
                            entityTour.setDestinationPlaceByIdDestination(destEnt);
                        }
                        bind.marshalling(tours.getClass(), tours, currentObj.getXMLPath());
                    }

                }
            });
            thr.start();
            thrs.add(thr);
        }
        // Ожидаем, пока все потоки выполнятся
        for (Thread t : thrs) {
            t.join();
        }

        logger.trace("All threads stop marshalling!");
        List<Thread> threds = new ArrayList<>();

        // Очищение таблиц БД
        for (int i = 0; i < objects.size(); i++) {
            AbstractDao currentObj = objects.get(i);
            currentObj.deleteAll();
        }

        CopyOnWriteArrayList<AbstractDao> objectsForSelect = objects;
        Collections.reverse(objectsForSelect);

        // Многопоточная загрузка
        for (int i = 0; i < objectsForSelect.size(); i++) {
            final int t = i;
            Thread thr = new Thread(new Runnable() {
                public void run() {
                    //Получаем из листа ДАО таблицы
                    AbstractDao currentObj = objectsForSelect.get(t);
                    Binding bind = new Binding();

                    // Проверяем, какого типа текущий DAO
                    if (currentObj instanceof ClientDao) {
                        ClientsDao newClientsDao = (ClientsDao) bind.unmarshalling(ClientsDao.class, currentObj.getXMLPath());
                        for (ClientEntity cl : newClientsDao.getClients()) {
                            logger.trace(cl);
                            currentObj.insert(cl);
                        }
                    }

                    if (currentObj instanceof CountryDao) {
                        CountriesDao newCountriesDao = (CountriesDao) bind.unmarshalling(CountriesDao.class, currentObj.getXMLPath());
                        for (CountryEntity cl : newCountriesDao.getCountries()) {
                            logger.trace(cl);
                            currentObj.insert(cl);
                        }
                    }

                    if (currentObj instanceof DestinationDao) {
                        DestinationsDao newDestinationsDao = (DestinationsDao) bind.unmarshalling(DestinationsDao.class, currentObj.getXMLPath());
                        for (DestinationEntity cl : newDestinationsDao.getDestinations()) {
                            logger.trace(cl);
                            currentObj.insert(cl);
                        }
                    }

                    if (currentObj instanceof EmployeeDao) {
                        EmployeesDao newEmployeesDao = (EmployeesDao) bind.unmarshalling(EmployeesDao.class, currentObj.getXMLPath());
                        for (EmployeeEntity cl : newEmployeesDao.getEmployees()) {
                            logger.trace(cl);
                            currentObj.insert(cl);
                        }
                    }

                    if (currentObj instanceof OrderDao) {
                        OrdersDao newOrdersDao = (OrdersDao) bind.unmarshalling(OrdersDao.class, currentObj.getXMLPath());
                        for (OrderEntity cl : newOrdersDao.getOrders()) {
                            logger.trace(cl);
                            currentObj.insert(cl);
                        }
                    }

                    if (currentObj instanceof TourDao) {
                        ToursDao newToursDao = (ToursDao) bind.unmarshalling(ToursDao.class, currentObj.getXMLPath());
                        for (TourEntity cl : newToursDao.getTours()) {
                            logger.trace(cl);
                            currentObj.insert(cl);
                        }
                    }

                }
            });
            thr.setPriority(objectsForSelect.size() - i);
            threds.add(thr);
        }

        for (Thread t : threds) {
            t.start();
            t.join();
        }

    }
}
