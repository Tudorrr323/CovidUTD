# CovidUTD - Android Application

Acest proiect a fost dezvoltat inițial în cadrul hackathonului **Entrepreneur 2021 H1**, organizat de **Universitatea Politehnica din București (UPB)** în anul 2021.

**📅 Actualizare:** Proiectul a fost optimizat și actualizat la data de **20.01.2026** pentru a fi compatibil cu cele mai noi versiuni de Android și librării, fiind complet funcțional.

## 📱 Descriere și Funcționalități

Tema concursului a fost **Covid19**, iar ideea centrală a aplicației este o soluție integrată care combină funcționalități de monitorizare a pandemiei cu utilitare bazate pe locație.

### Funcționalități Principale:

*   **🗺️ Hartă Interactivă:**
    *   Utilizatorii au acces la o hartă Google Maps unde pot căuta orice locație sau oraș.
    *   **Searchbar inteligent:** Oferă hint-uri (sugestii) automate pe măsură ce utilizatorul tastează.
    *   **Pin Drop:** La selectarea unui rezultat, se adaugă automat un pin pe hartă la locația respectivă.
    *   **Localizare:** Există un buton dedicat care centrează harta pe locația curentă a telefonului (GPS).

*   **📊 Statistici Covid19 (Global & Local):**
    *   Există un buton special în aplicație care deschide un dashboard cu date despre pandemie.
    *   **Diagramă Vizuală:** Un grafic (Pie Chart) care ilustrează proporțiile dintre:
        *   Cazuri Totale
        *   Recuperări
        *   Decese
        *   Cazuri Active
    *   **Listă Detaliată:** Se pot vizualiza statistici precise precum cazuri critice, cazurile de azi, totalul morților, morții de azi și numărul de țări afectate.
    *   **Sursa datelor:** Toate datele se actualizează zilnic, fiind preluate în timp real folosind API-ul open-source găzduit pe GitHub: [https://github.com/disease-sh/API](https://github.com/disease-sh/API) (folosit în aplicație prin endpoint-ul `corona.lmao.ninja`).

*   **🌍 Track Countries:**
    *   Pe pagina de statistici există funcția "Track Countries".
    *   Utilizatorul poate căuta o anumită țară folosind bara de căutare dedicată.
    *   Aplicația va afișa exact aceleași statistici detaliate și diagrama, dar filtrate specific pentru țara selectată.

*   **👤 Profil și Setări:**
    *   **Profil:** Utilizatorii își pot vizualiza datele contului.
    *   **Log-out:** Funcționalitate completă de deconectare securizată.

## 🛠️ Configurare și Instalare

Aplicația a fost realizată în **Android Studio**. Pentru a rula proiectul, aveți nevoie de configurarea cheilor API pentru serviciile Google.

### Pasul 1: Google Firebase (Baza de date & Autentificare)
1.  Creați un proiect nou în [Firebase Console](https://console.firebase.google.com/).
2.  Activați **Authentication** și **Firestore Database**.
3.  Descărcați fișierul de configurare `google-services.json` din consola Firebase.
4.  Mutați acest fișier în directorul:
    `CovidUTD/app/google-services.json`

### Pasul 2: Google Maps SDK (Hărți)
1.  Activați **Maps SDK for Android** și **Places API** din [Google Cloud Console](https://console.cloud.google.com/).
2.  Generați o cheie API (API Key).
3.  Deschideți fișierul `AndroidManifest.xml` din proiect (`app/src/main/AndroidManifest.xml`).
4.  Căutați linia de mai jos și înlocuiți valoarea cu cheia dumneavoastră:
    ```xml
    <meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="INTRODUCETI_AICI_CHEIA_API_NOUA" />
    ```

## 💻 Tehnologii Utilizate
*   **Java** (Android Native)
*   **Firebase** (Auth, Firestore)
*   **Volley** (API Requests)
*   **Google Maps & Places API**
*   **EazeGraph** (Grafice și Diagrame)