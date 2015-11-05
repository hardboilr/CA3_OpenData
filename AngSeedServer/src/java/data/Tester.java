//package data;
//
//import entity.DailyRate;
//import facades.CurrencyFacade;
//import java.util.List;
//
//public class Tester {
//    
//    public static void main(String[] args) {
//        
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu_dev");
//        EntityManager em = emf.createEntityManager();
//        
//        Currency cur1 = new Currency("USD", "US dollars");
//        Currency cur2 = new Currency("DKK", "Danske kroner");
//        Currency cur3 = new Currency("EUR", "Euroen");
//        Currency cur4 = new Currency("SVE", "Svenske dadler");
//        
//        String date1 = "2015-11-01";
//        String date2 = "2015-11-02";
//        String date3 = "2015-11-03";
//        
//        java.sql.Date sqlDate1 = java.sql.Date.valueOf(date1);
//        java.sql.Date sqlDate2 = java.sql.Date.valueOf(date2);
//        java.sql.Date sqlDate3 = java.sql.Date.valueOf(date3);
//        
//        DailyRate dailyRate1 = new DailyRate(sqlDate1, cur1, 650f);
//        DailyRate dailyRate2 = new DailyRate(sqlDate1, cur2, 100f);
//        DailyRate dailyRate3 = new DailyRate(sqlDate1, cur3, 745f);
//        DailyRate dailyRate4 = new DailyRate(sqlDate1, cur4, 80f);
//        
//        em.getTransaction().begin();
//        em.persist(cur1);
//        em.persist(cur2);
//        em.persist(cur3);
//        em.persist(cur4);
//        em.persist(dailyRate1);
//        em.persist(dailyRate2);
//        em.persist(dailyRate3);
//        em.persist(dailyRate4);
//        em.getTransaction().commit();
//        em.close();
//        
//        CurrencyFacade cf = new CurrencyFacade();
//        List<DailyRate> rates = cf.getDailyRates();
//        for(DailyRate rate : rates){
//            System.out.println(rate.getCurrency().getName());
//        }
//    }
//}
