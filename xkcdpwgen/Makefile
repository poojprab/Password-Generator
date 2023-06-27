# Define the compiler and its flags
JAVAC = javac
JFLAGS = -g

# Define the source and output directories
SRCDIR = src
BINDIR = bin

# Find all Java source files
SOURCES := $(shell find $(SRCDIR) -name "*.java")

# Transform Java source files into class files
CLASSES := $(SOURCES:$(SRCDIR)/%.java=$(BINDIR)/%.class)

# Default target to compile the Java source files
default: $(CLASSES)

# Compile Java source files into class files
$(BINDIR)/%.class: $(SRCDIR)/%.java
	@mkdir -p $(BINDIR)
	$(JAVAC) $(JFLAGS) -cp $(BINDIR) -d $(BINDIR) $<
