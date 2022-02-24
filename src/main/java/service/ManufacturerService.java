package service;

import model.Manufacturer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ManufacturerService {
    private SessionFactory sessionFactory;

    public ManufacturerService(SessionFactory connection) {
        this.sessionFactory = connection;
    }

    public void saveManufacturer(Manufacturer manufacturer) {
        Session session = sessionFactory.openSession();
        session.save(manufacturer);
        session.close();
    }

    public Manufacturer findById(Long id) {
        Session session = this.sessionFactory.openSession();
        return session.find(Manufacturer.class, id);
    }
}
