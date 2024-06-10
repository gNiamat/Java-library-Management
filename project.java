package introductio;


	import java.util.ArrayList;
	import java.util.Scanner;
	import java.io.FileWriter;
	import java.io.FileReader;
	import java.io.BufferedReader;
	import java.io.IOException;

	class library {
	    private ArrayList<String> books;

	    public  library(ArrayList<String> books) {
	        this.books = books;
	    }

	    public void displayAvailableBooks() {
	        System.out.println("Books Available in library are: ");
	        for (String book : books) {
	            System.out.println(" * " + book);
	        }
	    }

	    public void borrowBook(String bookName, String regNum) {
	        if (books.contains(bookName)) {
	            System.out.println("$$ you have been issued the book " + bookName + ". Please keep it safely and return it within 30 days. $$");
	            books.remove(bookName);
	            try (FileWriter fw = new FileWriter("borrowList.txt", true)) {
	                fw.write(regNum + " ");
	            } catch (IOException e) {
	                System.out.println("An error occurred while writing to the file.");
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("$$ Sorry, currently the book is not available. You can check after some days. $$");
	        }
	    }

	    public void returnBook(String bookName) {
	        System.out.println("$$ Thanks for returning this book! Hope you enjoyed it. $$");
	        books.add(bookName);
	    }
	}

	class Student {
	    private Scanner scanner;

	    public Student() {
	        scanner = new Scanner(System.in);
	    }

	    public String requestBook() {
	        System.out.print("Enter the name of the book you want to borrow: ");
	        return scanner.nextLine();
	    }

	    public String returnBook() {
	        System.out.print("Enter the name of the book you want to return: ");
	        return scanner.nextLine();
	    }
	}

	public class project {
	    public static void main(String[] args) {
	        ArrayList<String> bookList = new ArrayList<>();
	        bookList.add("Python");
	        bookList.add("C Language");
	        bookList.add("Java");
	        bookList.add("Pandas");
	        bookList.add("Flask");
	        bookList.add("VirtualEnv");

	        library centerLibrary = new library(bookList);
	        Student student = new Student();

	        Scanner scanner = new Scanner(System.in);

	        while (true) {
	            String welcomeMsg = "======= WELCOME TO CENTER LIBRARY =======\n" +
	                    "Please Enter Your Choice:\n" +
	                    "1. Displaying list of books\n" +
	                    "2. Borrowing Book\n" +
	                    "3. Returning Book\n" +
	                    "4. Exiting The Library";
	            System.out.println(welcomeMsg);

	            int choice = scanner.nextInt();
	            scanner.nextLine(); // consume the newline character

	            switch (choice) {
	                case 1:
	                    centerLibrary.displayAvailableBooks();
	                    break;
	                case 2:
	                    System.out.print("Your registration number: ");
	                    String regNum = scanner.nextLine();

	                    try (BufferedReader br = new BufferedReader(new FileReader("borrowList.txt"))) {
	                        String borrow = br.readLine();
	                        if (borrow != null && borrow.contains(regNum)) {
	                            System.out.println("$$ You have already taken a book from the library. Please return it first to take another book. $$");
	                        } else {
	                            centerLibrary.borrowBook(student.requestBook(), regNum);
	                        }
	                    } catch (IOException e) {
	                        System.out.println("An error occurred while reading the file.");
	                        e.printStackTrace();
	                    }
	                    break;
	                case 3:
	                    System.out.print("Your registration number: ");
	                    regNum = scanner.nextLine();
	                    StringBuilder newData = new StringBuilder();

	                    try (BufferedReader br = new BufferedReader(new FileReader("borrowList.txt"))) {
	                        String line;
	                        while ((line = br.readLine()) != null) {
	                            if (!line.contains(regNum)) {
	                                newData.append(line).append(" ");
	                            }
	                        }
	                    } catch (IOException e) {
	                        System.out.println("An error occurred while reading the file.");
	                        e.printStackTrace();
	                    }

	                    try (FileWriter fw = new FileWriter("borrowList.txt")) {
	                        fw.write(newData.toString().trim());
	                    } catch (IOException e) {
	                        System.out.println("An error occurred while writing to the file.");
	                        e.printStackTrace();
	                    }

	                    centerLibrary.returnBook(student.returnBook());
	                    break;
	                case 4:
	                    System.out.println("****** Thank you for using Center Library ******");
	                    scanner.close();
	                    System.exit(0);
	                    break;
	                default:
	                    System.out.println("You entered a wrong choice.");
	            }
	        }
	    }
	}


