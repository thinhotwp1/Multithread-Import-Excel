package learn.ldt.importexcelmultithread.service;

import learn.ldt.importexcelmultithread.dto.DataCommunity;
import learn.ldt.importexcelmultithread.repository.ImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class AnyService {

    @Autowired
    ImportRepository importRepository;

    public Long importDataOneThread() {
        try {
            Long start = System.currentTimeMillis();
            List<DataCommunity> dataCommunities = readFileCSV();

            // Lưu data vào database sử dụng one thread
            System.out.println("Number of data: " + dataCommunities.size());
            System.out.println("Insert to database ...");
            importRepository.saveAllAndFlush(dataCommunities);

            Long end = System.currentTimeMillis();
            System.out.println("Time: " + (end - start) + " ms");
            return end - start;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load CSV data", ex);
        }
    }

    public Long importDataMultiThread() {
        Long start = System.currentTimeMillis();
        List<DataCommunity> dataCommunities = readFileCSV();
        System.out.println("Number of data: " + dataCommunities.size());

        // Chia nhỏ list data để import multi thread, mỗi list có 20000 object
        List<List<DataCommunity>> lists = new ArrayList<>();
        int batchSize = 20000;
        for (int i = 0; i < dataCommunities.size(); i += batchSize) {
            int end = Math.min(i + batchSize, dataCommunities.size());
            List<DataCommunity> batch = dataCommunities.subList(i, end);
            lists.add(batch);
        }

        /* Tạo số lượng thread tương ứng với số lượng list data, với server 8gb ram, chip i5 nên để 15-25 luồng
         , nên bật jconsole để xem lượng ram, thread, cpu mà application sử dụng để chọn số lượng luồng phù hợp,
         kiểm tra số lượng kết nối của database tối đa nhận được để tránh miss object khi import,
         đã xảy ra trường hợp import đa luồng và miss mất 20.000 bản ghi do quá tải connection gọi tới database
         */

        int numThreads = lists.size();
        System.out.println("Number of threads: " + numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Import  multi thread
        var ref = new Object() {
            int begin = 1;
            int done = 1;
        };
        for (List<DataCommunity> listData : lists) {
            executor.submit(() -> {
                System.out.println("Index thread begin: " + ref.begin++ + ", thread name: " + Thread.currentThread().getName());
                importRepository.saveAllAndFlush(listData);
                System.out.println("Index thread done: " + ref.done++ + ", thread name: " + Thread.currentThread().getName());
            });
        }

        // Shutdown the executor and wait for all tasks to complete
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        Long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");
        return end - start;
    }

    private List<DataCommunity> readFileCSV() {
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new ClassPathResource("Community.csv").getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CsvToBean<DataCommunity> csvToBean = new CsvToBeanBuilder(reader)
                .withType(DataCommunity.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse();
    }
}
