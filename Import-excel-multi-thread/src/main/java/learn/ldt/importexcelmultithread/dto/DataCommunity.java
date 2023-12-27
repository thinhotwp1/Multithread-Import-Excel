package learn.ldt.importexcelmultithread.dto;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_data_community")
public class DataCommunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CsvBindByName(column = "course_id" )
    private String courseId;
    @CsvBindByName(column = "userid_DI" )
    private String userIdDI;
    @CsvBindByName(column = "registered" )
    private String registered;
    @CsvBindByName(column = "viewed" )
    private String viewed;
    @CsvBindByName(column = "explored" )
    private String explored;
    @CsvBindByName(column = "certified" )
    private String certified;
    @CsvBindByName(column = "final_cc_cname_DI" )
    private String finalCcnCameDI;
    @CsvBindByName(column = "LoE_DI" )
    private String LoEDI;
    @CsvBindByName(column = "YoB" )
    private String YoB;
    @CsvBindByName(column = "gender" )
    private String gender;
    @CsvBindByName(column = "gender1" )
    private String gender1;
    @CsvBindByName(column = "gender2" )
    private String gender2;
    @CsvBindByName(column = "gender3" )
    private String gender3;
    @CsvBindByName(column = "gender4" )
    private String gender4;
    @CsvBindByName(column = "gender5" )
    private String gender5;
    @CsvBindByName(column = "gender6" )
    private String gender6;
    @CsvBindByName(column = "gender7" )
    private String gender7;
    @CsvBindByName(column = "gender8" )
    private String gender8;
    @CsvBindByName(column = "gender9" )
    private String gender9;
    @CsvBindByName(column = "gender10" )
    private String gender10;
}
