package com.learning.springboot.checklistapi.entity;
import lombok.Data;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String guid;
}
