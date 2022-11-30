package utils;

import models.modelsHibernate.forum.Forum;
import models.modelsHibernate.forum.hasMember.HasMember;
import models.modelsHibernate.message.Comment;
import models.modelsHibernate.message.Post;
import models.modelsHibernate.organisation.Company;
import models.modelsHibernate.organisation.Organisation;
import models.modelsHibernate.organisation.University;
import models.modelsHibernate.person.Person;
import models.modelsHibernate.person.knows.Knows;
import models.modelsHibernate.person.knows.KnowsPath;
import models.modelsHibernate.person.likes.LikesComment;
import models.modelsHibernate.person.study.StudyAt;
import models.modelsHibernate.person.views.Pkp_Symmetric;
import models.modelsHibernate.person.work.WorkAt;
import models.modelsHibernate.place.City;
import models.modelsHibernate.place.Continent;
import models.modelsHibernate.place.Country;
import models.modelsHibernate.place.Place;
import models.modelsHibernate.tag.Tag;
import models.modelsHibernate.tag.TagClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    private static final StandardServiceRegistry standardServiceRegistry;

    static {
        try {
            Configuration cfg = getConfiguration();
            StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
            sb.applySettings(cfg.getProperties());
            standardServiceRegistry = sb.build();

            cfg.setSessionFactoryObserver(new SessionFactoryObserver() {
                private static final long serialVersionUID = 1L;

                @Override
                public void sessionFactoryCreated(SessionFactory factory) {
                }

                @Override
                public void sessionFactoryClosed(SessionFactory factory) {
                    StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
                }
            });

            sessionFactory = cfg.buildSessionFactory(standardServiceRegistry);
        } catch (Throwable th) {
            System.err.println("Initial SessionFactory creation failed" + th);
            throw new ExceptionInInitializerError(th);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session openSession() {
        return sessionFactory.openSession();
    }

    private static Configuration getConfiguration() {

        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(Person.class);
        cfg.addAnnotatedClass(Place.class);
        cfg.addAnnotatedClass(Comment.class);
        cfg.addAnnotatedClass(WorkAt.class);
        cfg.addAnnotatedClass(StudyAt.class);
        cfg.addAnnotatedClass(Tag.class);
        cfg.addAnnotatedClass(TagClass.class);
        cfg.addAnnotatedClass(Forum.class);
        cfg.addAnnotatedClass(HasMember.class);
        cfg.addAnnotatedClass(Continent.class);
        cfg.addAnnotatedClass(Country.class);
        cfg.addAnnotatedClass(City.class);
        cfg.addAnnotatedClass(Knows.class);
        cfg.addAnnotatedClass(Organisation.class);
        cfg.addAnnotatedClass(Company.class);
        cfg.addAnnotatedClass(University.class);
        cfg.addAnnotatedClass(Post.class);
        cfg.addAnnotatedClass(LikesComment.class);
        cfg.addAnnotatedClass(KnowsPath.class);
        cfg.addAnnotatedClass(Pkp_Symmetric.class);

        cfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        cfg.setProperty("hibernate.connection.url",
                "jdbc:postgresql://pgsql.ins.hs-anhalt.de:5432/stud04_db?currentSchema=social_network");
        cfg.setProperty("hibernate.connection.username", "stud04");
        cfg.setProperty("hibernate.connection.password", "9cR8r/9L");
        cfg.setProperty("hibernate.show_sql", "false");
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
        cfg.setProperty("hibernate.hbm2ddl.auto", "update");
        cfg.setProperty("hibernate.cache.provider_class",
                "org.hibernate.cache.NoCacheProvider");
        cfg.setProperty("hibernate.current_session_context_class", "thread");
        return cfg;

    }

}
