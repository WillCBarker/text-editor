# text-editor

A console-based text editor built with Java, utilizing a gap buffer for efficient text manipulation and JNA (Java Native Access) for terminal control on Windows. Aiming for a vim-style finished product.

## Features

- **Gap Buffer Implementation**: Efficiently manages text storage and manipulation.
- **Command Mode**: Use the Escape key to enter command mode for saving files, quitting, etc.
- **Keyboard Input Handling**: Supports keyboard inputs for text navigation and editing.
- **Cross-Platform Compatibility**: Built for Windows using JNA, with potential for expansion to other platforms.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven (for project management)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/console-text-editor.git

2. Build the project using Maven:
   ```mvn clean install```

3. Run the project with:
   ```mvn exec:java -Dexec.mainClass=com.willcb.projects.texteditor.TerminalTextEditor```
