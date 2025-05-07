import java.util.*;
public class Employee {

  // EMPLOYEE CLASS 
    static class EmpRecord {
        int id;
        String name;
        String department;
        String joinDate;

        public EmpRecord(int id, String name, String department, String joinDate) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.joinDate = joinDate;
        }

        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Dept: " + department + ", Joined: " + joinDate;
        }
    }

    //  LINKED LIST NODE FOR EMPLOYEES 
    static class Node {
        EmpRecord data;
        Node next;

        public Node(EmpRecord data) {
            this.data = data;
        }
    }

    // LINKED LIST IMPLEMENTATION 
    static class EmployeeList {
        Node head;

        public void add(EmpRecord emp) {
            Node newNode = new Node(emp);
            newNode.next = head;
            head = newNode;
        }

        public void delete(int id) {
            Node temp = head, prev = null;
            while (temp != null && temp.data.id != id) {
                prev = temp;
                temp = temp.next;
            }
            if (temp != null) {
                if (prev == null) head = temp.next;
                else prev.next = temp.next;
                System.out.println("Employee with ID " + id + " deleted.");
            } else {
                System.out.println("Employee not found.");
            }
        }

        public void display() {
            Node current = head;
            if (current == null) {
                System.out.println("No employees to display.");
                return;
            }
            while (current != null) {
                System.out.println(current.data);
                current = current.next;
            }
        }
    }

    // BST NODE 
    static class BSTNode {
        EmpRecord employee;
        BSTNode left, right;

        public BSTNode(EmpRecord employee) {
            this.employee = employee;
        }
    }

    // BINARY SEARCH TREE FOR EMPLOYEE SEARCH 
    static class EmployeeBST {
        BSTNode root;

        public void insert(EmpRecord emp) {
            root = insertRec(root, emp);
        }

        private BSTNode insertRec(BSTNode root, EmpRecord emp) {
            if (root == null) return new BSTNode(emp);
            if (emp.id < root.employee.id) root.left = insertRec(root.left, emp);
            else root.right = insertRec(root.right, emp);
            return root;
        }

        public EmpRecord searchById(int id) {
            return searchRec(root, id);
        }

        private EmpRecord searchRec(BSTNode root, int id) {
            if (root == null) return null;
            if (root.employee.id == id) return root.employee;
            return id < root.employee.id ? searchRec(root.left, id) : searchRec(root.right, id);
        }
    }

    // ========== LEAVE REQUEST 
    static class LeaveRequest {
        int employeeId;
        String reason;

        public LeaveRequest(int employeeId, String reason) {
            this.employeeId = employeeId;
            this.reason = reason;
        }

        public String toString() {
            return "Employee ID: " + employeeId + ", Reason: " + reason;
        }
    }

    // LEAVE REQUEST QUEUE 
    static class LeaveQueue {
        Queue<LeaveRequest> queue = new LinkedList<>();

        public void addRequest(LeaveRequest request) {
            queue.add(request);
        }

        public LeaveRequest processNext() {
            return queue.poll();
        }

        public void showAllRequests() {
            if (queue.isEmpty()) {
                System.out.println("No pending leave requests.");
                return;
            }
            for (LeaveRequest r : queue) System.out.println(r);
        }
    }

    // MAIN MENU 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeList empList = new EmployeeList();
        EmployeeBST empBST = new EmployeeBST();
        LeaveQueue leaveQueue = new LeaveQueue();

        while (true) {
            System.out.println("\n====== Employee Management Menu ======");
            System.out.println("1. Add Employee");
            System.out.println("2. Delete Employee");
            System.out.println("3. Display All Employees");
            System.out.println("4. Search Employee by ID");
            System.out.println("5. Add Leave Request");
            System.out.println("6. Process Leave Request");
            System.out.println("7. Show Leave Queue");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Department: "); String dept = sc.nextLine();
                    System.out.print("Join Date (YYYY-MM-DD): "); String date = sc.nextLine();
                    EmpRecord emp = new EmpRecord(id, name, dept, date);
                    empList.add(emp);
                    empBST.insert(emp);
                    System.out.println("Employee added.");
                    break;

                case 2:
                    System.out.print("Enter Employee ID to delete: ");
                    int delId = sc.nextInt();
                    empList.delete(delId);
                    
                    break;

                case 3:
                    empList.display();
                    break;

                case 4:
                    System.out.print("Enter Employee ID to search: ");
                    int sid = sc.nextInt();
                    EmpRecord found = empBST.searchById(sid);
                    System.out.println(found != null ? found : "Employee not found.");
                    break;

                case 5:
                    System.out.print("Employee ID for Leave Request: ");
                    int lid = sc.nextInt(); sc.nextLine();
                    System.out.print("Leave Reason: ");
                    String reason = sc.nextLine();
                    leaveQueue.addRequest(new LeaveRequest(lid, reason));
                    System.out.println("Leave request added.");
                    break;

                case 6:
                    LeaveRequest req = leaveQueue.processNext();
                    System.out.println(req != null ? "Processed Leave: " + req : "No leave requests in queue.");
                    break;

                case 7:
                    leaveQueue.showAllRequests();
                    break;

                case 8:
                    System.out.println("Exiting system. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
 }
}
}