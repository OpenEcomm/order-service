package com.bigtree.orders.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public class BaseEntity{
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "date_created")
	@DateTimeFormat(pattern = "dd-mm-yyyy HH:mm:ss")
	private LocalDateTime createdOn;

	@Column(name = "date_updated")
	@DateTimeFormat(pattern = "dd-mm-yyyy HH:mm:ss")
	private LocalDateTime updatedOn;

	@Column(name = "date_deleted")
	@DateTimeFormat(pattern = "dd-mm-yyyy HH:mm:ss")
	private LocalDateTime deletedOn;

	@Column(name = "deleted")
	private boolean deleted;

	@PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }
 
    @PreUpdate
    public void preUpdate() {
        updatedOn = LocalDateTime.now();
	}
	
}
