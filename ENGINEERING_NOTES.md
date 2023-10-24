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

Database indexing verilere daha hızlı erişmek için kullanılan bir yöntemdir. Query'lerde ki conditonlardaki kolonlara göre index konularak sorguların daha hızlı cevap dönmesi sağlanır.

Peki tablolara index koyduğumuzda veri tabanı nasıl daha hızlı cevap döner, index yapısı nasıl çalışır?

Where conditionlı sorgular attığımızda tablolarda ki satırlar condition'larda ki kolonlara göre sıralı olmadığı için veritabanı aradğımız conditionlara göre bütün tabloyu scan eder ve cevap döner.

Biz conditionlarımıza göre index koyduğumuzda veritabanının yaptığı şey aslında index konulan kolonlara göre bir veri yapısı oluşturarak sıralı bir şekilde veri yapısını tutmaktadır. Bu veri yapısı genellikle B-Tree ve onun türevleridir. Veritabanına index'lenmiş kolonlar üzerine bir sorgu geldiği zaman veri tabanı table üzerinde değil bu veri yapısı üzerinde scan yaparak dönmesi gereken satırları bulur. Bu veri yapısında her bir satırın adreside tutulduğu için direk O(1) karmaşıklığı ile datayı bulur ve satırdaki diğer kolonların bilgisini de döner.

Index oluşturmanın dezavantajına gelirsek write operasyonu yaparken hem bu veri yapısnı update edeceği hemde tabloya yazacağı için write operasyonuna ekstra bir yük getirir.

Buna ek olarak bir normal tablo'ya ek olarak birde veri yapısını diske yazdığı için ekstra bir disk maliyeti de getirir.

## Replication

Veritabanına gelen okuma yazma istekleri artık veritabanı sunucusunun kaldıramadığı bir noktaya ulaştığında veritabanını replicate ederek scale edebiliriz bu şekilde veritabanına gelen yükü başka sunuculara dağıtabiliriz.

Master-Slave replication stratejisi ile veritabanını birden fazla sunucuya kopyalabilir bir instance'ı master yapıp okuma ve yazma isteklerini master'a yönlendirebiliriz. Diğer instance'ları da slave yapıp okuma isteklerini de slave instance'lara yönlendirebiliriliz.

Bu şekilde okuma isteklerini farklı sunuculara dağıtmış oluruz.

Burada dikkat edilmesi gereken şey master'a bir yazma işlemi geldiğinde diğer slave'lere yazdığından emin olmasını istersek, slave'lere yazdığına dair acknowledge aldıktan sonra transaction'ı tamamlamasını sağlamak. Bu şekilde instance'lar arası inconsistent state'ler oluşmasını engellemiş oluruz. 

Fakat slave database'lerden acknowledgement bekleyeceği için yazma işleminde gecikmelere neden olabilir.

Yada ack beklemeden direk cevap dönebilir bu şekilde yazma işleminde bir gecikme olmamakla beraber eğer slave'lerden birinde bir down yada sorun olma durumunda inconsitent state'ler olma durumu oluşabilir.

## Partitioning

Veritabanında ki tablolar çok fazla büyüdüğü zaman yapılacak iyileştirmelerden bir tanesi de partitioning yapmaktır. Partitioningi iki şekilde yapabiliriz.

Birtanesi tabloları one to one ilişki ile mantıksal olarak ayrı tablolara bölmek. Çok sık ihtiyaç olan kolonları uygulamadaki iş modeli mantığı çerçevesinde bir tabloda tutup diğer kolonları farklı bir tabloya alabiliriz.

Bir diğer yöntem direk bir partition key belirleyip tabloyu eşit parçalara bölmek.Veritabanı bu şekilde tablolardaki	 dataları farklı file'lara yazar bu da scan edilecek datasetini azaltır. Buna örnek olarak üzerinde ay bilgisi olan bir kolon içeren bir tabloyu 12 parçaya bölebiliriiz. Ay conditionı içeren bir sorgu geldiğinde veritabanı bütün ayları içeren bir dataseti üzerinde scan etmek yerine sadece condition'da ki ayı içeren dataseti scan eder. Bu şekilde veritabanının scan ettiği data hacmini azaltmış oluruz.

## Sharding

TBD

## Sql Tuning

TBD

# Cache

* Distrubuted Caching
* CDN
* Redis
  * Redis persistence modes
  * Why redis is so fast?

# Queue & Communication Between Services

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