# Secondo Progetto del corso "Algoritmi e Strutture Dati"

Il progetto prevede l'analisi dei tempi di esecuzione di operazioni di ricerca e inserimento in: 

* Binay Search Trees
* AVL Trees
* Red Black Trees

Il programma, lanciato dalla classe Time, prevede diverse esecuzioni con input size crescente (sulla base di una apposita funzione esponenziale) dalle quali vengono ricavati tempo medio e standard deviation. 

Per ogni albero, in particolare, sono stati implementate le operazioni di inserimento e ricerca e la relativa classe di tipo "Nodo".

La generazione di chiavi casuali durante il calcolo dei tempi è delegata alla classe RandomKeyGenerator, che si appoggia a sua volta all'algoritmo Mersenne Twister.

Il calcolo dei tempi, invece, viene effettuato mantenendo un errore relativo inferiore all'1% sulla base della risoluzione preventivamente calcolata. In particolare, lo stopwatch impiegato per l'ottenimento di un valore temporale in nanosecondi sufficientemente preciso è quello implementato dalla classe System.nanoTime di Java.

