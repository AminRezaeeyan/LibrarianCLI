package com.librarian.CLI;

import com.librarian.services.*;
import com.librarian.exceptions.*;

import java.sql.SQLException;

public class ServiceDispatcher {
    private static ServiceDispatcher instance;

    private final LibraryService libraryService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ResourceService resourceService;

    private ServiceDispatcher() {
        userService = UserService.getInstance();
        libraryService = LibraryService.getInstance();
        categoryService = CategoryService.getInstance();
        resourceService = ResourceService.getInstance();
    }

    public static ServiceDispatcher getInstance() {
        if (instance == null) instance = new ServiceDispatcher();
        return instance;
    }

    public String dispatchCommand(String command, String[] args) throws LibraryServiceException, SQLException {
        switch (command) {
            case "add-library" -> {
                if (args.length != 5) throw new InvalidInputException();
                libraryService.addLibrary(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]));
            }
            case "update-library" -> {
                if (args.length != 5) throw new InvalidInputException();
                libraryService.updateLibrary(args[0], args[1], args[2], args[3], args[4]);
            }
            case "remove-library" -> {
                if (args.length != 1) throw new InvalidInputException();
                libraryService.removeLibrary(args[0]);
            }
            case "add-category" -> {
                if (args.length != 3) throw new InvalidInputException();
                categoryService.addCategory(args[0], args[1], args[2]);
            }
            case "update-category" -> {
                if (args.length != 3) throw new InvalidInputException();
                categoryService.updateCategory(args[0], args[1], args[2]);
            }
            case "remove-category" -> {
                if (args.length != 1) throw new InvalidInputException();
                categoryService.removeCategory(args[0]);
            }
            case "add-staff" -> {
                if (args.length != 7) throw new InvalidInputException();
                userService.addStaff(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            }
            case "add-professor" -> {
                if (args.length != 7) throw new InvalidInputException();
                userService.addProfessor(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            }
            case "add-graduate-student" -> {
                if (args.length != 7) throw new InvalidInputException();
                userService.addGraduateStudent(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            }
            case "add-undergraduate-student" -> {
                if (args.length != 6) throw new InvalidInputException();
                userService.addUndergraduateStudent(args[0], args[1], args[2], args[3], args[4], args[5]);
            }
            case "update-staff" -> {
                if (args.length != 7) throw new InvalidInputException();
                userService.updateStaff(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            }
            case "update-professor" -> {
                if (args.length != 7) throw new InvalidInputException();
                userService.updateProfessor(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            }
            case "update-graduate-student" -> {
                if (args.length != 7) throw new InvalidInputException();
                userService.updateGraduateStudent(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            }
            case "update-undergraduate-student" -> {
                if (args.length != 6) throw new InvalidInputException();
                userService.updateUndergraduateStudent(args[0], args[1], args[2], args[3], args[4], args[5]);
            }
            case "remove-user" -> {
                if (args.length != 1) throw new InvalidInputException();
                userService.removeUser(args[0]);
            }
            case "add-borrowable-book" -> {
                if (args.length != 8) throw new InvalidInputException();
                resourceService.addBorrowableBook(args[0], args[1], args[2], args[3], args[4], args[5], args[6], Integer.parseInt(args[7]));
            }
            case "add-thesis" -> {
                if (args.length != 8) throw new InvalidInputException();
                resourceService.addThesis(args[0], args[1], args[2], args[3], args[4], args[5], args[6], Integer.parseInt(args[7]));
            }
            case "add-purchasable-book" -> {
                if (args.length != 10) throw new InvalidInputException();
                resourceService.addPurchasableBook(args[0], args[1], args[2], args[3], args[4], args[5], args[6], Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]));
            }
            case "add-treasure-book" -> {
                if (args.length != 8) throw new InvalidInputException();
                resourceService.addTreasureBook(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            }
            case "update-borrowable-book" -> {
                if (args.length != 8) throw new InvalidInputException();
                resourceService.updateBorrowableBook(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            }
            case "update-thesis" -> {
                if (args.length != 8) throw new InvalidInputException();
                resourceService.updateThesis(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            }
            case "update-purchasable-book" -> {
                if (args.length != 10) throw new InvalidInputException();
                resourceService.updatePurchasableBook(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9]);
            }
            case "update-treasure-book" -> {
                if (args.length != 8) throw new InvalidInputException();
                resourceService.updateTreasureBook(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            }
            case "remove-resource" -> {
                if (args.length != 2) throw new InvalidInputException();
                resourceService.removeResource(args[0], args[1]);
            }
            case "buy" -> {
                if (args.length != 3) throw new InvalidInputException();
                libraryService.buyResource(args[0], args[1], args[2]);
            }
            case "read" -> {
                if (args.length != 5) throw new InvalidInputException();
                libraryService.readResource(args[0], args[1], args[2], args[3], args[4]);
            }
            case "borrow" -> {
                if (args.length != 4) throw new InvalidInputException();
                libraryService.borrowResource(args[0], args[1], args[2], args[3]);
            }
            case "return" -> {
                if (args.length != 4) throw new InvalidInputException();
                return libraryService.returnResource(args[0], args[1], args[2], args[3]);
            }
            case "report-most-borrowed" -> {
                if (args.length != 1) throw new InvalidInputException();
                return resourceService.reportMostBorrowed(args[0]);
            }
            case "report-best-seller" -> {
                if (args.length != 1) throw new InvalidInputException();
                return resourceService.reportBestSeller(args[0]);
            }
            case "report-penalties-sum" -> {
                return userService.reportPenaltiesSum() + "";
            }
            case "search-users" -> {
                if (args.length != 1) throw new InvalidInputException();
                return userService.searchUsers(args[0]);
            }
            case "search-resources" -> {
                if (args.length != 2) throw new InvalidInputException();
                return resourceService.searchResources(args[0], args[1]);
            }
            default -> throw new InvalidInputException();
        }
        return "success";
    }
}
