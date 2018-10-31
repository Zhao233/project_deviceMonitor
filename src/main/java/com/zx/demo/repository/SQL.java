//package com.zx.demo.repository;
//
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.stereotype.Repository;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@Repository
//public class SQL{
//
//    @Resource(name = "sessionFactory")
//    private SessionFactory sessionFactory;
//
//    public Session getSession() {
//        return sessionFactory.getCurrentSession();
//    }
//
//    public List queryBySql(String sql) {
//        List<Object[]> list = getSession().createSQLQuery(sql).list();
//        return list;
//    }
//
//    public int excuteBySql(String sql)
//    {
//        int result ;
//        SQLQuery query = this.getSession().createSQLQuery(sql);
//        result = query.executeUpdate();
//        return result;
//    }
//
//}
