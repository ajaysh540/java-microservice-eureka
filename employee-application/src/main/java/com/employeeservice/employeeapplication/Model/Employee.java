package com.employeeservice.employeeapplication.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String firstName,lastName,department;
    Long contactNumber;

    public Employee(String firstName, String lastName, String department, Long contactNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", contactNumber=" + contactNumber +
                '}';
    }
}
