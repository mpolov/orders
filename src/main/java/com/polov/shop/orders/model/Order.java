package com.polov.shop.orders.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "o")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<OrderItem> items;

}
