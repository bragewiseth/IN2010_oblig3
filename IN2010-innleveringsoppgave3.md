# Oblig 3

Man kjører programmet med de relative filbanene til `movies_tsv` og `actors_tsv`
som argumenter, i den rekkefølgen.  
Med testfilene i samme mappe som som javafilen kjøres programmet slik:

~~~
java innlevering3 movies.tsv actors.tsv
~~~

> Oppgave 1

Grafen er representert med nabolister. Kjøretid er $\mathcal{O}(|V|\cdot |m| + |M|)$ for innlesing.  
$|M|$ er antall filmer  
$|V|$ er antall skuespillere  
$|m|$ er antall filmer en skuespiller har spillt i  
$|v|$ er antall skuespillere i en film


> Oppgave 2 

Oppgaven er løst med `BFS`. Algoritmen terminerer når målet er oppdaget, det vil si at algoritmen finner første korteste vei.  
Verste tilfellet er $\mathcal{O}(|E|)$  
$|E|$ er $|M|\cdot |v| \cdot |m|$

> Oppgave 3 

Oppgaven er løst med `Dijkstra`. Algoritmen terminerer når målet er poppet av køen. Det kan finnes stier med lavere total vekt som forblir uoppdaget. Dette er usannsynlig siden de aller færreste filmene er ratet over $8.0$.  
Verste tilfellet er $\mathcal{O}\left(|E|\cdot \log(|V|)\right)$
> Oppgave 4

Oppgaven er løst med `DFS`  
Kjøretiden er $\mathcal{O}(|V|+|E|)$

___

På min maskin er kjøretiden til programmet på ca. $2$ sekunder.

+ **$500$ ms** | De $500$ første millisekundene blir brukt til å bygge grafen 
+ **$100$ ms** | Kjøretid på oppgave 2 er neglisjerbar liten
+ **$1000$ ms** | Det neste sekundet blir brukt på oppgave 3 
+ **$500$ ms** | $500$ millisekunder blir brukt på oppgave 4

Disse resultatne er ikke overaskende siden de stemmer overens med forventet kjøretid.
