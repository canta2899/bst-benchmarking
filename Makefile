SRC_DIR := src

OUT_DIR := out/production/SecondoProgettoASD

SRCS := $(wildcard $(SRC_DIR)/*.java)

CLS := $(SRCS:$(SRC_DIR)/%.java=$(OUT_DIR)/%.class)

LIB := lib

CP := $(SRC_DIR):$(LIB)/avalon-framework-4.1.3.jar:$(LIB)/commons-codec-1.12.jar:$(LIB)/commons-codec-1.13-javadoc.jar:$(LIB)/commons-codec-1.13-sources.jar:$(LIB)/commons-codec-1.13.jar:$(LIB)/commons-collections4-4.3.jar:$(LIB)/commons-collections4-4.4-javadoc.jar:$(LIB)/commons-collections4-4.4-sources.jar:$(LIB)/commons-collections4-4.4.jar:$(LIB)/commons-compress-1.18.jar:$(LIB)/commons-logging-1.1.jar:$(LIB)/commons-math3-3.3-javadoc.jar:$(LIB)/commons-math3-3.3-sources.jar:$(LIB)/commons-math3-3.3.jar:$(LIB)/commons-math3-3.6.1-javadoc.jar:$(LIB)/commons-math3-3.6.1-sources.jar:$(LIB)/commons-math3-3.6.1.jar:$(LIB)/curvesapi-1.06.jar:$(LIB)/log4j-1.2.13.jar:$(LIB)/logkit-1.0.1.jar:$(LIB)/ooxml-schemas-1.4.jar:$(LIB)/poi-4.1.1-javadoc.jar:$(LIB)/poi-4.1.1.jar:$(LIB)/poi-ooxml-4.1.0.jar:$(LIB)/poi-ooxml-schemas-4.1.0.jar:$(LIB)/servlet-api-2.3.jar:$(LIB)/xmlbeans-3.0.1.jar:$(LIB)/xmlbeans-3.1.0.jar

CP_EXEC := $(OUT_DIR):$(CP)

JC := javac
JCFLAGS := -d $(OUT_DIR) -cp $(CP)
JCFLAGS_EXEC := -cp $(CP_EXEC)

.SUFFIXES: .java
.PHONY: build clean execute
.DEFAULT_GOAL := build 

build: dir $(CLS)
	@echo "Compilation done"

dir:
	@mkdir -p $(OUT_DIR)

$(CLS): $(OUT_DIR)/%.class: $(SRC_DIR)/%.java
	@$(JC) $(JCFLAGS) $<

clean:
	@rm $(OUT_DIR)/*.class
	@echo "Binaries removed"

execute:
	@java $(JCFLAGS_EXEC) Time
