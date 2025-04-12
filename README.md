Functional Requirements
  Start from a list of seed URLs.
  Download web pages.
  Extract links and metadata.
  Recursively crawl discovered URLs (up to a depth or limit).
  Store content and metadata in a database.
  De-duplicate URLs (avoid revisits).
  Handle failures and retries.
  Control crawl rate (politeness, robots.txt).


HLD : https://imgur.com/a/XIHq5X7

How to test this?
1. you need to install docker.
2. build individual projects and create images using ./mvn clean package
3. docker compose down -v && docker compose up --build -d
4. run curl -X POST http://localhost:8081/api/seeds/publish

