# text-editor

A console-based text editor built with Java, utilizing a gap buffer for efficient text manipulation and JNA (Java Native Access) for terminal control on Windows. Aiming for a vim-style finished product.

## Features

- **In-terminal Usability**: Designed to be used within the terminal for quick file manipulation.
- **Efficient in-terminal usability**: File contents are stored in a gap buffer to minimize overhead and provide a lightweight, responsive experience up to ~1GB file sizes.
- **Windows Support**: Built for Windows using JNA, planning on adding support for other platforms

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Windows (for now)
- Maven (for project management)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/console-text-editor.git

2. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the project with:
   ```
   mvn exec:java -Dexec.mainClass=com.willcb.projects.texteditor.TerminalTextEditor -Dexec.args="<FILE PATH HERE>"

   mvn exec:java -Dexec.mainClass=com.willcb.projects.texteditor.TerminalTextEditor -Dexec.args="C:\Users\Willb\Desktop\text-editor\test.txt"