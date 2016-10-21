# Undo Redo Framework
A detailed documentation is found in my OODPScript.pdf
## Command Pattern (GoF)
This Framework is based on the Design Pattern of the GoF  
called Command Pattern  
UndoRedoManager is Invoker  
it establishes the possibility to track modifications
UndoRedoManager extends UndoRedoStack  
UndoRedoStack is the common infrastructure for UndoRedoManager and CompositeCommand

## Composite Pattern (GoF)
There is also a CompositeCommand for user defined sequences of commands, called makros 
and programmable multiple Commands
## Tests
Tests are provided in package de.gerdhirsch.undoredo.test for
UndoRedoManager and CompositeCommand  
and are usage examples based on a simple Calculator and Plus and Minus Commands  
Common Test Base is class UndoRedoTest