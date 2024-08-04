package com.librarian.CLI;

import com.librarian.exceptions.LibraryServiceException;
import com.librarian.utils.ColorPrintUtil;
import com.librarian.utils.ColorPrintUtil.Color;

import java.sql.SQLException;
import java.util.Scanner;

public class CommandLineInterface {
    private static CommandLineInterface instance;

    private CommandLineInterface() {
    }

    public static CommandLineInterface getInstance() {
        if (instance == null) instance = new CommandLineInterface();
        return instance;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            ColorPrintUtil.setColor(Color.CYAN);
            System.out.print("> ");
            ColorPrintUtil.resetColor();

            String cmdLine = scanner.nextLine().trim();
            if (cmdLine.isEmpty()) continue;
            if (cmdLine.equalsIgnoreCase("exit")) break;

            String[] splitCommand = cmdLine.split("#");
            String command = splitCommand[0];
            String[] args = (splitCommand.length > 1) ? (splitCommand[1].split("\\|")) : new String[0];

            try {
                String response = ServiceDispatcher.getInstance().dispatchCommand(command, args);
                ColorPrintUtil.setColor(Color.GREEN);
                System.out.println(response);
            } catch (LibraryServiceException | SQLException e) {
                ColorPrintUtil.setColor(Color.RED);
                System.out.println("!!! " + e.getMessage());
            } catch (Exception e){
                ColorPrintUtil.setColor(Color.RED);
                System.out.println("!!! Something went wrong!");
                System.out.println(e.getMessage());
            }finally {
                ColorPrintUtil.resetColor();
            }
        }
        scanner.close();
    }
}
