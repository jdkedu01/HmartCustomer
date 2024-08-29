package hmart.customer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Customer {
    @Id //JPA에서 인식
    String id;
    String name;
    Integer age; //JPA에서 Nullvalue 고려
    String levels;
    String job;
    Integer point; //JPA만 Nullvalue 고려
     // 가능한 테이블명과 속성명을 영어로 할 것.
    // 한글로 한 경우 여기서 @Table, @Column으로 match시켜야 함
    // JPA repository 참고
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLevels() {
        return levels;
    }
    public void setLevels(String levels) {
        this.levels = levels;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getPoint() {
        return point;
    }
    public void setPoint(Integer point) {
        this.point = point;
    }
}
