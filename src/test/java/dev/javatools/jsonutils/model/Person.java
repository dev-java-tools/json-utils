package dev.javatools.jsonutils.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Person {
    private String name;
    private int age;
    private String dateOfBirth;
    private List<Person> friends;
    private List<Address> associatedAddresses;
    private Address primaryAddress;
}
