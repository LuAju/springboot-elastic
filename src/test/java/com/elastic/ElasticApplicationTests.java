package com.elastic;

import com.elastic.dao.BookRepository;
import com.elastic.dao.EarphoneRepository;
import com.elastic.entity.Book;
import com.elastic.entity.Earphone;
import com.elastic.dao.EarphoneDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticApplicationTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EarphoneRepository earphoneRepository;

    @Autowired
    private EarphoneDao earphoneDao;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testSave(){
//        Book book = new Book("1", "时光的故事", 23.23, new Date(), "时光是一个好朋友","罗永浩");
//        bookRepository.save(book);
        // 小米（MI）Redmi AirDots 真无线蓝牙耳机|分体式耳机 |收纳充电盒 |蓝牙5.0 |按键防触控操作
        Earphone earphone = new Earphone("1", "小米", "蓝牙耳机", "蓝牙 无线",
                "小米（MI）Redmi AirDots 真无线蓝牙耳机|分体式耳机 |收纳充电盒 |蓝牙5.0 |按键防触控操作",
                109.00, new Date(),1555);
        earphoneRepository.save(earphone);
        earphone = new Earphone("2", "华为", "蓝牙耳机", "蓝牙 无线",
                "华为 HUAWEI FreeBuds 悦享版 无线耳机智慧触控 悦享无线真无线立体声无扰通话无忧续航陶瓷白蓝牙机",
                399.00, new Date(),500);
        earphoneRepository.save(earphone);
        earphone = new Earphone("3", "华为", "手机耳机", "手机",
                "华为AM115原装耳机 三键线控带麦 半入耳式音乐耳塞畅想8/7X 荣耀V9/V10 mate20/10安卓苹果通用耳麦",
                35.00, new Date(),9999);
        earphoneRepository.save(earphone);
    }

    @Test
    public void fun33(){
        List<Earphone> earphones = earphoneDao.matchAllForPage("price","earphone","earphones",0,2);
        System.out.println(earphones);
    }

    // 查询
    @Test
    public void findAll(){
        Iterable<Book> books = bookRepository.findAll();
        Iterator<Book> bookIterator = books.iterator();
        while(bookIterator.hasNext()){
            System.out.println(bookIterator.next());
        }
    }

    @Test
    public void fun(){
        List<Book> bookByNameAndContent = bookRepository.findBookByName("时光的故事");
        for (Book book: bookByNameAndContent) {
            System.out.println(book);
        }
    }

    @Test
    public void fun2(){
        List<Book> bookByNameAndContent = bookRepository.findBookByContent("朋友");
        for (Book book: bookByNameAndContent) {
            System.out.println(book);
        }
    }

    @Test
    public void fun3(){
        List<Book> bookByNameAndContent = bookRepository.findBookByNameAndContent("时光的故事", "朋友");
        for (Book book: bookByNameAndContent) {
            System.out.println(book);
        }
    }
}
