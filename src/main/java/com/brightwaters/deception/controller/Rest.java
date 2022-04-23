package com.brightwaters.deception.controller;
// package com.brightwaters.coinpricer.controller;

// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.math.BigDecimal;
// import java.net.http.HttpResponse;
// import java.sql.Date;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;

// import com.brightwaters.coinpricer.model.BalanceHistory;
// import com.brightwaters.coinpricer.model.InputTransaction;
// import com.brightwaters.coinpricer.model.Transaction;
// import com.brightwaters.coinpricer.model.UserJwt;
// import com.brightwaters.coinpricer.model.DeceptionUser;
// import com.brightwaters.coinpricer.repository.CryptoRepository;
// import com.brightwaters.coinpricer.repository.CryptoTransactionRepository;
// import com.brightwaters.coinpricer.repository.MyBatisRepository;
// import com.brightwaters.coinpricer.repository.AppUserRepository;
// import com.brightwaters.coinpricer.svc.BalanceUpdate;
// import com.brightwaters.coinpricer.svc.GetBestPrice;
// import com.brightwaters.coinpricer.svc.NanoWalletMonitor;
// import com.brightwaters.coinpricer.svc.PriceGrabberAll;
// import com.brightwaters.coinpricer.svc.ReadCsv;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.JsonMappingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.opencsv.exceptions.CsvValidationException;

// import org.apache.tomcat.util.http.parser.Authorization;
// import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
// import org.hibernate.query.Query;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class Rest {
//     @Autowired
//     PriceGrabberAll grabber;
    
//     @Autowired
//     GetBestPrice s;

//     @Autowired
//     CryptoTransactionRepository transRepos;

//     @Autowired
//     ReadCsv csvReader;

//     @Autowired
//     MyBatisRepository mybatis;

//     @Autowired
//     BalanceUpdate balance;

//     @Autowired
//     NanoWalletMonitor nano;

//     @Autowired
//     AppUserRepository userRepos;
//     private BCryptPasswordEncoder bCryptPasswordEncoder;

//     public Rest() {
//         this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
//     }

//     // @GetMapping("/historical")
//     // private void historical() {
//     //     grabber.pullHistoricalCGPrices();
//     // }

//     @GetMapping("/test")
//     private void test() {
//         BigDecimal t = s.getPrice("2021-11-23", "ERG", "2021-11-23 17:55:15");
//         System.out.println(t);
//     }

//     @GetMapping("/getAllTransactions") 
//     public List<Transaction> getAllTransactions() {
//         String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        
//         int startIndex = principal.indexOf('=') + 1;
//         int endIndex = principal.indexOf(',');
//         String verifiedUser = principal.substring(startIndex, endIndex);
        
//         // if (!verifiedUser.equals(username)) {
//         //     return null;
//         // }
        
//         ArrayList<Transaction> transactions = new ArrayList();
//         // transactions = (ArrayList<Transaction>) transRepos.findByUsername(username);
//         transactions = mybatis.getAllTransByUser(verifiedUser);
//         System.out.println(transactions.size());
//         return transactions;
//     }
    
//     @GetMapping("/crypto/balance/history/allTime/{token}") 
//     public List<BalanceHistory> getBalByuserandToken(@PathVariable("token") String token) {
//         String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        
//         int startIndex = principal.indexOf('=') + 1;
//         int endIndex = principal.indexOf(',');
//         String verifiedUser = principal.substring(startIndex, endIndex);
        
//         ArrayList<BalanceHistory> bals = new ArrayList();
//         // transactions = (ArrayList<Transaction>) transRepos.findByUsername(username);
//         bals = mybatis.getBalanceHistByUserandToken(verifiedUser, token);
//         System.out.println(bals.size());

//         return bals;
//     }

//     @PostMapping(value = "/transaction/delete", consumes = "application/json", produces = "application/json") 
//     public void deleteTransaction(@RequestBody Transaction trans) {
//         String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        
//         int startIndex = principal.indexOf('=') + 1;
//         int endIndex = principal.indexOf(',');
//         String verifiedUser = principal.substring(startIndex, endIndex);
//         String username = trans.getUsername();
//         if (!verifiedUser.equals(username)) {
//             System.out.println("Delete " + trans.getpKey() + " " + trans.getUsername());
//             LocalDate d = trans.getDate();
//             Date dt = Date.valueOf(d);
//             transRepos.delete(trans);
//             balance.createFullBalanceHistory(username, dt);
//         }
//     }

//     @PostMapping(value = "/transaction/upsert", consumes = "application/json", produces = "application/json") 
//     public Long updateOrInsertTransaction(@RequestBody Transaction trans) {
//         String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        
//         int startIndex = principal.indexOf('=') + 1;
//         int endIndex = principal.indexOf(',');
//         String verifiedUser = principal.substring(startIndex, endIndex);
//         String username = trans.getUsername();
//         if (!verifiedUser.equals(username)) {
//             System.out.println("Upsert " + trans.getpKey() + " " + trans.getUsername());
        
//             LocalDate d = trans.getDate();
//             trans.setpKey(null);
//             Date dt = Date.valueOf(d);
//             trans = transRepos.save(trans);
//             balance.createFullBalanceHistory(username, dt);
//             return trans.getpKey();
//         }
//         else {
//             return null;
//         }
        
//     }

//     @CrossOrigin(origins = "*")
//     @PostMapping(value = "/transaction/upsert/all", consumes = "application/json", produces = "application/json") 
//     public void updateOrInsertTransactionsAll(@RequestBody List<Transaction> transactions) {
//         String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        
//         int startIndex = principal.indexOf('=') + 1;
//         int endIndex = principal.indexOf(',');
//         String verifiedUser = principal.substring(startIndex, endIndex);
//         Boolean trueUser = true;
//         // ArrayList<Transaction> realTransactions = new ArrayList<>();
//         for (Transaction trans: transactions) {
//             if (!verifiedUser.equals(trans.getUsername())) {
//                 trueUser = false;
//             }
            
//         }

//         if (trueUser) {
//             System.out.println("Upsert " + transactions.get(0).getUsername());
//             String username = transactions.get(0).getUsername();
//             Date earliestDate = new Date(System.currentTimeMillis());
//             for (Transaction trans: transactions) {
//                 LocalDate d = trans.getDate();
//                 // trans.setpKey(null);
//                 Date dt = Date.valueOf(d);
//                 if (dt.compareTo(earliestDate) < 0) {
//                     earliestDate = dt;
//                 }
//                 trans = transRepos.save(trans);
//             }
//             balance.createFullBalanceHistory(username, earliestDate);
//         } 
//     }

//     // @GetMapping("/csv") 
//     // public void csv() throws CsvValidationException, FileNotFoundException, IOException {
//     //     csvReader.getCsvValues();
//     // }

//     // @GetMapping(value = "/crypto/mybatis/test/{username}")
//     // public Date testDateThing(@PathVariable("username") String username) {
//     //     Date dt = mybatis.getFirstTransactionForUser(username);
//     //     return dt;
//     // }
    
//     @GetMapping(value = "/crypto/getBalances/{date}")
//     public ArrayList<BalanceHistory> testDateThingHist(@PathVariable("date") String date) {
//         String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        
//         int startIndex = principal.indexOf('=') + 1;
//         int endIndex = principal.indexOf(',');
//         String verifiedUser = principal.substring(startIndex, endIndex);
        
//         Date dt = Date.valueOf(date);
//         ArrayList<BalanceHistory> list = mybatis.getBalanceHistoryForDate(verifiedUser, dt);
//         // System.out.println(list);
//         return list;
//     }

//     @GetMapping(value = "/crypto/getBalances/today")
//     public ArrayList<BalanceHistory> balToday() {
//         String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        
//         int startIndex = principal.indexOf('=') + 1;
//         int endIndex = principal.indexOf(',');
//         String verifiedUser = principal.substring(startIndex, endIndex);
//         LocalDate today = LocalDate.now();
//         Date tod = Date.valueOf(today);
//         ArrayList<BalanceHistory> list = mybatis.getBalanceHistoryForDate(verifiedUser, tod);
//         return list;
//     }

//     @GetMapping(value = "/crypto/getBalances/yesterday")
//     public ArrayList<BalanceHistory> balyday() {
//         String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        
//         int startIndex = principal.indexOf('=') + 1;
//         int endIndex = principal.indexOf(',');
//         String verifiedUser = principal.substring(startIndex, endIndex);
//         LocalDate ydayy = LocalDate.now().minusDays(1);
//         Date yday = Date.valueOf(ydayy);
//         ArrayList<BalanceHistory> list = mybatis.getBalanceHistoryForDate(verifiedUser, yday);
//         return list;
//     }

//     // @GetMapping(value = "/crypto/mybatis/delete/test")
//     // public void testDel() {
//     //     mybatis.deleteCoinHistoryForDateRange("Brightwaters", Date.valueOf("2021-11-23"), Date.valueOf("3021-11-23"));
//     // }

//     @GetMapping(value = "/crypto/history/date")
//     public void testHistory2() {
//         String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        
//         int startIndex = principal.indexOf('=') + 1;
//         int endIndex = principal.indexOf(',');
//         String verifiedUser = principal.substring(startIndex, endIndex);
//         balance.createFullBalanceHistory(verifiedUser, Date.valueOf("2022-1-17"));
//     }

//     @GetMapping(value = "/crypto/history")
//     public void testHistory1() {
//         String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        
//         int startIndex = principal.indexOf('=') + 1;
//         int endIndex = principal.indexOf(',');
//         String verifiedUser = principal.substring(startIndex, endIndex);
//         balance.createFullBalanceHistory(verifiedUser, null);
//     }

//     @GetMapping(value = "/crypto/price/{token}") 
//     public BigDecimal getCurrPrice(@PathVariable("token") String token) {
//         BigDecimal t = s.getCurrentPrice(token);
//         return t;
//     }

//     @GetMapping(value = "/crypto/price/all")
//     public HashMap<String, BigDecimal> getAllPricesCurr() {
//         String[] coins = {"ERG","BTC","NANO","RTM","ADA","XLM","ETH","XCH","ALGO","RVN"};
//         HashMap <String, BigDecimal> prices = new HashMap<>();
//         for (String token : coins) {
//             BigDecimal t = s.getCurrentPrice(token);
//             prices.put(token, t);
//         }

//         return prices;
//     }

    

//     @GetMapping(value = "/crypto/data/day/{token}") 
//     public BigDecimal getDataDay(@PathVariable("token") String token) {
//         BigDecimal t = s.getCurrentPrice(token);
//         return t;
//     }

//     @GetMapping(value = "/crypto/price/all/yesterday")
//     public HashMap<String, BigDecimal> getAllPricesCurrYday() {
//         String[] coins = {"ERG","BTC","NANO","RTM","ADA","XLM","ETH","XCH","ALGO","RVN"};
//         HashMap <String, BigDecimal> prices = new HashMap<>();
//         LocalDateTime yday = LocalDateTime.now().minusDays(1);
//         String dt = yday.getYear() + "-" + yday.getMonthValue() + "-" + yday.getDayOfMonth() + " " + yday.getHour() + ":" + yday.getMinute() + ":" + yday.getSecond();
//         String date = yday.getYear() + "-" + yday.getMonthValue() + "-" + yday.getDayOfMonth();
//         System.out.println(dt);
//         for (String token : coins) {
            
//             BigDecimal t = s.getPrice(date, token, dt);
//             prices.put(token, t);
//         }

//         return prices;
//     }

//     @GetMapping(value = "/crypto/price/{token}/{month}/{day}/{year}/{time}")
//     public BigDecimal getUser(@PathVariable("token") String token, @PathVariable("month") String month, @PathVariable("day") String day, @PathVariable("year") String year, @PathVariable("time") String time) {
//         // 3:20:00 PM
//         //System.out.println(time);
//         if (time.contains("AM".toUpperCase()) || time.contains("PM".toUpperCase())) {
//             String[] arrOfStr = time.split(":", 3);
//             Integer twel = Integer.parseInt(arrOfStr[0]);
//             if (time.contains("PM") && twel != 12) {
//                 Integer t = Integer.parseInt(arrOfStr[0]) + 12;
//                 arrOfStr[0] = (t).toString();
//             }
//             arrOfStr[2] = arrOfStr[2].split(" ", 2)[0];
//             time = arrOfStr[0] + ":" + arrOfStr[1] + ":" + arrOfStr[2];

//         }
//         if (year.length() < 4) {
//             year = "20" + year;
//         }
//         //System.out.println(time);
//         // 11/23/2021
//         // 2021-11-23
//         // String[] arrOfStr = date.split("/", 3);
//         // for (String s : arrOfStr) {
//         //     System.out.println(s);
//         // }
//         String date = year + "-" + month + "-" + day;
//         String dt = date + " " + time;
//         System.out.println(dt.toString());
//         BigDecimal t = s.getPrice(date, token, dt);
//         System.out.println(token + " " + t);
//         return t;
//     }

//     @GetMapping(value = "/help")
//     public String help() {
//         return "Example request https://prices.bright-waters.com/crypto/price/ADA/2021-11-23/17:55:15";
//     }

//     @PostMapping("/userSignUp")
//     public void signUpUser(@RequestBody DeceptionUser user) {
//         user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//         userRepos.save(user);
//     }

//     @GetMapping(value = "/nano")
//     public void nano() {
//         nano.pullNanoTransactions();
//     }

    
// }
