package day3.assignment;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class MainApplication {
    private Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "employees.dat";
    private Map<Integer, Employee> map = new HashMap<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

// INITIALIZE THE MENUBAR
    public void init() {
        readDataFromFile();
        while(true) {
            System.out.println("****************************************************************");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Filter Employee (by join date yyyy-MM-dd)");
            System.out.println("7. Sort Employee");
            System.out.println("8. Employee Tenure");
            System.out.println("9. Save & Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addOrUpdateEmployee(false, 0);
                case 2 -> viewEmployees();
                case 3 -> searchEmployee(false);
                case 4 -> searchEmployee(true);//addOrUpdateEmployee(true);
                case 5 -> deleteEmployee();
                case 6 -> filterEmployee();
                case 7 -> sortEmployee();
                case 8 -> calculateTenure();
                case 9 -> {
                    writeToFile();
                    System.out.println("Data saved successfully.\n Bye bye!!!  see you again..");
                    //System.exit(0);
                    return;
                }
                default -> System.out.println("Ooops!!! You entered an invalid option.");
            }
        }
    }
//CASE 1 & 4: ADD OR UPDATE EMPLOYEE STARTS
    private void addOrUpdateEmployee(boolean flag, int id) {
        //int id = 0;
        if(!flag){
            System.out.println("Enter Employee Id: ");
             id = scanner.nextInt();
            if ( map.containsKey(id)) {
                System.out.println("Employee exists!");
                return;
            }
        }
        scanner.nextLine();
        System.out.println("Enter employee Name: ");
        String name = scanner.nextLine();
        System.out.println("Enter employee age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter employee address: ");
        String address = scanner.nextLine();
        System.out.println("Enter employee department: ");
        String department = scanner.nextLine();
        System.out.println("Enter employee designation: ");
        String designation = scanner.nextLine();
        System.out.println("Enter Joining Date (as yyyy-MM-dd): ");
        String joinDate = scanner.nextLine();
        LocalDate date = LocalDate.parse(joinDate, formatter);

        Employee employee = new Employee(id, name, age, address, department, designation, date);
        map.put(id, employee);
        System.out.println("Employee added successfully!");
    }
//CASE 2: VIEW EMPLOYEE STARTS
    private void viewEmployees() {
        if (map.isEmpty()) {
            System.out.println("No data found...Please add some employees.");
            return;
        }
        map.values().forEach(System.out::println);
    }
//CASE 3: SEARCH EMPLOYEE STARTS
    private void searchEmployee(boolean flag) {
        System.out.println(flag ? "Enter Employee id to update: " : "Enter Employee id to search: ");
        int id = scanner.nextInt();
        Employee employee = map.get(id);
        if (employee == null) {
            System.out.println("Employee not found!");
        } else {
            if(flag)
                addOrUpdateEmployee(flag, id);
            else
                System.out.println(employee);
        }
    }
//CASE 5: DELETE EMPLOYEE STARTS
    private void deleteEmployee() {
        System.out.print("Enter Employee id to delete: ");
        int id = scanner.nextInt();
        if(map.remove(id) != null) {
            System.out.println("Employee deleted successfully");
        } else {
            System.out.println("Employee not found");
        }
    }
//CASE 9: WRITE DATA TO FILE  STARTS
    private void writeToFile() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(map);
        } catch(IOException e) {
            System.out.println("Error!!! Something went wrong!");
        }
    }
// READ DATA FROM FILE  STARTS
    @SuppressWarnings("unchecked")
    private void readDataFromFile() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            map = (HashMap<Integer, Employee>) ois.readObject();
            if (map.isEmpty()) {
                System.out.println("No data found...Please add some employees.");
                return;
            } System.out.println("\n********************** Data From File **************************");
            map.values().forEach(System.out::println);
            System.out.println("****************************************************************\n");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Files is empty.");
        }
    }

//CASE 6: FILTER EMPLOYEE BY JOIN DATE  STARTS
    private void filterEmployee() {
        System.out.print("Enter Employee join date: ");
        String joinDate = scanner.next();
        LocalDate date = LocalDate.parse(joinDate, formatter);

        Map<Integer, Employee> filteredMap = map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getJoiningDate().equals(date))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        filteredMap.values().stream().forEach(System.out::println);
    }

//CASE 7: SORT EMPLOYEE BY JOIN DATE  STARTS
    private void sortEmployee() {
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        int order = Integer.parseInt(scanner.next());
        switch (order) {
            case 1 -> {
                Comparator<Employee> comparator = Comparator.comparing(Employee::getJoiningDate);
                map.values().stream().sorted(comparator).forEach(System.out::println);
            }
            case 2 -> {
                Comparator<Employee> comparator = Comparator.comparing(Employee::getJoiningDate,Comparator.reverseOrder());
                map.values().stream().sorted(comparator).forEach(System.out::println);
            }
        }
    }
//CASE 8: CALCULATE TENURE  STARTS
    private void calculateTenure() {
        System.out.println("1. All");
        System.out.println("2. By Id");
        int order = Integer.parseInt(scanner.next());
        switch (order) {
            case 1 -> {
                map.values().forEach(e ->
                    {
                        long years = ChronoUnit.YEARS.between(e.getJoiningDate(), LocalDate.now());
                        long months = ChronoUnit.MONTHS.between(e.getJoiningDate(), LocalDate.now());
                        long days = ChronoUnit.DAYS.between(e.getJoiningDate(), LocalDate.now());
                        System.out.println(e.getName() + " has been working for "
                                + years + " years." + months + " months." + days + " days.");
                        });
                }
            case 2 -> {
                System.out.println("Enter Employee Id: ");
                int id = Integer.parseInt(scanner.next());
                Optional<Employee> employee = Optional.ofNullable(map.get(id));
                if(employee.isPresent()) {
                    long years = ChronoUnit.YEARS.between(employee.get().getJoiningDate(), LocalDate.now());
                    long months = ChronoUnit.MONTHS.between(employee.get().getJoiningDate(), LocalDate.now());
                    long days = ChronoUnit.DAYS.between(employee.get().getJoiningDate(), LocalDate.now());
                    System.out.println(employee.get().getName() + " has been working for "
                            + years + " years." + months + " months." + days + " days.");
                    }
                else
                    System.out.println("Employee not found!");
                }
            }
    }
    public static void main(String[] args) {
        System.out.println("****************************************************************");
        System.out.println("************* WELCOME TO EMS ***********************************");
        System.out.println("****************************************************************");
        new MainApplication().init();
    }
}
