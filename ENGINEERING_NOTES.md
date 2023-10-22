# Database & Storage Systems

## Relational Database

İlişkisel veri tabanları veri güvenirliliğini ön planda tutan ACID prensiplerini göz önünde bulundurarak tasarlanmış databaselerdir.

Veriler normalize edilmiş bir şekilde tablo yapılarında tutulurlar. Bu şekilde diskte optimize edilmiş bir hacimde verileri tutarlar.

Birbirlerine sıkı bir şekilde bağlanmış ilişkisel veriler tutabilirler. (primary key, foreign key yapıları ile)

Distrubuted bir şekilde horizontal olarak scale etmeye uygun değiller.

**Atomicity**: Bir transactionın parçalanamaz biçimde ya tamamen tamamlanması yada tamamen fail olması ve transctionda ki işlemlerin geri alınmasıdır(roll back).

**Consistency:** Herhangi bir T anında yapılan bir okuma işleminin herkes tarafından aynı olarak okunmasıdır.

**Isolation:** Bir transaction bitene kadar yapılan işlemlerin diğer transaction'ları etkilememesidir. Bunu başlayan bir transaction bitene kadar diğer transaction'ları bekleterek sağlar.

**Durability:** Eğer bir transaction commit edilmişse onun kaybolmaması demektir.

## NoSql Databases

NoSQL database'ler genellikle distrubuted bir şekilde horizontal olarak scale edilebilmek üzerine tasarlanmış databaselerdir.

Document based, key value based gibi tipleri var. Veriler birbirleri ile ilişkili olmadıkları için farklı sunucularda tutulmaya uygunlar.

Örnek use case:

Diyelim ki MongoDB kullanıyorsunuz. Sizin kitaplar diye bir döküman tipiniz var. Bu kitap'ların büyüklüğüde 50 GB diyelim. Yani bir ilişkisel db'de tutmak isteseniz bir sunucu'da 50GB lık bir tabloda tutacaksın.

Bir sorgu attığın zaman gidip 50 GB data üzerinde scan etmek zorunda.

Eğer sharding yaparak verileri 2 GB olacak şekilde farklı sunuculara dağıtırsan belirlediğin bir key üzerinde sorgun sadece 2 GB üzerinde scan edecek bu şekilde daha hızlı çalışacaktır. 

## NoSql vs Sql

TBD

## Indexing

TBD

## Partioning

TBD

## Sharding

TBD

## Replication

TBD

## Sql Tuning

TBD

# Cache

* Distrubuted Caching
* CDN
* Redis
  * Redis persistence modes
  * Why redis is so fast?

# Queue & Communication Betwwen Services

* Kafka
  * How kafka works?
* RabbitMq
* Http service call between services
  * Retry
  * Backoff Duration
* Load Balancing
* Throtling or Rate Limiting

# Microservice & Microservice Design Patterns

* API Gateway
* CQRS(Commang Query Resonsibility Segredation)
* SAGA
* Circuit Breaker
* Service Registers & Service Discovery

# Kubernetes

* Kubernetes benefits

# Spring Boot

* Spring Data Jpa - Hibernate - JPA
* Aspect oriented programming
* Spring IoC structure
* Spring beans lifesycles
* Dependency Injection

# Java

- Java17 new features
- JVM - JRE - JDK
- Java memory areas
  - Heap Memory
  - Stack Memory
  - Program Counter
  - Class Area

# Unit & Integration Testing