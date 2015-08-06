studentscrowd
=============
ARCHITETTURA DELL’APPLICAZIONE WEB

Il nostro progetto intitolato "StudentsCrowd" è stato sviluppato in Java con tecnologia Servlet. Il server usato per testare l'applicazione è stato Glassfish, mentre il database utilizzato è stato Derby.

Abbiamo diviso il progetto in 5 package, in modo da renderne comprensibile la struttura generale.
Le funzioni di ogni package possono essere riassunte come segue:
<ul>
<li> Database interaction </li>
<li> Database mapping </li>
<li> Listener </li>
<li> Filter </li>
<li> Servlet </li>
</ul>

Nel primo package (com.primoprogetto.database.interaction) troviamo le classi che ci permettono di interagire col database, una per ogni tabella del database. Utilizzando i metodi statici di queste classi è possibile fare operazioni di inserimento, modifica ed estrapolazione dei  dati.

Nel secondo package (com.primoprogetto.database) troviamo la classe che ci permette di effettuare le interazioni col database (ovvero il DBManager) e tutte le classi che ci permettono di mappare lo stesso.

Nel terzo package (com.primoprogetto.listeners) troviamo un'unica classe, ovvero il ContextListener, la quale ha la funzione di ascoltatore nel server. Essa ci permette di utilizzare un'unico oggetto DBManager per ogni servlet che lo richiede, senza la necessità di aprire connessioni multiple.

Nel package dedicato ai filtri (com.primoprogetto.filters) troviamo:
1. SessionFilter blocca l'accesso se una sessione non è aperta
2. PdfFilter blocca l'accesso alla pagina di visualizzazione del pdf se non si è
amministratori di un gruppo
3. GroupFilter blocca l'accesso ai gruppi a cui non si è registrati
4. FileFilter blocca l'accesso ai file allegati ai post, se non si appartiene a quel
gruppo.

Nell'ultimo package (com.primoprogetto.servlet) troviamo le servlet. Esse sono state suddivise per nome in due formati:
1) "HTML_nomeservlet" se la loro funzione è unicamente quella di stampare codice html;
2) "Servlet_nomeservlet" se la loro funzione è quella di effettuare interazioni col database.

Il database è stato invece strutturato in 6 tabelle:
1. USERS per salvare username e password di un utente
2. INVITATIONS per salvare gli stati degli inviti ai gruppi
3. GROUPS per salvare i gruppi creati
4. USERGROUP per salvare i gruppi a cui un utente è iscritto
5. POSTS per salvare i post dei gruppi
6. POSTFILE per salvare gli eventuali file allegati ad un post (scelti di proposito di dimensione massima pari a 1MB per evitare rallentamenti nel caricamento).
7. 
Nel file web.xml sono state mappate tutte le servlet, i filtri, le pagine d'errore, l'url al
database ed il welcome-file-list che ci permette di far partire l'applicazione da una servlet. La cartella build/web/Resources/File/ funge da contenitore alle cartelle contenenti i file di ogni gruppo (salvati per ID).
