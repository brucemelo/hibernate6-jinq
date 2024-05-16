package com.github.brucemelo.infrastructure;

import org.hibernate.StatelessSession;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;

public class AppJPAStreamProvider {

    private static JinqJPAStreamProvider jinqJPAStreamProvider;

    private static JinqJPAStreamProvider streamProvider() {
        if (jinqJPAStreamProvider == null) {
            jinqJPAStreamProvider = new JinqJPAStreamProvider(AppHibernate.getSessionFactory().getMetamodel());
        }
        return jinqJPAStreamProvider;
    }

    public static <U> JPAJinqStream<U> streamAll(StatelessSession session, Class<U> entity) {
        return streamProvider().streamAll(session.getFactory().createEntityManager(), entity);
    }

}
