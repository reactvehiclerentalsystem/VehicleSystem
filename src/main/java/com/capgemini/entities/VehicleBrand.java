
package com.capgemini.entities;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import lombok.Data;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "brand_id", scope = Integer.class)
public class VehicleBrand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brand_id;
	private String brand_name;

	@OneToMany(mappedBy = "vehicleBrand")
	@JsonManagedReference
	private List<Vehicle> vehicle = new ArrayList<>();

}
