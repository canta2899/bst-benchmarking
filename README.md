# Binary Search Trees Benchmarking

This repository provides a software developed as one of the projects assigned during the Algorithm and Data Structures course, held by professor [Carla Piazza](http://users.dimi.uniud.it/~carla.piazza/) and [Gabriele Puppis](http://users.dimi.uniud.it/~gabriele.puppis/) at the University of Udine. The aim was providing a tool that benchmarks three data structures: 

* Binay Search Trees
* AVL Trees
* Red Black Trees

## Introduction

The software was developed using the **Java** programming language and runs operations of research and insertion over the three kinds of trees with random inputs of various dimensionality according to the following function: 

$$\large{n \\; = \\; 1.116^{i} \\; \cdot \\; 10}$$

Moreover, we provided a **functional** implementation for all the algorithms and performed a [**JVM Warmup**](https://www.baeldung.com/java-jvm-warmup) (that pushes the classes into the Java Virtual Machine's cache to get constant and fast access at runtime) in order to obtain accurate results

For each dimensionality, the **amortized time** was computed by running the operations multiple times and the CPU clock resolution was taken into account in order to ensure a relative error below 1%. 

The random input was obtained by using the **Mersenne Twister** algorithm. 

## Results

The results are saved inside the file `results/results.csv`, that contains, for each row, the input dimensionality and the execution time for the AVL, Red Black and Binary Search trees.

A specific script, written in **R**, is then able to use the data to plot graphs that show how the execution time increases with input dimensionality in linear and logaritmic scale. 

An accurate execution proves that for random inputs (already balanced) the Binary Search Trees scored the best execution times, because no balancing operation is required. Of course, for unbalanced inputs a completely different behaviour would be observed.

## How to use

In order to run the code on your machine and plot the graphs you'll need to install **Java JDK** and **R**. Once installed you can compile the code by running

```Makefile

make build

```

Then, run the benchmark by executing

```Makefile

make execute

```

This will take an **hour** (or more) and will produce a **csv** file inside the `results` folder (that will be created if it doesn't already exists).

Once the execution is completed, you can run

```Makefile

make graphs

```

This will launch the R script and graphs computed from the previously obtained data will be saved as PNG files inside the `results` folder.
