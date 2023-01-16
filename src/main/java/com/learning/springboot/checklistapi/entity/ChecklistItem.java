package com.learning.springboot.checklistapi.entity;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalTime;

@Data
@Entity(name = "ChecklistItem")
@Table(indexes = { @Index(name = "IDX_GUID_CK_IT", columnList = "guid") })
public class ChecklistItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistId;

    private String description;

    private Boolean idCompleted;

    private LocalTime deadLine;

    private LocalTime postDate;

    @ManyToOne
    private Category categoryEntity;
}
