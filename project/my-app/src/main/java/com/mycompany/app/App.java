package com.mycompany.app;
import com.google.gson.*;
import com.mycompany.app.model.Employee;
import com.mycompany.app.model.PensionPlan;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<Employee> listEmp = new ArrayList<>();
        listEmp.add(new Employee(1, "Daniel", "Agar", LocalDate.parse("2018-01-17"), 105945.50));
        listEmp.add(new Employee(2, "Bernard", "Shaw", LocalDate.parse("2019-04-03"), 197750.00));
        listEmp.add(new Employee(3, "Carly", "Agar", LocalDate.parse("2014-05-16"), 842000.75));
        listEmp.add(new Employee(4, "Wesley", "Schneider", LocalDate.parse("2019-05-02"), 74500.00));

        List<PensionPlan> pensionPlans = new ArrayList<>();
        pensionPlans.add(new PensionPlan(1, LocalDate.parse("2023-01-17"), 100.00));
        pensionPlans.add(new PensionPlan(3, LocalDate.parse("2019-11-04"), 1555.50));

        //Task 1:
        List<Employee> sortedEmployees = listEmp.stream()
                .sorted(Comparator.comparing(Employee::getLastName).thenComparing(Employee::getYearlySalary).reversed())
                .collect(Collectors.toList());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();

      //  System.out.println(gson.toJson(sortedEmployees));

        // Task 2:

        //List<PensionPlan> pensionPlans = new ArrayList<>();
       // pensionPlans.add(new PensionPlan(1, LocalDate.parse("2023-01-17"), 100.00));
       // pensionPlans.add(new PensionPlan(3, LocalDate.parse("2019-11-04"), 1555.50));
        LocalDate firstDateOfNextMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        LocalDate lastDateOfNextMonth = firstDateOfNextMonth.withDayOfMonth(firstDateOfNextMonth.lengthOfMonth());

        List<Employee> nextPension = listEmp.stream()
                .filter(employee -> pensionPlans.stream().noneMatch(p -> p.getPlanReferenceNumber() == employee.getEmployeeId()))
                .filter(employee -> employee.getEmploymentDate().isBefore(lastDateOfNextMonth) || employee.getEmploymentDate().isEqual(lastDateOfNextMonth))
                .collect(Collectors.toList());
        System.out.println(gson.toJson(nextPension));
    }
}
