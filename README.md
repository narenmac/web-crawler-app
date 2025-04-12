Functional Requirements
1. Start from a list of seed URLs.
2. Download web pages.
3. Extract links and metadata.
4. Recursively crawl discovered URLs (up to a depth or limit).
5. Store content and metadata in a database.
6. De-duplicate URLs (avoid revisits).
7. Handle failures and retries.
8. Control crawl rate (politeness, robots.txt).


HLD : https://imgur.com/a/XIHq5X7

How to test this?
1. you need to install docker.
2. build individual projects and create images using ./mvn clean package
3. docker compose down -v && docker compose up --build -d
4. run curl -X POST http://localhost:8081/api/seeds/publish

