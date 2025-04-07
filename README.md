# Progetto di Tecnologie Software per il Web (Classe Resto 1 a.a. 2024/2025)

#### Docente: Rita Francese

#### Studenti: Iaquino Sabato 0512123029, Mancusi Carlo 0512120116 

## Checklist per la preparazione del progetto

Per superare la discussione del progetto devono essere seguite nel dettaglio le seguenti linee guida. Il progetto è di gruppo (max quattro persone). E' possibile suddividersi i compiti durante lo sviluppo, ma nel momento della discussione tutti devono essere a conoscenza dei contenuti e delle funzionalità dell'intero progetto. La Base di Dati ha un ruolo molto importante.

Il sito deve essere di commercio elettronico: il cliente deve poter inserire prodotti nel carrello, variarne la quantità. Una volta confermato l'ordine deve essere possibile visualizzare l'ordine nell'elenco degli ordini effettuati e va svuotato il carrello. 

Va prevista la figura dell'amministratore e delle pagine a lui dedicate, accessibili solo dopo autenticazione (vedere lezione su Security). Usare autenticazione programmata. L'AMMINISTRATORE DEVE POTER INSERIRE, MODIFICARE, VISUALIZZARE E CANCELLARE ELEMENTI DEL CATALOGO, VISUALIZZARE GLI ORDINI COMPLESSIVI, DALLA DATA ALLA DATA E PER CLIENTE.

REQUISITO MOLTO IMPORTANTE: IL DATABASE DEVE ESSERE STRUTTURATO IN MODO TALE CHE SE VENGONO MODIFICATI IL PREZZO O L'IVA DI UN PRODOTTO ACQUISTATO SUCCESSIVAMENTE ALL'ACQUISTO L'ORDINE DEL CLIENTE MANTIENE I DATI CORRETTI. Va mantenuto il vincolo d'integrità referenziale: se l'amministratore cancella un prodotto non deve scomparire dagli ordini effettuati. Prevenire SQL injection. Usare i filtri

- Il sito deve essere responsive;
- Il sito deve girare su Tomcat direttamente;
- Usare il modello MVC;
- Creare almeno due package: uno per le servlet, chiamato Control, ed uno per il Model, chiamato Model.
- Il Model deve contenere i bean, il carrello;
- Il codice HTML viene creato esclusivamente dalle JSP. JSP e HTML che formano il view
- Usare il datasource o drivemanager per connettersi al DB (esempio storage). Se si usa DriveManager utilizzare anche Connection Pool.
- I form sono controllati con javascript. Il form viene inviato al server solo se corretto. Usare le espressioni regolari per validare i campi del form. Mettere il "focus" sul campo in cui l'utente sta scrivendo. Visualizzare le istruzioni di compilazione di ogni campo di input nel placeholder. Fornire i messaggi di errore quando l'utente preme il submit (evitare gli alert).
- Usare AJAX per scambiare piccole informazioni con il server (in formato JSON). Almeno: utilizzare ajax per la barra di ricerca (come in google suggest) e controllare durante la registrazione che l'email non è già presente nel database
- Gestire le sessioni per memorizzare il carrello. Si salva l'ordine nel DB dopo l'acquisto.
- DARE conferma all'utente - registrazione effettuata con successo, prodotto inserito con successo"
- quando amministratore cancella chiedere conferma prima di eseguie
- Gestire pagine di errore. il server non deve perdere il controllo in caso di errore (risorsa non trovata, server indisponibile, mancanza di permessi per l'accesso)
- Usare i fragment (con include) nelle pagine JSP per creare header, footer e menu (esempio progetto negozio, L09 bis, JSP).

## Prima consegna - Project Proposal (Data di assegno 25/02/2025 - Consegna entro 04/03/2025)
Realizzare la proposta del sito web di eCommerce: il progetto deve riguardare la realizzazione di un sito web dinamico, responsive, con persistenza dei dati, che includere l’utilizzo di tutte le tecnologie spiegate durante il corso.

Elementi da sviluppare:
1. Obiettivo del progetto: scegliere un progetto su una tematica di cui almeno un componente del gruppo è appassionato. Descrivere brevemente il sito a chi si rivolge e quali bisogni intende soddisfare.
2. Analisi di siti esistenti: descrivere le caratteristiche e funzionalità di uno o più siti concorrenti di commercio elettronico con carrello.
3. Funzionalità del sito: una breve descrizione dei contenuti del sito e delle funzionalità a disposizione dell’utente.

## Seconda consegna - Web design Document (Data di assegno 18/03/2025 - Consegna entro 26/03/2025)
Sviluppare il design del sito web di eCommerce: realizzare il documento di web design del sito web, specificando le funzioni, la struttura del sito web e del database del sito web.

Elementi da sviluppare:
1. ~~Obiettivo del progetto: scegliere un progetto su una tematica di cui almeno un componente del gruppo è appassionato. Descrivere brevemente il sito a chi si rivolge e quali bisogni intende soddisfare.~~
2. ~~Analisi di siti esistenti: descrivere le caratteristiche e funzionalità di uno o più siti concorrenti di commercio elettronico con carrello.~~
3. ~~Funzionalità del sito: una breve descrizione dei contenuti del sito e delle funzionalità a disposizione dell’utente.~~
4. Utenti del sito: indicare le tipologie di utenti ed elencare quali sono le funzionalità del sito a cui potranno accedere.
5. Diagramma navigazionale: realizzare il diagramma delle pagine che compongono il sito web.
6. La base di dati: sviluppare lo schema ER della base di dati del sito web.
7. Layout: sviluppare il layout delle pagine del sito web.
8. Tema: scegliere il tema per il sito web.
9. Scelta dei colori: scegliere i colori per il sito web.

## Terza consegna - Database (Data di assegno 01/04/2025 - Consegna entro 08/04/2025)
Creare il catalogo prodotti per il proprio sito web come nell'esempio Storage.

Elementi da sviluppare:
1. Creare una connessione con il database. 
2. Creare il DataBase in MySQL WorkBench.
3. Se si usa il datasource modificare web.xml e il context.xml con i dati della connessione.
4. Usare il DAO per mappare i bean nel modello relazionale.
