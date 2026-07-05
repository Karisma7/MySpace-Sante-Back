# INSEE SAP Backend

Backend Spring Boot qui:

- appelle l'API Sirene INSEE;
- filtre les entreprises de services a la personne;
- persiste les donnees dans PostgreSQL.

## Configuration

Les secrets ne sont pas stockes dans le code. Creez un fichier `.env` a la racine du backend a partir du modele `.env.example`, puis lancez l'application depuis ce dossier.

Variables obligatoires:

- `INSEE_API_KEY`
- `DB_PASSWORD`

Variables optionnelles:

- `DB_USERNAME` (defaut: `postgres`)
- `DB_NAME` (defaut: `MySpace-Sante`)
- `DB_HOST` (defaut: `localhost`)
- `DB_PORT` (defaut: `5432`)
- `INSEE_BASE_URL` (defaut: `https://api.insee.fr/api-sirene/3.11`)
- `INSEE_PAGE_SIZE` (defaut: `100`)

Exemple de fichier `.env`:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=MySpace-Sante
DB_USERNAME=postgres
DB_PASSWORD=
INSEE_BASE_URL=https://api.insee.fr/api-sirene/3.11
INSEE_API_KEY=
INSEE_PAGE_SIZE=100
```

## Lancement

```bash
mvn spring-boot:run
```

## Synchronisation

Declencher une synchronisation manuelle:

```bash
curl -X POST "http://localhost:8080/api/sap-companies/sync?maxPages=5"
curl.exe -X POST "http://localhost:8081/api/sap-companies/sync?maxPages=5"
```

Le flux detaille et les fichiers impliques sont documentes dans [docs/sync-flow.md](docs/sync-flow.md).

Lister les entreprises stockees:

```bash
curl "http://localhost:8080/api/sap-companies"
curl.exe "http://localhost:8081/api/sap-companies"
```

## Hypothese API

Le backend interroge l'endpoint de recherche `siret` de l'API Sirene avec des filtres sur les codes APE/NAF lies aux services a la personne. L'en-tete d'authentification INSEE est deja injecte automatiquement par le client HTTP avec la valeur de `INSEE_API_KEY`.

Pour tester l'appel INSEE en direct, vous pouvez utiliser un `curl` comme celui-ci:

```bash
curl --header "X-INSEE-Api-Key-Integration: VOTRE_CLE_INSEE" \
	"https://api.insee.fr/api-sirene/3.11/siret?q=periode(activitePrincipaleEtablissement:88.10A)%20AND%20periode(etatAdministratifEtablissement:A)&nombre=1&curseur=*"
```