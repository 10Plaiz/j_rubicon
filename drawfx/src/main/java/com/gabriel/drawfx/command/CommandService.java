package com.gabriel.drawfx.command;

import java.util.Stack;
public class CommandService {
    static Stack<Command> undoStack = new Stack<Command>();
    static Stack<Command> redoStack = new Stack<Command>();

    public static void ExecuteCommand(Command command) {
        System.out.println("Executing command: " + command.getClass().getSimpleName());
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        printStacks();
    }

    public static void undo() {
        System.out.println("Undo called - Stack size: " + undoStack.size());
        if (undoStack.empty())
            return;
        Command command = undoStack.pop();
        System.out.println("Undoing command: " + command.getClass().getSimpleName());
        command.undo();
        redoStack.push(command);
        printStacks();
    }

    public static void redo() {
        System.out.println("Redo called - Stack size: " + redoStack.size());
        if (redoStack.empty())
            return;
        Command command = redoStack.pop();
        System.out.println("Redoing command: " + command.getClass().getSimpleName());
        command.execute();
        undoStack.push(command);
        printStacks();
    }
    
    // Debug method to print current stack states
    public static void printStacks() {
        System.out.println("=== COMMAND STACKS DEBUG ===");
        System.out.println("Undo Stack Size: " + undoStack.size());
        System.out.println("Redo Stack Size: " + redoStack.size());
        
        System.out.println("\nUndo Stack Contents:");
        for (int i = undoStack.size() - 1; i >= 0; i--) {
            Command cmd = undoStack.get(i);
            System.out.println("  [" + i + "] " + cmd.getClass().getSimpleName());
        }
        
        System.out.println("\nRedo Stack Contents:");
        for (int i = redoStack.size() - 1; i >= 0; i--) {
            Command cmd = redoStack.get(i);
            System.out.println("  [" + i + "] " + cmd.getClass().getSimpleName());
        }
        System.out.println("============================\n");
    }

    public static int getUndoStackSize() {
        return undoStack.size();
    }

    public static int getRedoStackSize() {
        return redoStack.size();
    }
}